package br.com.marcos.transacoes.infra.hibernate.listeners;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.services.SeqGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountModelListener extends AbstractMongoEventListener<Account> {
    private final SeqGeneratorService seqGenerator;

    @Autowired
    public AccountModelListener(final SeqGeneratorService seqGenerator){
        this.seqGenerator = seqGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Account> event) {
        if(event.getSource().getAccountId() < 1){
            event.getSource().setAccountId(seqGenerator.generateSequence(Account.SEQUENCE_NAME));
        }
    }
}
