package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.repositories;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.repositories.entities.PedidoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PedidoProvider extends MongoRepository<PedidoEntity, String> {
    List<PedidoEntity> findByCodigoCliente(Integer codigoCliente);

    long countByCodigoCliente(Integer codigoCliente);
}
