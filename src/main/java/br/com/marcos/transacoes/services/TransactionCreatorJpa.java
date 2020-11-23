package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.infra.persistence.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.lang.String.format;

public class TransactionCreatorJpa implements TransactionCreator{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCreatorJpa.class);

    @NotNull
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionCreatorJpa(final TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Mono<Transaction> createTransaction(@Valid Transaction transaction) {
        LOGGER.info(format("Received request to create a transaction %s;", transaction.toString()));
        return transactionRepository.insert(transaction)
                .doOnError(err -> LOGGER.error("Semthing went wrong on inserting the transaction!",  err))
                .onErrorReturn(new Transaction(999999, 1, -1L, LocalDateTime.now(), 1));
    }
}
