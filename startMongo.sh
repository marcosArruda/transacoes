#!/usr/bin/env bash

docker-compose down
docker system prune -f
docker volume prune -f
docker-compose up -d mongo
