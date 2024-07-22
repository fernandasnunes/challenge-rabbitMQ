package com.desafio_rabbitMQ.desafio_rabbitMQ.application.usecase;

import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.inputs.SolicitarPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PedidoRepository;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolicitarPedidoImpl implements SolicitarPedido {

    private static final Logger logger = LoggerFactory.getLogger(SolicitarPedidoImpl.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void execute(Pedido pedido) throws TechnicalException {
        logger.debug("Executando solicitação de pedido para o cliente com código: {}", pedido.getCodigoCliente());
        try {
            pedidoRepository.guardar(pedido);
            logger.info("Pedido guardado com sucesso para o cliente com código: {}", pedido.getCodigoCliente());
        } catch (Exception ex) {
            logger.error("Erro ao inserir pedido para o cliente com código: {}", pedido.getCodigoCliente(), ex);
            throw new TechnicalException("Erro ao inserir pedido", ex);
        }
    }
}
