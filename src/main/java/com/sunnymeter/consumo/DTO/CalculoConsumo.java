package com.sunnymeter.consumo.DTO;

import java.time.Month;

public record CalculoConsumo(String instalacao_uuid,
                             long timestamp_calculo,
                             int dia_referencia,
                             Month mes_referencia,
                             int ano_referencia,
                             int dias_para_acabar_o_mes,
                             float consumo_mensal_medio_kwh,
                             float consumo_diario_medio_kwh,
                             float consumo_mensal_estimado_kwh) {

    public CalculoConsumo(String instalacao_uuid, long timestamp_calculo, int dia_referencia, Month mes_referencia, int ano_referencia, int dias_para_acabar_o_mes, float consumo_mensal_medio_kwh, float consumo_diario_medio_kwh, float consumo_mensal_estimado_kwh) {
        this.instalacao_uuid = instalacao_uuid;
        this.timestamp_calculo = timestamp_calculo;
        this.dia_referencia = dia_referencia;
        this.mes_referencia = mes_referencia;
        this.ano_referencia = ano_referencia;
        this.dias_para_acabar_o_mes = dias_para_acabar_o_mes;
        this.consumo_mensal_medio_kwh = consumo_mensal_medio_kwh;
        this.consumo_diario_medio_kwh = consumo_diario_medio_kwh;
        this.consumo_mensal_estimado_kwh = consumo_mensal_estimado_kwh;
    }
}
