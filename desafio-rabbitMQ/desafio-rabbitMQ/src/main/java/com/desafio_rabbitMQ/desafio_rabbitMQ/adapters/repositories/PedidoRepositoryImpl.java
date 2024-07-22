package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.repositories;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.repositories.entities.PedidoEntity;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PedidoRepository;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoRepositoryImpl implements PedidoRepository {

    @Autowired
    private PedidoProvider pedidoProvider;

    @Override
    public void guardar(Pedido pedido) {
        PedidoEntity pedidoEntity = convertToEntity(pedido);
        pedidoProvider.save(pedidoEntity);
    }

    @Override
    public Double calcularValorTotalPedido(Integer codigoCliente) {
        List<PedidoEntity> pedidoEntities = pedidoProvider.findByCodigoCliente(codigoCliente);
        return pedidoEntities.stream()
                .mapToDouble(this::calculateTotalValue)
                .sum();
    }

    @Override
    public long contarPedidosPorCliente(Integer codigoCliente) {
        return pedidoProvider.countByCodigoCliente(codigoCliente);
    }

    @Override
    public List<Pedido> consultarPorCodigoCliente(Integer codigoCliente) {
        List<PedidoEntity> pedidoEntities = pedidoProvider.findByCodigoCliente(codigoCliente);
        return pedidoEntities.stream()
                .map(this::convertToDomain)
                .collect(Collectors.toList());
    }

    private PedidoEntity convertToEntity(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setCodigoPedido(pedido.getCodigoPedido());
        pedidoEntity.setCodigoCliente(pedido.getCodigoCliente());

        List<PedidoEntity.ItemEntity> itemEntities = pedido.getItens().stream()
                .map(item -> {
                    PedidoEntity.ItemEntity itemEntity = new PedidoEntity.ItemEntity();
                    itemEntity.setProduto(item.getProduto());
                    itemEntity.setQuantidade(item.getQuantidade());
                    itemEntity.setPreco(item.getPreco());
                    return itemEntity;
                })
                .collect(Collectors.toList());

        pedidoEntity.setItens(itemEntities);
        return pedidoEntity;
    }

    private Pedido convertToDomain(PedidoEntity pedidoEntity) {
        Pedido pedido = new Pedido();
        pedido.setCodigoPedido(pedidoEntity.getCodigoPedido());
        pedido.setCodigoCliente(pedidoEntity.getCodigoCliente());

        List<Pedido.Item> items = pedidoEntity.getItens().stream()
                .map(itemEntity -> {
                    Pedido.Item item = new Pedido.Item();
                    item.setProduto(itemEntity.getProduto());
                    item.setQuantidade(itemEntity.getQuantidade());
                    item.setPreco(itemEntity.getPreco());
                    return item;
                })
                .collect(Collectors.toList());

        pedido.setItens(items);
        return pedido;
    }

    private Double calculateTotalValue(PedidoEntity pedidoEntity) {
        return pedidoEntity.getItens().stream()
                .mapToDouble(PedidoEntity.ItemEntity::getPreco)
                .sum();
    }
}
