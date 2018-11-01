package com.almybaties.exceptions;

/**
 * @author adonai
 * user-defined Exception extends RuntimeException of JDk
 */
public class AIbatisException extends RuntimeException{

    private static final long serialVersionUID = 3880206998166270511L;

    public AIbatisException() {
        super();
    }

    public AIbatisException(String message) {
        super(message);
    }

    public AIbatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public AIbatisException(Throwable cause) {
        super(cause);
    }
}
