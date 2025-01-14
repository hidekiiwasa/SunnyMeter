package com.sunnymeter.producao.DTO;

import java.time.Month;

public record CalculoProducao(
        String instalacao_uuid,
        long timestamp_calculo,
        int dia_referencia,
        Month mes_referencia,
        int ano_referencia,
        int dias_para_acabar_o_mes,
        float producao_mensal_medio_kwh,
        float producao_diario_medio_kwh,
        float producao_mensal_estimado_kwh
) {

    public CalculoProducao(String instalacao_uuid, long timestamp_calculo, int dia_referencia, Month mes_referencia, int ano_referencia, int dias_para_acabar_o_mes, float producao_mensal_medio_kwh, float producao_diario_medio_kwh, float producao_mensal_estimado_kwh) {
        this.instalacao_uuid = instalacao_uuid;
        this.timestamp_calculo = timestamp_calculo;
        this.dia_referencia = dia_referencia;
        this.mes_referencia = mes_referencia;
        this.ano_referencia = ano_referencia;
        this.dias_para_acabar_o_mes = dias_para_acabar_o_mes;
        this.producao_mensal_medio_kwh = producao_mensal_medio_kwh;
        this.producao_diario_medio_kwh = producao_diario_medio_kwh;
        this.producao_mensal_estimado_kwh = producao_mensal_estimado_kwh;
    }
}
