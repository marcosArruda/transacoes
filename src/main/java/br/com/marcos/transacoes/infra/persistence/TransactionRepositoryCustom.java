package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Transaction;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public interface TransactionRepositoryCustom {

    Flux<Transaction> findAll(Query query);
}
