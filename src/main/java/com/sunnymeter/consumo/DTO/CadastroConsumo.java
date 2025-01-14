package com.sunnymeter.consumo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroConsumo(
        @NotBlank
        String instalacao_uuid,
        @NotNull
        float consumo_kwh,
        @NotNull
        long medicao_timestamp
) {
}
