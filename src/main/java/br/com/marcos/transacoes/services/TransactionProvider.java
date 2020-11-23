package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.api.resources.TransactionCriteria;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface TransactionProvider {

    String ERROR_MSG = "Couldn't run query on %s collection";

    Mono<List<Transaction>> findMany(final TransactionCriteria criteria);
    Mono<List<Transaction>> findByAccountId(final long accountId);
    Mono<Transaction> findSingle(final UUID transactionId);
}
