package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.clients.rabbit;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto.SolicitarPedidoRequest;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.ReceberMensagemConsumer;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceberMensagemConsumerImpl implements ReceberMensagemConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ReceberMensagemConsumerImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "pedidosQueue")
    public void consumir(String message) throws TechnicalException {
        try {
            SolicitarPedidoRequest solicitarPedidoRequest = objectMapper.readValue(message, SolicitarPedidoRequest.class);
            logger.info("Received message: {}", solicitarPedidoRequest);
        } catch (Exception ex) {
            logger.error("Erro ao receber mensagem na fila", ex);
            throw new TechnicalException("Erro ao receber mensagem na fila", ex);
        }
    }
}
