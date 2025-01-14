package com.sunnymeter.producao.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroProducao(
        @NotBlank
        String instalacao_uuid,
        @NotNull
        float producao_kwh,
        @NotNull
        long medicao_timestamp
) {
}
