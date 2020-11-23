package br.com.marcos.transacoes.services;

public class ApplicationException extends RuntimeException {

    public ApplicationException(final Throwable cause) {
        super(cause);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
