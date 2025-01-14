package com.sunnymeter.service;

import com.sunnymeter.consumo.Consumo;
import com.sunnymeter.consumo.DTO.CadastroConsumo;
import com.sunnymeter.consumo.DTO.CalculoConsumo;
import com.sunnymeter.consumo.DTO.InformacoesConsumo;
import com.sunnymeter.instalacao.Instalacao;
import com.sunnymeter.repository.RepositoryConsumo;
import com.sunnymeter.repository.RepositoryInstalacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceConsumo {

    @Autowired
    private RepositoryConsumo repositoryConsumo;

    @Autowired
    private RepositoryInstalacao repositoryInstalacao;

    public ResponseEntity<?> cadastrar(CadastroConsumo dados) {
        Optional<Instalacao> optionalInstalacao = repositoryInstalacao.findById(dados.instalacao_uuid());
        if (optionalInstalacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instalação não foi encontrada.");
        }

        Instalacao instalacao = optionalInstalacao.get();

        if (dados.consumo_kwh() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insira um consumo válido.");
        }

        List<Consumo> consumos = repositoryConsumo.findByInstalacao(instalacao);

        if (!consumos.isEmpty()) {
            Consumo ultimoConsumo = consumos.get(consumos.size() - 1);

            if (ultimoConsumo.getConsumoKwh() >= dados.consumo_kwh()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insira um consumo_kwh maior do que os que já tiveram.");
            }

            if (ultimoConsumo.getMedTimestamp() >= dados.medicao_timestamp()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insira um timestamp maior (uma data posterior) aos que já tiveram.");
            }
        }

        Consumo novoConsumo = instalacao.adicionarConsumo(dados.consumo_kwh(), dados.medicao_timestamp());
        repositoryConsumo.save(novoConsumo);

        return ResponseEntity.ok(new InformacoesConsumo(novoConsumo));
    }

    public ResponseEntity<?> calcularConsumo(String id) {
        Optional<Instalacao> instalacaoOpt = repositoryInstalacao.findById(id);

        if (instalacaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma instalação foi encontrada.");
        }

        Instalacao instalacao = instalacaoOpt.get();
        LocalDate dataAtual = LocalDate.now();
        YearMonth anoMesAtual = YearMonth.of(dataAtual.getYear(), dataAtual.getMonth());
        int diasParaAcabarOMes = anoMesAtual.lengthOfMonth() - dataAtual.getDayOfMonth();

        List<Consumo> consumosDoMes = repositoryConsumo.findByInstalacao_id(id).stream()
                .filter(consumo -> isConsumoNoMesAtual(consumo.getMedTimestamp(), dataAtual))
                .toList();

        float consumoMensal = calcularConsumoMensal(consumosDoMes);
        float consumoDiario = calcularConsumoDiario(consumosDoMes);
        float consumoMensalEstimado = consumoMensal + (consumoDiario * diasParaAcabarOMes);

        return ResponseEntity.ok(new CalculoConsumo(
                instalacao.getId(),
                Instant.now().getEpochSecond(),
                dataAtual.getDayOfMonth(),
                dataAtual.getMonth(),
                dataAtual.getYear(),
                diasParaAcabarOMes,
                consumoMensal,
                consumoDiario,
                consumoMensalEstimado
        ));

    }

    private boolean isConsumoNoMesAtual(long timestamp, LocalDate dataAtual) {
        Month consumoMes = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getMonth();
        return consumoMes.equals(dataAtual.getMonth());
    }

    private float calcularConsumoMensal(List<Consumo> consumos) {
        float consumoPrimeiroDoMes = consumos.get(0).getConsumoKwh();

        float consumoUltimoDoMes = consumos.get(consumos.size() - 1).getConsumoKwh();

        return consumoUltimoDoMes - consumoPrimeiroDoMes;
    }

    private float calcularConsumoDiario(List<Consumo> consumos) {
        int primeiroDia = Instant.ofEpochSecond(consumos.get(0).getMedTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getDayOfMonth();
        int ultimoDia = Instant.ofEpochSecond(consumos.get(consumos.size() - 1).getMedTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getDayOfMonth();

        if (ultimoDia == primeiroDia) {
            return calcularConsumoMensal(consumos);
        }

        return calcularConsumoMensal(consumos) / (ultimoDia - primeiroDia);
    }


}
