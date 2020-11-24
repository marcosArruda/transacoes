package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.api.resources.AccountCriteria;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountProviderJpaTest {

    private static final Account firstAccount = new Account(1, "Marcos Faria Arruda", "123456789", 0);
    private static final AccountCriteria accountCriteria =
            new AccountCriteria(1L, "Marcos Faria Arruda", "123456789");

    @InjectMocks
    private AccountProviderJpa accountProvider;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void runFindManyAccounts() {
        when(accountRepository.findAll(accountCriteria.buildQuery())).thenReturn(Flux.just(firstAccount));

        StepVerifier.create(accountProvider.findMany(accountCriteria))
                .expectNext(List.of(firstAccount))
                .verifyComplete();

        verify(accountRepository, times(1)).findAll(eq(accountCriteria.buildQuery()));
    }

    @Test
    public void runReturnsFailedMonoWhenSearchFails() {
        when(accountRepository.findAll(accountCriteria.buildQuery())).thenReturn(Flux.error(new RuntimeException()));

        StepVerifier.create(accountProvider.findMany(accountCriteria))
                .expectError()
                .verify();

        verify(accountRepository, times(1)).findAll(eq(accountCriteria.buildQuery()));
    }
}
