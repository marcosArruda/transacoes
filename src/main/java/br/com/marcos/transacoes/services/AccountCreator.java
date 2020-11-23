package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface AccountCreator {
    Mono<Account> createAccount(@Valid final Account account);
}
