package com.desafio_rabbitMQ.desafio_rabbitMQ.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class Pedido {
    private int codigoPedido;
    private int codigoCliente;
    private List<Item> itens;

    @Data
    public static class Item {
        private String produto;
        private int quantidade;
        private double preco;
    }
}
