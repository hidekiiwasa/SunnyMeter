package com.sunnymeter.cliente.DTO;

import jakarta.validation.constraints.NotBlank;

public record CadastroCliente(@NotBlank String nome, @NotBlank String endereco, @NotBlank String documento, @NotBlank String tipo, @NotBlank String cep) {
}
