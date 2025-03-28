package org.fiap.infra.exceptions;

public class RecordAlreadyExistsException extends RuntimeException {

    public RecordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordAlreadyExistsException(String message) {
        super(message);
    }

}
