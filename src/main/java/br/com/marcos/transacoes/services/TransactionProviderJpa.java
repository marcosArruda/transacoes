package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.api.resources.TransactionCriteria;
import br.com.marcos.transacoes.infra.persistence.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionProviderJpa implements TransactionProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProviderJpa.class);

    @NotNull
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionProviderJpa(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Mono<List<Transaction>> findMany(@Valid @NotNull final TransactionCriteria criteria) {
        return transactionRepository.findAll(criteria.buildQuery())
                .collectList()
                .doOnError(err -> LOGGER.error(String.format(ERROR_MSG, Transaction.COLLECTION), err));
    }

    @Override
    public Mono<List<Transaction>> findByAccountId(long accountId) {
        return transactionRepository.findAll(new TransactionCriteria(accountId).buildQuery())
                .collectList()
                .doOnError(err -> LOGGER.error(String.format(ERROR_MSG, Transaction.COLLECTION), err));
    }

    @Override
    public Mono<Transaction> findSingle(UUID transactionId) {
        return transactionRepository.findAll(new TransactionCriteria(transactionId).buildQuery())
                .doOnError(err -> LOGGER.error(String.format(ERROR_MSG, Transaction.COLLECTION), err))
                .single();

    }


}
