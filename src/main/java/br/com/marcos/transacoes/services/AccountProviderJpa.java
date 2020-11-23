package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.api.resources.AccountCriteria;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class AccountProviderJpa implements AccountProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountProviderJpa.class);

    @NotNull
    private final AccountRepository accountRepository;

    @Autowired
    public AccountProviderJpa(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<List<Account>> findMany(@Valid @NotNull final AccountCriteria criteria) {
        return accountRepository.findAll(criteria.buildQuery())
                .collectList()
                .doOnError(err -> LOGGER.error(String.format(ERROR_MSG, Account.COLLECTION), err));
    }

    @Override
    public Mono<Account> findOne(long accountId) {
        return accountRepository.findOne(Example.of(new Account(accountId)))
                .doOnError(err -> LOGGER.error(String.format(ERROR_MSG, Account.COLLECTION), err));
    }
}
