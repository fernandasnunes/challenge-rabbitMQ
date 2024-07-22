package com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;

public interface PublicarMensagemProducer {

    void publicar(Pedido pedido) throws TechnicalException;
}
