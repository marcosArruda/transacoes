package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Account;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public interface AccountRepositoryCustom {

    Flux<Account> findAll(Query query);
}
