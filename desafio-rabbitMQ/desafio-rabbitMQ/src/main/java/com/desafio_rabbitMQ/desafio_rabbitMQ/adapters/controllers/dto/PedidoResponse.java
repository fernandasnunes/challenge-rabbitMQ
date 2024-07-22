package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponse {

    @JsonProperty("codigo_pedido")
    private String codigoPedido;

    @JsonProperty("codigo_cliente")
    private Integer codigoCliente;

    @JsonProperty("valor_total")
    private Double valorTotal;
}
