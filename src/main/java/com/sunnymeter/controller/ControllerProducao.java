package com.sunnymeter.controller;

import com.sunnymeter.producao.DTO.CadastroProducao;
import com.sunnymeter.service.ServiceProducao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("producao")
public class ControllerProducao {
    @Autowired
    private ServiceProducao service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody CadastroProducao dados) {
        return service.cadastrar(dados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> calcularConsumo(@PathVariable String id) {
        return service.calcularProducao(id);
    }
}
