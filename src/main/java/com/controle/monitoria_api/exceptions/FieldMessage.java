package com.controle.monitoria_api.exceptions;

public record FieldMessage(
        String fieldName,
        String message) {
}
