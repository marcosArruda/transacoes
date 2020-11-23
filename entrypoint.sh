#!/usr/bin/env bash

if [ "$DEBUG_PORT" = "9999" ]; then
    java -cp app:app/lib/* br.com.marcos.transacoes.TransactionsApplication
else
    java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:${DEBUG_PORT} -Djava.security.egd=file:/dev/./urandom -cp app:app/lib/* br.com.marcos.transacoes.TransactionsApplication
fi

