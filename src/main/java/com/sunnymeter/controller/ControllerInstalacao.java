package com.sunnymeter.controller;

import com.sunnymeter.instalacao.DTO.CadastroInstalacao;
import com.sunnymeter.instalacao.DTO.InformacoesInstalacao;
import com.sunnymeter.service.ServiceInstalacao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("instalacoes")
public class ControllerInstalacao {
    @Autowired
    private ServiceInstalacao service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody CadastroInstalacao dados) {
        return service.cadastrar(dados);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public InformacoesInstalacao deletar(@PathVariable String id) {
        return service.deletar(id);
    }
}
