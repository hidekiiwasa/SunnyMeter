package com.sunnymeter.contrato.DTO;

public record MensagemErroContrato(
        String error_code,
        String error_description
) {

    public MensagemErroContrato(String error_code, String error_description) {
        this.error_code = error_code;
        this.error_description = error_description;
    }
}
