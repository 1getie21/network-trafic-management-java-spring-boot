package com.insa.TeamOpsSystem.exceptions;

import lombok.Data;


@Data
public class FileNotExistsException extends RuntimeException {
    private String message;

    public FileNotExistsException(String message) {
        this.message = message;
    }
}
