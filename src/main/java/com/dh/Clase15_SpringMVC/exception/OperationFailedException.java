package com.dh.Clase15_SpringMVC.exception;


//OperationFailedException: Se usará para manejar cualquier operación que falle inesperadamente, como fallos en actualizaciones o eliminaciones
//error servidor 500
public class OperationFailedException extends RuntimeException {
    public OperationFailedException(String message) {
        super(message);
    }
}