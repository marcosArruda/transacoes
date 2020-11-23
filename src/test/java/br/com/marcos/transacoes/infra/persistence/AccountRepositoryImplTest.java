package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.api.resources.AccountCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryImplTest {

    private static final Account firstAccount =
            new Account("Marcos Faria Arruda", "123456789");

    private static final AccountCriteria accountCriteria =
            new AccountCriteria(null, null, null);

    @InjectMocks
    private AccountRepositoryImpl accountRepository;

    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void findAllShouldFinish() {
        when(reactiveMongoTemplate.find(accountCriteria.buildQuery(), Account.class))
                .thenReturn(Flux.just(firstAccount));

        StepVerifier
                .create(accountRepository.findAll(accountCriteria.buildQuery()))
                .expectNext(firstAccount)
                .verifyComplete();

        verify(reactiveMongoTemplate, times(1))
        .find(eq(accountCriteria.buildQuery()), eq(Account.class));
    }
}
