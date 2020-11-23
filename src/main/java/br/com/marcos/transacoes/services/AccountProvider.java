package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.api.resources.AccountCriteria;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountProvider {
    String ERROR_MSG = "Couldn't run query on %s collection";

    Mono<List<Account>> findMany(AccountCriteria criteria);
    Mono<Account> findOne(long accountId);
}
