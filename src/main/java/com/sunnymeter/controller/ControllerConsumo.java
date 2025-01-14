package com.sunnymeter.controller;

import com.sunnymeter.consumo.DTO.CadastroConsumo;
import com.sunnymeter.service.ServiceConsumo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consumo")
public class ControllerConsumo {
    @Autowired
    private ServiceConsumo service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody CadastroConsumo dados) {
        return service.cadastrar(dados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> calcularConsumo(@PathVariable String id) {
        return service.calcularConsumo(id);
    }
}
