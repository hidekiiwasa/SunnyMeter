package com.sunnymeter.consumo.DTO;

import com.sunnymeter.consumo.Consumo;

public record InformacoesConsumo(
    String registro_consumo_uuid,
    String instalacao_uuid,
    float consumo_kwh,
    long medicao_timestamp,
    long created_at
) {
    public InformacoesConsumo(Consumo consumo) {
        this(consumo.getId(), consumo.getInstalacao().getId(), consumo.getConsumoKwh(), consumo.getMedTimestamp(), System.currentTimeMillis());
    }
}
