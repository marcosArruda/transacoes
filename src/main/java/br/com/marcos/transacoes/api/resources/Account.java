package br.com.marcos.transacoes.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Document(collection = Account.COLLECTION)
public class Account {
    @Transient
    public static final String SEQUENCE_NAME = "accounts_sequence";
    @Transient
    public static final String COLLECTION = "accounts";

    @Transient
    public static final String ACCOUNT_ID_JSON_FIELD = "account_id";
    @Transient
    public static final String ACCOUNT_ID_JAVA_FIELD = "accountId";

    @NotNull
    @Id
    private long accountId;

    @Transient
    public static final String OWNER_NAME_JSON_FIELD = "owner_name";
    @Transient
    public static final String OWNER_NAME_JAVA_FIELD = "ownerName";
    @NotBlank
    private String ownerName;

    @Transient
    public static final String DOCUMENT_NUMBER_JSON_FIELD = "document_number";
    @Transient
    public static final String DOCUMENT_NUMBER_JAVA_FIELD = "documentNumber";
    @NotBlank
    private String documentNumber;

    @Transient
    public static final String BALANCE_JSON_FIELD = "balance";
    @Transient
    public static final String BALANCE_JAVA_FIELD = "balance";

    private long balance;



    public Account(long accountId){
        this.accountId = accountId;
    }

    @PersistenceConstructor
    public Account(final long accountId,
                   final String ownerName,
                   final String documentNumber,
                   final long balance){
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.documentNumber = documentNumber;
        this.balance = balance;
    }

    @JsonCreator
    public Account(@JsonProperty(OWNER_NAME_JSON_FIELD) final String ownerName,
                   @JsonProperty(DOCUMENT_NUMBER_JSON_FIELD) final String documentNumber){
        this.ownerName = ownerName;
        this.documentNumber = documentNumber;
    }

    @JsonProperty(ACCOUNT_ID_JSON_FIELD)
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(final long accountId){
        this.accountId = accountId;
    }

    @JsonProperty(OWNER_NAME_JSON_FIELD)
    public String getOwnerName() {
        return ownerName;
    }

    @JsonProperty(DOCUMENT_NUMBER_JSON_FIELD)
    public String getDocumentNumber() {
        return documentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                ownerName.equals(account.ownerName) &&
                documentNumber.equals(account.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, ownerName, documentNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("accountId=" + accountId)
                .add("ownerName='" + ownerName + "'")
                .add("documentNumber='" + documentNumber + "'")
                .toString();
    }
}
