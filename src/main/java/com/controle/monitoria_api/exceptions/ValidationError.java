package com.controle.monitoria_api.exceptions;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addErro(String fieldName, String message) {
        erros.add(new FieldMessage(fieldName, message));
    }
}
