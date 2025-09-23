package com.uce.sedral.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ManejadorExceptionResponse extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        StringBuilder errores = new StringBuilder();
        for (ObjectError e : ex.getBindingResult().getAllErrors()) {
            errores.append(e.getObjectName());
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validacion fallida",
                request.getDescription(false));
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarExcepcionesServidor(Exception ex,
                                                                              WebRequest request) {
        ExceptionResponse excepetionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(excepetionResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModeloNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ModeloNotFoundException ex,
                                                                            WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
