package com.example.employee.exception.handler;

import com.example.employee.dto.Response;
import com.example.employee.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleManagerException(EmailAlreadyExistException exception) {
        return new Response(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(),new ArrayList<>());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleManagerNotFoundException(ManagerNotFoundException exception)
    {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()),exception.getMessage(),new ArrayList<>());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(),new ArrayList<>());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleUserNotFoundException(UserNotFoundException userNotFoundException)
    {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()),userNotFoundException.getMessage(),new ArrayList<>());
    }

    public Response handleAccessDeniedException(AccessDeniedException accessDeniedException)
    {
        return new Response(String.valueOf(HttpStatus.UNAUTHORIZED.value()),accessDeniedException.getMessage(),null);
    }
}
