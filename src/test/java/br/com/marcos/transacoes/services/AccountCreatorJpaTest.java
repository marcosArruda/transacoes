package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreatorJpaTest {

    private static final Account newAccount = new Account("Marcos Faria Arruda", "123456789");
    private static final Account savedAccount = new Account("Marcos Faria Arruda", "123456789");

    @InjectMocks
    private AccountCreatorJpa accountCreatorJpa;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void createAccountShouldComplete() {
        savedAccount.setAccountId(1);
        when(accountRepository.insert(any(Account.class))).thenReturn(Mono.just(savedAccount));
        accountCreatorJpa.createAccount(newAccount);
        verify(accountRepository, times(1)).insert(eq(newAccount));
    }
}
