#!/usr/bin/env bash

curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"owner_name": "Marcos", "document_number": "999999"}' http://localhost:8080/accounts

sleep 2

curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"account_id": "Marcos", "operation_type_id": "999999", "amount": 2345}' http://localhost:8080/transactions



transaction_id
account_id
operation_type_id
amount
event_date
installment_num