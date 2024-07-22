package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.handlers;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums.RegrasPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.BusinessException;
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
public class BusinessExceptionHandler extends AbstractHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletRequest httpRequest, BusinessException businessException) {
        return reply(httpRequest, businessException);
    }

    @Override
    public HttpStatus status(HttpServletRequest httpRequest, Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            switch (businessException.getErroProcessamentoEnum()) {
                case PEDIDO_NAO_ENCONTRADO:
                    return HttpStatus.NOT_FOUND;
                default:
                    return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public int codigo(HttpServletRequest httpRequest, Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            return businessException.getErroProcessamentoEnum().getCodigo();
        }
        return RegrasPedido.INEXISTENTE.getCodigo();
    }

    @Override
    public String mensagem(HttpServletRequest httpRequest, Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            return businessException.getErroProcessamentoEnum().getMensagem();
        }
        return RegrasPedido.INEXISTENTE.getMensagem();
    }

    @Override
    public ArrayList<String> detalhes(HttpServletRequest httpRequest, Exception exception) {
        return detalhado(exception);
    }
}

