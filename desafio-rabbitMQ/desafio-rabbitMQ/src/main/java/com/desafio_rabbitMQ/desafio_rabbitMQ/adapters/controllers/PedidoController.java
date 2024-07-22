package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto.CriarPedidoResponse;
import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto.PedidoResponse;
import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto.SolicitarPedidoRequest;
import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.mappers.PedidoMapper;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.inputs.ConsultarPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PublicarMensagemProducer;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.BusinessException;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.exceptions.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PublicarMensagemProducer publicarMensagemProducer;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ConsultarPedido consultarPedido;

    @PostMapping("/enviar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CriarPedidoResponse> enviarPedido(@Valid @RequestBody SolicitarPedidoRequest solicitarPedidoRequest) throws TechnicalException {
        Pedido pedido = pedidoMapper.mapper(solicitarPedidoRequest);
        publicarMensagemProducer.publicar(pedido);
        CriarPedidoResponse response = new CriarPedidoResponse();
        response.setMensagem("Pedido criado com sucesso");
        response.setCodigoCliente(pedido.getCodigoCliente());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/valorTotal/{codigoCliente}")
    public ResponseEntity<PedidoResponse> obterValorTotalPedido(
            @PathVariable @NotNull @Positive @Min(1) Integer codigoCliente) throws TechnicalException {
        Double valorTotal = consultarPedido.calcularValorTotalPedido(codigoCliente);
        PedidoResponse response = new PedidoResponse();
        response.setCodigoCliente(codigoCliente);
        response.setValorTotal(valorTotal != 0.0 ? valorTotal : null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quantidadeTotal/{codigoCliente}")
    public ResponseEntity<Long> contarPedidosPorCliente(
            @PathVariable @NotNull @Positive @Min(1) Integer codigoCliente) throws TechnicalException {
        long quantidade = consultarPedido.contarPedidosPorCliente(codigoCliente);
        return ResponseEntity.ok(quantidade);
    }

    @GetMapping("/listar/{codigoCliente}")
    public ResponseEntity<List<PedidoResponse>> listarPedidosPorCliente(
            @PathVariable @NotNull @Positive @Min(1) Integer codigoCliente) throws BusinessException, TechnicalException {
        List<Pedido> pedidos = consultarPedido.consultarPorCodigoCliente(codigoCliente);
        List<PedidoResponse> responseList = pedidos.stream().map(pedido -> {
            PedidoResponse response = new PedidoResponse();
            response.setCodigoPedido(String.valueOf(pedido.getCodigoPedido()));
            response.setCodigoCliente(pedido.getCodigoCliente());
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
}
