package com.sunnymeter.contrato.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroContrato(
        @NotBlank
        String instalacao_uuid,

        @NotBlank
        String cliente_uuid,

        @NotNull
        int timeframe
) {
}
