package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.handlers;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums.RegrasPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TechnicalExceptionHandler extends AbstractHandler {

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletRequest httpRequest, TechnicalException technicalException) {
        return reply(httpRequest, technicalException);
    }

    @Override
    public HttpStatus status(HttpServletRequest httpRequest, Exception exception) {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public int codigo(HttpServletRequest httpRequest, Exception exception) {
        return RegrasPedido.TECNICO.getCodigo();
    }

    @Override
    public String mensagem(HttpServletRequest httpRequest, Exception exception) {
        return RegrasPedido.TECNICO.getMensagem();
    }

    @Override
    public ArrayList<String> detalhes(HttpServletRequest httpRequest, Exception exception) {
        return detalhado(exception);
    }
}

