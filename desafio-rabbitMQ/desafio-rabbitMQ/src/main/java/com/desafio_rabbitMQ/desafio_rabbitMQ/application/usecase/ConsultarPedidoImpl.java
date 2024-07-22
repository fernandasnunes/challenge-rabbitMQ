package com.desafio_rabbitMQ.desafio_rabbitMQ.application.usecase;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.repositories.entities.PedidoEntity;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.inputs.ConsultarPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PedidoRepository;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums.RegrasPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.BusinessException;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultarPedidoImpl implements ConsultarPedido {

    private static final Logger logger = LoggerFactory.getLogger(ConsultarPedidoImpl.class);

    private final PedidoRepository pedidoRepository;

    @Autowired
    public ConsultarPedidoImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Double calcularValorTotalPedido(Integer codigoCliente) throws TechnicalException {
        logger.debug("Calculando valor total dos pedidos para o cliente com código: {}", codigoCliente);
        try {
            Double valorTotal = pedidoRepository.calcularValorTotalPedido(codigoCliente);
            if (valorTotal == 0.0) {
                throw new BusinessException("Nenhum pedido encontrado para o cliente com código:" + codigoCliente, RegrasPedido.PEDIDO_NAO_ENCONTRADO);
            }
            return valorTotal;
        } catch (Exception e) {
            logger.error("Erro ao calcular valor total dos pedidos para o cliente com código: {}", codigoCliente, e);
            throw new TechnicalException("Erro técnico ao calcular valor total dos pedidos", e);
        }
    }

    @Override
    public long contarPedidosPorCliente(Integer codigoCliente) throws TechnicalException {
        logger.debug("Contando pedidos para o cliente com código: {}", codigoCliente);
        try {
            long quantidade = pedidoRepository.contarPedidosPorCliente(codigoCliente);
            if (quantidade == 0) {
                throw new BusinessException("Nenhum pedido encontrado para o cliente com código: " + codigoCliente, RegrasPedido.PEDIDO_NAO_ENCONTRADO);
            }
            return quantidade;
        } catch (Exception e) {
            logger.error("Erro ao contar pedidos para o cliente com código: {}", codigoCliente, e);
            throw new TechnicalException("Erro técnico ao contar pedidos do cliente", e);
        }
    }

    @Override
    public List<Pedido> consultarPorCodigoCliente(Integer codigoCliente) throws TechnicalException, BusinessException {
        logger.debug("Consultando pedidos para o cliente com código: {}", codigoCliente);
        try {
            List<Pedido> pedidos = pedidoRepository.consultarPorCodigoCliente(codigoCliente);
            if (pedidos.isEmpty()) {
                throw new BusinessException("Nenhum pedido encontrado para o cliente com código: " + codigoCliente, RegrasPedido.PEDIDO_NAO_ENCONTRADO);
            }
            logger.info("Encontrados {} pedidos para o cliente com código: {}", pedidos.size(), codigoCliente);
            return new ArrayList<>(pedidos);
        } catch (BusinessException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao consultar pedidos para o cliente com código: {}", codigoCliente, e);
            throw new TechnicalException("Erro técnico ao consultar pedidos do cliente", e);
        }
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
}
