package br.com.marcos.transacoes.infra.persistence;

import br.com.marcos.transacoes.api.resources.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, UUID>, TransactionRepositoryCustom {
}
