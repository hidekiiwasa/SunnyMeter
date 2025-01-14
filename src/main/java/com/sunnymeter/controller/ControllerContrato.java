package com.sunnymeter.controller;

import com.sunnymeter.contrato.DTO.CadastroContrato;
import com.sunnymeter.service.ServiceContrato;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contratos")
public class ControllerContrato {

    @Autowired
    private ServiceContrato service;


    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody CadastroContrato dados) {
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
    public ResponseEntity<?> deletar(@PathVariable String id) {
        return service.deletar(id);
    }
}
