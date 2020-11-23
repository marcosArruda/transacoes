package br.com.marcos.transacoes;

import br.com.marcos.transacoes.config.profiles.AppProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(AppProfiles.TESTING)
public class TransactionsApplicationTests {

    @Test
    public void contextLoads() {
    }
}

