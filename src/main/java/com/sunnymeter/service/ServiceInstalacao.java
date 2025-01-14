package com.sunnymeter.service;

import com.sunnymeter.instalacao.DTO.CadastroInstalacao;
import com.sunnymeter.instalacao.DTO.InformacoesInstalacao;
import com.sunnymeter.instalacao.Instalacao;
import com.sunnymeter.repository.RepositoryInstalacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceInstalacao {
    @Autowired
    private RepositoryInstalacao repository;

    public ResponseEntity<?> cadastrar(CadastroInstalacao dados) {
        Instalacao instalacao = new Instalacao(dados);
        repository.save(instalacao);
        return ResponseEntity.ok(new InformacoesInstalacao(instalacao));
    }

    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(repository.findAll().stream().map(InformacoesInstalacao::new).toList());
    }

    public ResponseEntity<?> buscarPorId(String id) {
        Optional<Instalacao> instalacao = repository.findById(id);
        if (instalacao.isPresent()) {
            return ResponseEntity.ok(new InformacoesInstalacao(instalacao.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public InformacoesInstalacao deletar(String id) {
        var instalacao = repository.getReferenceById(id);
        instalacao.deletar();
        return new InformacoesInstalacao(instalacao);
    }
}
