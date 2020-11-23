package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, Long>, AccountRepositoryCustom {
}
