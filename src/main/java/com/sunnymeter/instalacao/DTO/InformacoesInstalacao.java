package com.sunnymeter.instalacao.DTO;

import com.sunnymeter.instalacao.Instalacao;

public record InformacoesInstalacao(String instalacao_uuid, String endereco, String cep, boolean ativo){
    public InformacoesInstalacao(Instalacao instalacao) {
        this(instalacao.getId(), instalacao.getEndereco(), instalacao.getCep(), instalacao.isAtivo());
    }
}
