package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import br.com.marcos.transacoes.infra.persistence.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.lang.String.format;

@Service
public class TransactionCreatorJpa implements TransactionCreator{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCreatorJpa.class);

    @NotNull
    private final TransactionRepository transactionRepository;

    @NotNull
    private final AccountRepository accountRepository;

    @NotNull
    private final BalanceProvider balanceProvider;

    @Autowired
    public TransactionCreatorJpa(final TransactionRepository transactionRepository, final BalanceProvider balanceProvider, final AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.balanceProvider = balanceProvider;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Transaction> maybeCreateTransaction(@Valid Transaction transaction) {
        LOGGER.info(format("Received request to create a transaction %s;", transaction.toString()));

        // 1 verificar o saldo
        return balanceProvider.preApply(transaction)
                // 2 sensibilizar o saldo se aprovado no 1
                .map(validated -> {
                    if(validated.isValid()){
                        //na pratica nao faremos isso.
                        var acc = accountRepository
                                .findById(transaction.getAccountId())
                                .toProcessor().block();
                        acc.sensibiliza(transaction.getAmount());
                    }
                    return validated;
                })
                // 3 persistir a transacao;
        .flatMap(validated -> {
            if (validated.isValid()) {
                return transactionRepository.insert(transaction)
                        .doOnError(err -> LOGGER.error("Semthing went wrong on inserting the transaction!", err))
                        .onErrorReturn(new Transaction(999999, 1, -1L, LocalDateTime.now(), 1));
            }
            return Mono.just(transaction);
        });
    }
}
