package com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.inputs;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;

public interface SolicitarPedido {
    void execute(Pedido pedido) throws TechnicalException;
}
