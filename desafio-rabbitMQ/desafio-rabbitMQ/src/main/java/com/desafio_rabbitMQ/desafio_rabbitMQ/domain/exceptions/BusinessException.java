package com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums.ErroProcessamentoEnum;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums.RegrasPedido;
import lombok.Getter;

@Getter
public class BusinessException extends Exception {
    private final RegrasPedido erroProcessamentoEnum;

    public BusinessException(String message, RegrasPedido erroProcessamentoEnum) {
        super(message);
        this.erroProcessamentoEnum = erroProcessamentoEnum;
    }

    public BusinessException(String message, RegrasPedido erroProcessamentoEnum, Throwable cause) {
        super(message, cause);
        this.erroProcessamentoEnum = erroProcessamentoEnum;
    }

}
