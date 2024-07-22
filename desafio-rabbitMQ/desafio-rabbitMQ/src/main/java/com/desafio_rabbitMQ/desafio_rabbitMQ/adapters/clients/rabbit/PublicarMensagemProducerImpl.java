package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.clients.rabbit;

import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PedidoRepository;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PublicarMensagemProducer;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicarMensagemProducerImpl implements PublicarMensagemProducer {

    private static final Logger logger = LoggerFactory.getLogger(PublicarMensagemProducerImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void publicar(Pedido pedido) throws TechnicalException {
        try {
            logger.info("Iniciando processo de publicação do pedido: {}", pedido.getCodigoPedido());
            String message = objectMapper.writeValueAsString(pedido);
            rabbitTemplate.convertAndSend("pedidosQueue", message);
            logger.info("Mensagem publicada na fila: {}", message);
            pedidoRepository.guardar(pedido);
            logger.info("Pedido guardado no repositório com sucesso: {}", pedido.getCodigoPedido());
        } catch (Exception ex) {
            logger.error("Erro ao processar o pedido: {}", pedido.getCodigoPedido(), ex);
            throw new TechnicalException("Erro ao guardar pedido", ex);
        }
    }
}
