package com.sunnymeter.contrato.DTO;

import com.sunnymeter.contrato.Contrato;

public record InformacoesContrato(
        String instalacao_uuid,
        String cliente_uuid,
        String contrato_uuid,
        int timeframe,
        String ativo,
        long contrato_inicio_timestamp
) {

    public InformacoesContrato(Contrato contrato) {
        this(contrato.getInstalacao().getId(),
                contrato.getCliente().getId(),
                contrato.getId(),
                contrato.getDuracao(),
                contrato.isAtivo() ? "Ativo" : "Desativado",
                contrato.getDataDeInicio());
    }
}
