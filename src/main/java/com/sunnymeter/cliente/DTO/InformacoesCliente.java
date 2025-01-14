package com.sunnymeter.cliente.DTO;

import com.sunnymeter.cliente.Cliente;

public record InformacoesCliente(
        String cliente_uuid,
        String nome,
        String endereco,
        String documento,
        String cep,
        boolean ativo,
        String tipo
) {

    public InformacoesCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEndereco(), cliente.getDocumento(), cliente.getCep(), cliente.isAtivo(), cliente.getTipo());
    }
}
