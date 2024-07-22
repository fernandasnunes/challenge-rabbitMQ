package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.mappers;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto.SolicitarPedidoRequest;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido mapper(SolicitarPedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();
        pedido.setCodigoPedido(pedidoRequest.getCodigoPedido());
        pedido.setCodigoCliente(pedidoRequest.getCodigoCliente());

        List<Pedido.Item> items = pedidoRequest.getItens().stream()
                .map(this::toDomainItem)
                .collect(Collectors.toList());

        pedido.setItens(items);
        return pedido;
    }

    private Pedido.Item toDomainItem(SolicitarPedidoRequest.ItemRequest itemRequest) {
        Pedido.Item item = new Pedido.Item();
        item.setProduto(itemRequest.getProduto());
        item.setQuantidade(itemRequest.getQuantidade());
        item.setPreco(itemRequest.getPreco());
        return item;
    }
}
