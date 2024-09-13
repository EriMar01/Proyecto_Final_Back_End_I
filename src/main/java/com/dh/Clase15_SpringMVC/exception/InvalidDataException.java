package com.dh.Clase15_SpringMVC.exception;


//InvalidDataException: Se usará cuando la entrada proporcionada al sistema es inválida
//error cliente 400
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
