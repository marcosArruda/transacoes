package br.com.marcos.transacoes.api;

import br.com.marcos.transacoes.api.resources.Transaction;
import br.com.marcos.transacoes.api.resources.TransactionCriteria;
import br.com.marcos.transacoes.services.TransactionCreator;
import br.com.marcos.transacoes.services.TransactionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TransactionsController.TRANSACTIONS_URI)
public class TransactionsController {
    public static final String TRANSACTIONS_URI = "/transactions";

    private final TransactionCreator transactionCreator;
    private final TransactionProvider transactionProvider;

    @Autowired
    public TransactionsController(final TransactionCreator transactionCreator, final TransactionProvider transactionProvider){
        this.transactionCreator = transactionCreator;
        this.transactionProvider = transactionProvider;
    }

    @PostMapping
    private Mono<ResponseEntity<Transaction>> postTransaction(@NotNull @RequestBody final Transaction transaction){
        return transactionCreator.createTransaction(transaction)
                .map(trz -> new ResponseEntity<>(trz, HttpStatus.OK));
    }

    @GetMapping
    private Mono<ResponseEntity<List<Transaction>>> findTransactions(@RequestParam(value = Transaction.TRANSACTION_ID_JSON_FIELD, required = false) final UUID transactionId,
                                                     @RequestParam(value = Transaction.ACCOUNT_ID_JSON_FIELD, required = false) final Long accountId,
                                                     @RequestParam(value = Transaction.OPERATION_TYPE_ID_JSON_FIELD, required = false) final Integer operationTypeId,
                                                     @RequestParam(value = Transaction.AMOUNT_JSON_FIELD, required = false) final Long amount,
                                                     @RequestParam(value = Transaction.EVENT_DATE_JSON_FIELD, required = false) final LocalDateTime eventDate,
                                                     @RequestParam(value = Transaction.INSTALLMENT_NUM_JSON_FIELD, required = false) final Integer installmentNum){
        return transactionProvider.findMany(new TransactionCriteria(transactionId, accountId, operationTypeId, amount, eventDate, installmentNum))
                .map(list -> new ResponseEntity<>(list, HttpStatus.OK));
    }
}
