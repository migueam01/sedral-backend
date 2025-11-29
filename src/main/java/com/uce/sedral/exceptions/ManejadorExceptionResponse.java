package com.uce.sedral.exceptions;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public final ResponseEntity<ExceptionResponse> manejarAccessDenied(org.springframework.security.access.AccessDeniedException ex,
                                                                       WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "No tiene permisos para acceder a este recurso",
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> manejarBadCredentials(
            BadCredentialsException ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "Usuario o contraseña incorrectos",
                "Verifique sus credenciales e intente nuevamente"
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarExcepcionesServidor(Exception ex,
                                                                              WebRequest request) {
        if (ex instanceof AuthenticationException ||
                ex instanceof AccessDeniedException) {
            throw new RuntimeException("Security exception should be handled above", ex);
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(true));
        return new ResponseEntity<>(exceptionResponse,
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
