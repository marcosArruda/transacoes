package br.com.marcos.transacoes.api;

import br.com.marcos.transacoes.api.resources.Account;
import br.com.marcos.transacoes.config.profiles.AppProfiles;
import br.com.marcos.transacoes.infra.persistence.AccountRepository;
import br.com.marcos.transacoes.services.SeqGeneratorService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static br.com.marcos.transacoes.api.AccountsController.ACCOUNTS_URI;

/**
 * !!IMPORTANT!!
 * The tests below are INTEGRATION TESTS that NEED a mongodb server running.
 * I´ve provided one for you in the docker-compose.yml
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(AppProfiles.TESTING)
public class AccountsControllerIntegrationTest {
    private static final Account newAccount = new Account("Marcos", "123456789");
    private static final Account persistedAccount = new Account(1, "Marcos", "123456789", 0);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SeqGeneratorService seqGenerator;

    private WebTestClient testClient;

    @Before
    public void setup() {

        /*
        MongodStarter starter = MongodStarter.getDefaultInstance();

        int port = 50960;
        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            MongodProcess mongod = mongodExecutable.start();

            try (MongoClient mongo = new MongoClient("localhost", port)) {
                DB db = mongo.getDB("test");
                DBCollection col = db.createCollection("testCol", new BasicDBObject());
                col.save(new BasicDBObject("testDoc", new Date()));
            }

        } finally {
            if (mongodExecutable != null)
                mongodExecutable.stop();
        }
        */

        testClient = WebTestClient.bindToApplicationContext(context).build();
        System.out.println("THESE TESTS NEED A MONGODB INSTANCE ONLINE TO RUN. THEY ARE INTEGRATION TESTS!!");
        accountRepository.deleteAll().toProcessor().block();
        seqGenerator.resetAllSequences();
    }


    @Test
    public void postAccount() {
        accountRepository.deleteAll().toProcessor().block();
        seqGenerator.resetAllSequences();
        final Account response =
                this.testClient.post()
                        .uri(ACCOUNTS_URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .bodyValue(newAccount)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(Account.class)
                        .returnResult()
                        .getResponseBody();

        Assertions.assertThat(response).isEqualToIgnoringGivenFields(newAccount, Account.ACCOUNT_ID_JAVA_FIELD);
        seqGenerator.resetAllSequences();
        accountRepository.deleteAll().toProcessor().block();
    }

    @Test
    public void getAccount() {
        accountRepository.deleteAll().toProcessor().block();
        seqGenerator.resetAllSequences();

        this.testClient.post()
                .uri(ACCOUNTS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .syncBody(newAccount)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .returnResult()
                .getResponseBody();

        final Account response = this.testClient.get()
                .uri(ACCOUNTS_URI + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isEqualTo(persistedAccount);
        accountRepository.deleteAll().toProcessor().block();
        seqGenerator.resetAllSequences();
    }
}
