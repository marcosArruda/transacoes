package br.com.marcos.transacoes.services;

import br.com.marcos.transacoes.api.resources.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SeqGeneratorService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public SeqGeneratorService(final ReactiveMongoTemplate reactiveMongoTemplate){
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public long generateSequence(final String seqName) {
        DatabaseSequence counter = getSeq(seqName);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public long resetAllSequences(){
        return reactiveMongoTemplate.findAllAndRemove(
                query(where("_id").exists(true)), DatabaseSequence.class
        ).toStream().count();
    }

    /**
     * This method just find the sequence requested, increment it, upsert and finally return it..
     * @param seqName the name of the sequence
     * @return the pseudo-optional DatabaseSequence
     */
    private DatabaseSequence getSeq(final String seqName){
        return reactiveMongoTemplate.findAndModify(
                query(where("_id").is(seqName)), new Update().inc("seq",1),
                options().returnNew(true).upsert(true), DatabaseSequence.class
        ).toProcessor().block();
    }
}
