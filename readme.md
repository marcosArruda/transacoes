# Transações

## O que é?

Este repo almeja implementar o controle transacional de movimentações financêiras de um sistema de pagamentos online. É de conhecimento comum que um sistema completo dessa envergadura é um pouquinho complexo, porém, creio que consegui implementar uma grande parcela das regras que existem em sistemas deste tipo, em várias instituições financeiras. No sistema, existem endpoints para criar uma nova conta, realizar transações financeiras e consultar o saldo bem como as transações (extrado) consolidado.

A stack escolhida é 100% reativa e non-blocking de maneira a prover a melhor performance possível, bem como elasticidade durante "rajadas" de requisições. Para isso, é fundamental utilizar os tipos reativos Mono e Flux que herdam das interfaces da especificação reativa. A implementação é reativa e non-blocking desde a interface de acesso(HTTP) até as camadas que tratam qualquer tipo de I/O e/ou persistência.

## Componentes

- Java 11
- Spring WebFlux
- Spring Data
- Docker
- Suporte ao minikube nativo.

## Dependências

- MongoDB 3.6+ para persistência

## Variáveis de Ambiente

- SPRING_PROFILE
- DB_HOST
- DB_PORT
- DB_NAME
- DB_USERNAME
- DB_PASSWORD

Obs.: Adicionei uma série de estruturas bonus para a facilitar a integração contínua e infraestrutura como código dessa aplicação, entre elas:

- suporte ao [Skaffold](https://skaffold.dev/) para subir diretamente no minikube;
- build da aplicação encapsulado dentro do daemon do docker, de maneira a não exigir NENHUMA dependência na maquina que se deseja usar para buildar, apenas o Docker, obviamente.
- Implementão de Testes unitários de todas as camadas da aplicação (porém, nem todo o código possui teste unitário).
- docker-compose.yml com a dependência do mongodb para subir a aplicação local(e rodar o teste de integração também).
- docker-compose.yml com stack completa do kafka, porém não tive tempo de terminar o meu grande plano de fazer TODA a persistência no kafka :).

## Como rodar?

Para executar a aplicação em modo local, apenas com as dependências(mongodb) em docker:
#
```bash
./startMongo.sh # espere o mongo subir...
gradle clean bootRun -x test
```
#
Para executar a aplicação 100% em docker:
#
```bash
./startMongo.sh # espere o mongo subir...
docker build -t marcos/transacoes:latest .
docker run -d --name transacoes --network mongo-net -p 8080:8080 marcos/transacoes:latest
docker network connect bridge transacoes
```
#
Obs.: Eu **sei** que eu poderia colocar tudo no docker-compose. Mas algumas vezes, gosto de rodar os comandos baixo-nível exatamente para ter certeza do que está sendo executado. A "automação" possui uma parcela de abstração, o que deixa as execuções sem os detalhes necessários e importantes no momento em que estamos realizando um **"troubleshooting"**.
 
Qualquer dúvida, é só entrar em contato.