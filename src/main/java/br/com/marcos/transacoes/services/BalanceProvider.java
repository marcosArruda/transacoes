package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.api.resources.TransactionVerified;
import reactor.core.publisher.Mono;

public interface BalanceProvider {
    Mono<TransactionVerified> preApply(final Transaction transaction);
}
