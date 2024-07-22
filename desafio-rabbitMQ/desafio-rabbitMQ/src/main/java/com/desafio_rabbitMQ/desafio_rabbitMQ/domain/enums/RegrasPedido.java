package com.desafio_rabbitMQ.desafio_rabbitMQ.domain.enums;

public enum RegrasPedido {

    INEXISTENTE(-1, "Ocorreu uma falha inesperada ao tentar processar a requisição."),
    TECNICO(899, "Ocorreu uma falha técnica ao processar a requisição."),
    REQUISICAO_INVALIDA(900, "Não é possível atender a requisição."),
    PEDIDO_NAO_ENCONTRADO(100, "O cliente informado não um possui pedido.");

    private final int codigo;
    private final String mensagem;

    RegrasPedido(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }
}

