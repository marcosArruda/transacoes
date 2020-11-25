package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.api.resources.OperationType;
import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.api.resources.TransactionVerified;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Service
public class BalanceProviderJpa implements BalanceProvider{


    @NotNull
    private final AccountRepository accountRepository;

    @Autowired
    public BalanceProviderJpa(final AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public Mono<TransactionVerified> preApply(@NotNull final Transaction transaction) {
        final OperationType op = OperationType.fromId(transaction.getOperationTypeId());

        return accountRepository.findById(transaction.getAccountId())
                .map(a -> verify(a, op, transaction));
    }

    private TransactionVerified verify(Account account, OperationType opType, Transaction transaction){
        var balance = account.getBalance();
        var amount = transaction.getAmount();
        balance += amount;
        return new TransactionVerified(account, !(balance < 0));
    }
}
