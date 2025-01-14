package com.sunnymeter.service;

import com.sunnymeter.instalacao.Instalacao;
import com.sunnymeter.producao.DTO.CadastroProducao;
import com.sunnymeter.producao.DTO.CalculoProducao;
import com.sunnymeter.producao.DTO.InformacoesProducao;
import com.sunnymeter.producao.Producao;
import com.sunnymeter.repository.RepositoryInstalacao;
import com.sunnymeter.repository.RepositoryProducao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceProducao {
    @Autowired
    private RepositoryProducao repositoryProducao;

    @Autowired
    private RepositoryInstalacao repositoryInstalacao;


    public ResponseEntity<?> cadastrar(CadastroProducao dados) {
        Optional<Instalacao> optionalInstalacao = repositoryInstalacao.findById(dados.instalacao_uuid());
        if (optionalInstalacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instalação não foi encontrada.");
        }

        Instalacao instalacao = optionalInstalacao.get();

        if (dados.producao_kwh() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insira uma produção válida.");
        }

        List<Producao> producoes = repositoryProducao.findByInstalacao(instalacao);

        if (!producoes.isEmpty()) {
            Producao ultimoProducao = producoes.get(producoes.size() - 1);

            if (ultimoProducao.getProducaoKwh() >= dados.producao_kwh()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insira uma producao_kwh maior do que os que já tiveram.");
            }

            if (ultimoProducao.getMedTimestamp() >= dados.medicao_timestamp()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insira um timestamp maior (uma data posterior) aos que já tiveram.");
            }
        }

        Producao novoProducao = instalacao.adicionarProducao(dados.producao_kwh(), dados.medicao_timestamp());
        repositoryProducao.save(novoProducao);

        return ResponseEntity.ok(new InformacoesProducao(novoProducao));
    }

    public ResponseEntity<?> calcularProducao(String id) {
        Optional<Instalacao> optionalInstalacao = repositoryInstalacao.findById(id);
        if (optionalInstalacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma instalação foi encontrada.");
        }

        Instalacao instalacao = optionalInstalacao.get();
        LocalDate dataAtual = LocalDate.now();
        YearMonth anoMesAtual = YearMonth.of(dataAtual.getYear(), dataAtual.getMonth());
        int diasParaAcabarOMes = anoMesAtual.lengthOfMonth() - dataAtual.getDayOfMonth();

        List<Producao> producoesDoMes = repositoryProducao.findByInstalacao_Id(id).stream()
                .filter(producao -> isProducaoNoMesAtual(producao.getMedTimestamp(), dataAtual))
                .collect(Collectors.toList());

        float producaoMensal = calcularProducaoMensal(producoesDoMes);
        float producaoDiaria = calcularProducaoDiaria(producoesDoMes);
        float producaoMensalEstimada = producaoMensal + (producaoDiaria * diasParaAcabarOMes);

        return ResponseEntity.ok(new CalculoProducao(
                instalacao.getId(),
                Instant.now().getEpochSecond(),
                dataAtual.getDayOfMonth(),
                dataAtual.getMonth(),
                dataAtual.getYear(),
                diasParaAcabarOMes,
                producaoMensal,
                producaoDiaria,
                producaoMensalEstimada
        ));
    }

    private boolean isProducaoNoMesAtual(long timestamp, LocalDate dataAtual) {
        Month producaoMes = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getMonth();
        return producaoMes.equals(dataAtual.getMonth());
    }

    private float calcularProducaoMensal(List<Producao> producoes) {
        float producaoPrimeiroDoMes = producoes.get(0).getProducaoKwh();
        float producaoUltimoDoMes = producoes.get(producoes.size() - 1).getProducaoKwh();
        return producaoUltimoDoMes - producaoPrimeiroDoMes;
    }

    private float calcularProducaoDiaria(List<Producao> producoes) {
        int primeiroDia = Instant.ofEpochSecond(producoes.get(0).getMedTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getDayOfMonth();
        int ultimoDia = Instant.ofEpochSecond(producoes.get(producoes.size() - 1).getMedTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getDayOfMonth();

        if (ultimoDia == primeiroDia) {
            return calcularProducaoMensal(producoes);
        }

        return calcularProducaoMensal(producoes) / (ultimoDia - primeiroDia);
    }

}
