package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<Account> findAll(Query query) {
        return reactiveMongoTemplate.find(query, Account.class);
    }
}
