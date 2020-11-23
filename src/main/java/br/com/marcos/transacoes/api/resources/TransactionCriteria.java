package br.com.marcos.transacoes.api.resources;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class TransactionCriteria {

    private final UUID transactionId;
    private final Long accountId;
    private final Integer operationTypeId;
    private final Long amount;
    private final LocalDateTime eventDate;
    private final Integer installmentNum;


    public TransactionCriteria(final UUID transactionId, final Long accountId, final Integer operationTypeId, final Long amount,
                               final LocalDateTime eventDate, final Integer installmentNum) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate;
        this.installmentNum = installmentNum;
    }

    public TransactionCriteria(final Long accountId) {
        this.transactionId = null;
        this.accountId = accountId;
        this.operationTypeId = null;
        this.amount = null;
        this.eventDate = null;
        this.installmentNum = null;
    }

    public TransactionCriteria(final UUID transactionId) {
        this.transactionId = transactionId;
        this.accountId = null;
        this.operationTypeId = null;
        this.amount = null;
        this.eventDate = null;
        this.installmentNum = null;
    }

    public Query buildQuery() {
        final Query query = new Query();

        if (transactionId != null)
            query.addCriteria(Criteria.where(Transaction.TRANSACTION_ID_JAVA_FIELD).is(transactionId));

        if (accountId != null)
            query.addCriteria(Criteria.where(Transaction.ACCOUNT_ID_JAVA_FIELD).is(accountId));

        if (operationTypeId != null && operationTypeId > 0 && operationTypeId < 5)
            query.addCriteria(Criteria.where(Transaction.OPERATION_TYPE_ID_JAVA_FIELD).is(operationTypeId));

        if (amount != null)
            query.addCriteria(Criteria.where(Transaction.AMOUNT_JAVA_FIELD).is(amount));

        if (eventDate != null)
            query.addCriteria(Criteria.where(Transaction.EVENT_DATE_JAVA_FIELD).is(eventDate));

        if (installmentNum != null && installmentNum > 0)
            query.addCriteria(Criteria.where(Transaction.INSTALLMENT_NUM_JAVA_FIELD).is(installmentNum));

        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCriteria that = (TransactionCriteria) o;
        return Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(operationTypeId, that.operationTypeId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(eventDate, that.eventDate) &&
                Objects.equals(installmentNum, that.installmentNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountId, operationTypeId, amount, eventDate, installmentNum);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionCriteria.class.getSimpleName() + "[", "]")
                .add("transactionId=" + transactionId)
                .add("accountId=" + accountId)
                .add("operationTypeId=" + operationTypeId)
                .add("amount=" + amount)
                .add("eventDate=" + eventDate)
                .add("installmentNum=" + installmentNum)
                .toString();
    }
}
