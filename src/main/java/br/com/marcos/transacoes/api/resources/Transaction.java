package br.com.marcos.transacoes.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = Transaction.COLLECTION)
public class Transaction {
    @Transient
    public static final String COLLECTION = "transactions";

    @Transient
    public static final String TRANSACTION_ID_JSON_FIELD = "transaction_id";
    @Transient
    public static final String TRANSACTION_ID_JAVA_FIELD = "transactionId";

    @NotNull
    @Id
    private UUID transactionId;

    @Transient
    public static final String ACCOUNT_ID_JSON_FIELD = "account_id";
    @Transient
    public static final String ACCOUNT_ID_JAVA_FIELD = "accountId";

    @NotNull
    private long accountId;

    @Transient
    public static final String OPERATION_TYPE_ID_JSON_FIELD = "operation_type_id";
    @Transient
    public static final String OPERATION_TYPE_ID_JAVA_FIELD = "operationTypeId";
    @Min(1)
    @Max(4)
    @NotNull
    private Integer operationTypeId;

    @Transient
    public static final String AMOUNT_JSON_FIELD = "amount";
    @Transient
    public static final String AMOUNT_JAVA_FIELD = "amount";
    @NotNull
    private Long amount;

    @Transient
    public static final String EVENT_DATE_JSON_FIELD = "event_date";
    @Transient
    public static final String EVENT_DATE_JAVA_FIELD = "eventDate";
    @PastOrPresent
    @NotNull
    private LocalDateTime eventDate;

    @Transient
    public static final String INSTALLMENT_NUM_JSON_FIELD = "installment_num";
    @Transient
    public static final String INSTALLMENT_NUM_JAVA_FIELD = "installmentNum";
    @Positive
    private Integer installmentNum;

    public Transaction(){
    }

    public Transaction(final long accountId){
        this.accountId = accountId;
    }

    @JsonCreator
    public Transaction(@JsonProperty(ACCOUNT_ID_JSON_FIELD) final long accountId,
                       @JsonProperty(OPERATION_TYPE_ID_JSON_FIELD) final int operationTypeId,
                       @JsonProperty(AMOUNT_JSON_FIELD) final Long amount,
                       @JsonProperty(EVENT_DATE_JSON_FIELD) final LocalDateTime eventDate,
                       @JsonProperty(INSTALLMENT_NUM_JSON_FIELD) final Integer installmentNum){
        this.transactionId = UUID.randomUUID();
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate == null ? LocalDateTime.now() : eventDate;
        this.installmentNum = installmentNum;
    }

    @PersistenceConstructor
    public Transaction(final UUID transactionId, final long accountId, final int operationTypeId, final Long amount,
                       final LocalDateTime eventDate, final int installmentNum){
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate;
        this.installmentNum = installmentNum;
    }

    @JsonProperty(TRANSACTION_ID_JSON_FIELD)
    public UUID getTransactionId() {
        return transactionId;
    }

    @JsonProperty(ACCOUNT_ID_JSON_FIELD)
    public long getAccountId() {
        return accountId;
    }

    @JsonProperty(OPERATION_TYPE_ID_JSON_FIELD)
    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    @JsonProperty(AMOUNT_JSON_FIELD)
    public Long getAmount() {
        return amount;
    }

    @JsonProperty(EVENT_DATE_JSON_FIELD)
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    @JsonProperty(INSTALLMENT_NUM_JSON_FIELD)
    public Integer getInstallmentNum() {
        return installmentNum;
    }
}
