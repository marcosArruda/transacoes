package br.com.marcos.transacoes.api.resources;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Objects;
import java.util.StringJoiner;

public class AccountCriteria {

    private final Long accountId;
    private final String ownerName;
    private final String documentNumber;


    public AccountCriteria(final Long accountId, final String ownerName, final String documentNumber) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.documentNumber = documentNumber;
    }

    public Query buildQuery() {
        final Query query = new Query();

        if (accountId != null)
            query.addCriteria(Criteria.where(Account.ACCOUNT_ID_JAVA_FIELD).is(accountId));

        if (ownerName != null && !ownerName.isBlank())
            query.addCriteria(Criteria.where(Account.OWNER_NAME_JAVA_FIELD).is(ownerName));

        if (documentNumber != null && !documentNumber.isBlank())
            query.addCriteria(Criteria.where(Account.DOCUMENT_NUMBER_JAVA_FIELD).is(documentNumber));

        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCriteria that = (AccountCriteria) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(ownerName, that.ownerName) &&
                Objects.equals(documentNumber, that.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, ownerName, documentNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountCriteria.class.getSimpleName() + "[", "]")
                .add("accountId=" + accountId)
                .add("ownerName='" + ownerName + "'")
                .add("documentNumber='" + documentNumber + "'")
                .toString();
    }
}
