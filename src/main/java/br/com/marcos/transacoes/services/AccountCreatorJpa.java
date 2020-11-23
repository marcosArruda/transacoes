package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.lang.String.format;

@Service
public class AccountCreatorJpa implements AccountCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCreatorJpa.class);

    @NotNull
    private final AccountRepository accountRepository;

    @Autowired
    public AccountCreatorJpa(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> createAccount(@Valid final Account account) {
        LOGGER.info(format("Received request to create an account %s;", account.toString()));
        return accountRepository.insert(account)
                .doOnError(err -> LOGGER.error("Semthing went wrong on inserting the account!",  err))
                .onErrorReturn(new Account("ERROR", "-1"));
    }
}
