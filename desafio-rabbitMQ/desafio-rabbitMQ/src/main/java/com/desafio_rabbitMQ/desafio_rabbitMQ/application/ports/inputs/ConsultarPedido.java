package com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.inputs;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.BusinessException;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;

import java.util.List;

public interface ConsultarPedido {
    Double calcularValorTotalPedido(Integer codigoCliente) throws TechnicalException;
    long contarPedidosPorCliente(Integer codigoCliente) throws TechnicalException;
    List<Pedido> consultarPorCodigoCliente(Integer codigoCliente) throws TechnicalException, BusinessException;
}
