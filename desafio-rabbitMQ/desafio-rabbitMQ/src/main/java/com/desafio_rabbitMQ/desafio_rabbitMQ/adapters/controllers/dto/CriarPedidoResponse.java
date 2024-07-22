package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CriarPedidoResponse {

    @JsonProperty("mensagem")
    private String mensagem;

    @JsonProperty("codigo_cliente")
    private Integer codigoCliente;
}

