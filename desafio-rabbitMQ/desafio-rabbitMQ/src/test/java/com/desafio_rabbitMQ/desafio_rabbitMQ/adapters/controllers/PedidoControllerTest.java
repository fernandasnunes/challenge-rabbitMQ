package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers;

import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.dto.SolicitarPedidoRequest;
import com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.mappers.PedidoMapper;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.inputs.ConsultarPedido;
import com.desafio_rabbitMQ.desafio_rabbitMQ.application.ports.outputs.PublicarMensagemProducer;
import com.desafio_rabbitMQ.desafio_rabbitMQ.domain.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoMapper pedidoMapper;

    @MockBean
    private PublicarMensagemProducer publicarMensagemProducer;

    @MockBean
    private ConsultarPedido consultarPedido;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnviarPedido() throws Exception {
        SolicitarPedidoRequest request = new SolicitarPedidoRequest();
        request.setCodigoCliente(22);
        request.setCodigoPedido(14);
        SolicitarPedidoRequest.ItemRequest item = new SolicitarPedidoRequest.ItemRequest();
        item.setProduto("lapis");
        item.setQuantidade(10);
        item.setPreco(60.0);
        request.setItens(Collections.singletonList(item));

        Pedido pedido = new Pedido();
        pedido.setCodigoCliente(22);
        pedido.setCodigoPedido(14);
        Pedido.Item itemPedido = new Pedido.Item();
        itemPedido.setQuantidade(10);
        itemPedido.setPreco(60.0);
        pedido.setItens(Collections.singletonList(itemPedido));

        doNothing().when(publicarMensagemProducer).publicar(any(Pedido.class));
        when(pedidoMapper.mapper(any(SolicitarPedidoRequest.class))).thenReturn(pedido);

        mockMvc.perform(MockMvcRequestBuilders.post("/pedidos/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Pedido criado com sucesso"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo_cliente").value(22))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testEnviarPedidoBadRequest() throws Exception {
        SolicitarPedidoRequest request = new SolicitarPedidoRequest();
        request.setItens(Collections.singletonList(new SolicitarPedidoRequest.ItemRequest()));

        mockMvc.perform(MockMvcRequestBuilders.post("/pedidos/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testObterValorTotalPedido() throws Exception {
        when(consultarPedido.calcularValorTotalPedido(anyInt())).thenReturn(100.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/valorTotal/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo_cliente").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor_total").value(100.0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testContarPedidosPorCliente() throws Exception {
        when(consultarPedido.contarPedidosPorCliente(anyInt())).thenReturn(5L);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/quantidadeTotal/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("5"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testListarPedidosPorCliente() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setCodigoPedido(1);
        pedido.setCodigoCliente(1);

        List<Pedido> pedidos = Collections.singletonList(pedido);

        when(consultarPedido.consultarPorCodigoCliente(anyInt())).thenReturn(pedidos);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/listar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo_pedido").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo_cliente").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testEnviarPedidoBadRequestComValidacao() throws Exception {
        SolicitarPedidoRequest request = new SolicitarPedidoRequest();
        request.setItens(Collections.singletonList(new SolicitarPedidoRequest.ItemRequest()));

        mockMvc.perform(MockMvcRequestBuilders.post("/pedidos/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(900))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Não é possível atender a requisição."))
                .andDo(MockMvcResultHandlers.print());
    }
}
