package com.almybaties.exceptions;

/**
 * @author adonai
 */
public class AlPersistenceException extends AIbatisException{

    private static final long serialVersionUID = -7537395265357977271L;

    public AlPersistenceException() {
        super();
    }

    public AlPersistenceException(String message) {
        super(message);
    }

    public AlPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlPersistenceException(Throwable cause) {
        super(cause);
    }

}
