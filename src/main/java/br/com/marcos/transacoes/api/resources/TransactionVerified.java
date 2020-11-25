package br.com.marcos.transacoes.api.resources;

public class TransactionVerified {
    final private Account account;
    final private boolean valid;

    public TransactionVerified(Account account, boolean valid) {
        this.account = account;
        this.valid = valid;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isValid() {
        return valid;
    }
}
