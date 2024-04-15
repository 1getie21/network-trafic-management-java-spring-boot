package com.insa.TeamOpsSystem.exceptions;

import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException {
    private String message;

    public AlreadyExistException(String message) {
        this.message = message;
    }
}
