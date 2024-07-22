package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SolicitarPedidoRequest {
    @JsonProperty("codigoPedido")
    @NotNull(message = "Código do pedido não pode ser nulo")
    @Min(value = 1, message = "Código do pedido deve ser maior que zero")
    private Integer codigoPedido;

    @JsonProperty("codigoCliente")
    @NotNull(message = "Código do cliente não pode ser nulo")
    @Min(value = 1, message = "Código do cliente deve ser maior que zero")
    private Integer codigoCliente;

    @JsonProperty("itens")
    @NotEmpty(message = "Itens não podem ser vazios")
    @Valid
    private List<ItemRequest> itens;

    @Data
    public static class ItemRequest {

        @JsonProperty("produto")
        @NotNull(message = "Produto não pode ser nulo")
        @NotEmpty(message = "Produto não pode ser vazio")
        private String produto;

        @JsonProperty("quantidade")
        @NotNull(message = "Quantidade não pode ser nula")
        @Min(value = 1, message = "Quantidade deve ser maior que zero")
        private Integer quantidade;

        @JsonProperty("preco")
        @NotNull(message = "Preço não pode ser nulo")
        @Min(value = 0, message = "Preço não pode ser negativo")
        private Double preco;

    }

}

