package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.handlers;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums.RegrasPedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends AbstractHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletRequest httpRequest, MethodArgumentNotValidException exception) {
        return reply(httpRequest, exception);
    }

    @Override
    protected HttpStatus status(HttpServletRequest httpRequest, Exception exception) {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected int codigo(HttpServletRequest httpRequest, Exception exception) {
        return RegrasPedido.REQUISICAO_INVALIDA.getCodigo();
    }

    @Override
    protected String mensagem(HttpServletRequest httpRequest, Exception exception) {
        return RegrasPedido.REQUISICAO_INVALIDA.getMensagem();
    }

    @Override
    protected ArrayList<String> detalhes(HttpServletRequest httpRequest, Exception exception) {
        ArrayList<String> list = new ArrayList<>();
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) exception;
            List<FieldError> fieldErrors = validationException.getBindingResult().getFieldErrors();
            list.addAll(fieldErrors.stream()
                    .map(fieldError -> String.format("Campo '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList()));
        } else {
            list = detalhado(exception);
        }
        return list;
    }
}
