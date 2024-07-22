package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.repositories.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "pedidos")
public class PedidoEntity {
    @Id
    private String id;
    private int codigoPedido;
    private int codigoCliente;
    private List<ItemEntity> itens;

    @Data
    public static class ItemEntity {
        private String produto;
        private int quantidade;
        private double preco;
    }
}
