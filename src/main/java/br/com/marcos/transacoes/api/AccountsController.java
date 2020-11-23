package br.com.marcos.transacoes.api;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.services.AccountCreator;
import br.com.marcos.transacoes.services.AccountProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(AccountsController.ACCOUNTS_URI)
public class AccountsController {
    public static final String ACCOUNTS_URI = "/accounts";

    private final AccountCreator accountCreator;
    private final AccountProvider accountProvider;

    @Autowired
    public AccountsController(final AccountCreator accountCreator, final AccountProvider accountProvider) {
        this.accountCreator = accountCreator;
        this.accountProvider = accountProvider;
    }

    @PostMapping
    private Mono<ResponseEntity<Account>> postAccount(@NotNull @RequestBody final Account account) {
        return accountCreator.createAccount(account)
                .map(acc -> new ResponseEntity<>(acc, HttpStatus.OK));
    }

    /**
     * Returns a Json model of the Account Data
     *
     * @param accountId A numeric Id of the requested account
     * @return json example:
     * {
     * "account_id": 1,
     * "document_number": "12345678900"
     * }
     */
    @GetMapping("/{accountId}")
    private Mono<ResponseEntity<Account>> getOneAccount(@PathVariable Long accountId) {
        return accountProvider.findOne(accountId)
                .map(output -> new ResponseEntity<>(output, HttpStatus.OK));
    }
}
