package com.sunnymeter.controller;

import com.sunnymeter.cliente.DTO.CadastroCliente;
import com.sunnymeter.cliente.DTO.InformacoesCliente;
import com.sunnymeter.service.ServiceCliente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clientes")
public class ControllerCliente {
    @Autowired
    private ServiceCliente service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody CadastroCliente dados) {
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
    public InformacoesCliente deletar(@PathVariable String id) {
        return service.deletar(id);
    }
}
