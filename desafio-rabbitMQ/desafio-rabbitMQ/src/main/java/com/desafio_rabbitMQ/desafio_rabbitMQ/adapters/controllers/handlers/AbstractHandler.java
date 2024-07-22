package com.desafio_rabbitMQ.desafio_rabbitMQ.adapters.controllers.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public abstract class AbstractHandler {
    protected abstract HttpStatus status(HttpServletRequest httpRequest, Exception exception);
    protected abstract int codigo(HttpServletRequest httpRequest, Exception exception);
    protected abstract String mensagem(HttpServletRequest httpRequest, Exception exception);
    protected abstract ArrayList<String> detalhes(HttpServletRequest httpRequest, Exception exception);

    protected MediaType contentType() {
        return MediaType.APPLICATION_JSON;
    }

    protected ResponseEntity<ErrorResponse> reply(HttpServletRequest httpRequest, Exception exception) {
        ArrayList<String> details = detalhes(httpRequest, exception);
        return ResponseEntity.status(status(httpRequest, exception))
                .contentType(contentType())
                .body(new ErrorResponse(codigo(httpRequest, exception), mensagem(httpRequest, exception), details));
    }

    protected ArrayList<String> detalhado(Throwable throwable) {
        ArrayList<String> list = new ArrayList<>();
        if (throwable == null) return list;
        list.add(resumido(throwable));
        Throwable cause = throwable.getCause();
        while (cause != null) {
            list.add(resumido(cause));
            cause = cause.getCause();
        }
        return list;
    }

    private String resumido(Throwable throwable) {
        String simpleName = throwable.getClass().getSimpleName();
        return throwable.getMessage() != null ? throwable.getMessage() : simpleName;
    }

    public static class ErrorResponse {
        private final int codigo;
        private final String mensagem;
        private final ArrayList<String> detalhes;

        public ErrorResponse(int codigo, String mensagem, ArrayList<String> detalhes) {
            this.codigo = codigo;
            this.mensagem = mensagem;
            this.detalhes = detalhes;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getMensagem() {
            return mensagem;
        }

        public ArrayList<String> getDetalhes() {
            return detalhes;
        }
    }
}
