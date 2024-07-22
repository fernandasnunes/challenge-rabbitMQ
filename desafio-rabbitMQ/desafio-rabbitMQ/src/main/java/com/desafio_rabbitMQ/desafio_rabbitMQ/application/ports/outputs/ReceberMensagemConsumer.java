package com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;

public interface ReceberMensagemConsumer {
    void consumir(String pedido) throws TechnicalException;

}
