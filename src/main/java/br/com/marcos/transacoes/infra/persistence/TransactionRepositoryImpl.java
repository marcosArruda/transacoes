package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<Transaction> findAll(Query query) {
        return reactiveMongoTemplate.find(query, Transaction.class);
    }
}
