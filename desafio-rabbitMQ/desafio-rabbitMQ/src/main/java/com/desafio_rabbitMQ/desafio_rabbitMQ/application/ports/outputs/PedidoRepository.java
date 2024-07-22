package com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs;

import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;

import java.util.List;

public interface PedidoRepository {
    void guardar(Pedido pedido);
    Double calcularValorTotalPedido(Integer codigoCliente);
    long contarPedidosPorCliente(Integer codigoCliente);
    List<Pedido> consultarPorCodigoCliente(Integer codigoCliente);
}
