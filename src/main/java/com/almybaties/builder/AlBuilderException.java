package com.almybaties.builder;

import com.almybaties.exceptions.AlPersistenceException;

/**
 * @author adonai
 * user-defined builder Exception
 */
public class AlBuilderException  extends AlPersistenceException{

    private static final long serialVersionUID = -3885164021020443281L;

    public AlBuilderException() {
        super();
    }

    public AlBuilderException(String message) {
        super(message);
    }

    public AlBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlBuilderException(Throwable cause) {
        super(cause);
    }

}
