package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Transaction;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface TransactionCreator {
    Mono<Transaction> maybeCreateTransaction(@Valid final Transaction transaction);
}
