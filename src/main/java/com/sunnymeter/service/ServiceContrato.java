package com.sunnymeter.service;

import com.sunnymeter.contrato.Contrato;
import com.sunnymeter.contrato.DTO.CadastroContrato;
import com.sunnymeter.contrato.DTO.InformacoesContrato;
import com.sunnymeter.contrato.DTO.MensagemErroContrato;
import com.sunnymeter.repository.RepositoryCliente;
import com.sunnymeter.repository.RepositoryContrato;
import com.sunnymeter.repository.RepositoryInstalacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ServiceContrato {
    @Autowired
    private RepositoryContrato repositoryContrato;
    @Autowired
    private RepositoryInstalacao repositoryInstalacao;
    @Autowired
    private RepositoryCliente repositoryCliente;

    public ResponseEntity<?> cadastrar (CadastroContrato dados) {
        var instalacao = repositoryInstalacao.findById(dados.instalacao_uuid());
        var cliente = repositoryCliente.findById(dados.cliente_uuid());

        if (!isValidTimeframe(dados.timeframe())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErroContrato("INVALID_TIMEFRAME", "Invalid timeframe used! Please select a valid timeframe! \nInput timeframe: " + dados.timeframe() + "\\nAvailable timeframes: [90, 180, 270, ... , 810]"));
        }

        if (instalacao.isPresent() && cliente.isPresent()) {
            Contrato contrato = new Contrato(dados.timeframe(), System.currentTimeMillis(), instalacao.get(), cliente.get());
            repositoryContrato.save(contrato);
            return ResponseEntity.ok(new InformacoesContrato(contrato));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> listar() {
        long timestampAtual = System.currentTimeMillis();

        List<Contrato> contratosAtivos = repositoryContrato.findAllByAtivoTrue();

        contratosAtivos.forEach(contrato -> {
            long timeframeEmTimestamp = TimeUnit.DAYS.toMillis(contrato.getDuracao());
            long diferencaEmTimestamp = timestampAtual - contrato.getDataDeInicio();

            if (diferencaEmTimestamp >= timeframeEmTimestamp) {
                contrato.deletar();
                repositoryContrato.save(contrato);
            }
        });

        List<InformacoesContrato> contratosDto = contratosAtivos.stream().map(InformacoesContrato::new).toList();

        return ResponseEntity.ok(contratosDto);
    }

    public ResponseEntity<?> buscarPorId(String id) {
        Optional<Contrato> contratoOpt = repositoryContrato.findById(id);

        if (contratoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum contrato encontrado com esse ID.");
        }

        Contrato contrato = contratoOpt.get();
        long timestampAtual = System.currentTimeMillis();
        long timeframeEmTimestamp = TimeUnit.DAYS.toMillis(contrato.getDuracao());
        long diferencaEmTimestamp = timestampAtual - contrato.getDataDeInicio();

        if (diferencaEmTimestamp >= timeframeEmTimestamp) {
            contrato.deletar();
            repositoryContrato.save(contrato);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contrato expirou.");
        }

        return ResponseEntity.ok(new InformacoesContrato(contrato));
    }

    public ResponseEntity<?> deletar (String id) {
        var contrato = repositoryContrato.getReferenceById(id);
        if (contrato.isAtivo() == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErroContrato("INVALID_DELETE_REQUEST", "This contract is already canceled!"));
        }
        contrato.deletar();
        return ResponseEntity.ok(new InformacoesContrato(contrato));
    }

    private boolean isValidTimeframe(int timeframe) {
        return timeframe % 90 == 0;
    }


}
