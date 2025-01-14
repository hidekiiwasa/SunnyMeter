package com.sunnymeter.service;

import com.sunnymeter.cliente.Cliente;
import com.sunnymeter.cliente.DTO.CadastroCliente;
import com.sunnymeter.cliente.DTO.InformacoesCliente;
import com.sunnymeter.repository.RepositoryCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceCliente {
    @Autowired
    private RepositoryCliente repository;

    public ResponseEntity<?> cadastrar(CadastroCliente dados) {
        Cliente cliente = new Cliente(dados);
        repository.save(cliente);
        return ResponseEntity.ok(new InformacoesCliente(cliente));
    }

    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(repository.findAll().stream().map(InformacoesCliente::new).toList());
    }

    public ResponseEntity<?> buscarPorId(String id) {
        Optional<Cliente> cliente = repository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(new InformacoesCliente(cliente.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public InformacoesCliente deletar(String id) {
        var cliente = repository.getReferenceById(id);
        cliente.deletar();
        return new InformacoesCliente(cliente);
    }
}
