package com.sunnymeter.producao.DTO;

import com.sunnymeter.producao.Producao;

public record InformacoesProducao(String registro_producao_uuid,
                                  String instalacao_uuid,
                                  double producao_kwh,
                                  long medicao_timestamp,
                                  long created_ap) {
    public InformacoesProducao(Producao producao) {
        this(producao.getId(), producao.getInstalacao().getId(), producao.getProducaoKwh(), producao.getMedTimestamp(), System.currentTimeMillis());
    }
}
