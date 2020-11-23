package br.com.marcos.transacoes.config.profiles;

import br.com.marcos.transacoes.config.AWSConfigConstants;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class TransacoesDBConfig extends AbstractReactiveMongoConfiguration {

    @Value(AWSConfigConstants.MongoDB.Events.URL)
    private String dbUrl;

    @Value(AWSConfigConstants.MongoDB.Events.NAME)
    private String dbName;

    @Value(AWSConfigConstants.MongoDB.Events.PORT)
    private Integer dbPort;

    @Value(AWSConfigConstants.MongoDB.Events.USERNAME)
    private String dbUsername;

    @Value(AWSConfigConstants.MongoDB.Events.PASSWORD)
    private String dbPassword;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(String.format("mongodb://%s:%s@%s:%d", dbUsername, dbPassword, dbUrl, dbPort));
    }
}
//@ActiveProfiles(AppProfiles.TESTING)