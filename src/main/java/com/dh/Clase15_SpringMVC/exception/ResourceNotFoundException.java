package com.dh.Clase15_SpringMVC.exception;

//creamos nuestra propia excepción

//ResourceNotFoundException: Se usará cuando un recurso no es encontrado (por ejemplo, al buscar un odontólogo o paciente por ID)
//error de cliente 404
public class ResourceNotFoundException extends RuntimeException {
    //creo un constructor
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
