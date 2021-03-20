#!/bin/bash

if [[ ! $(docker ps | grep ml-geo-api-db) ]];
then
    printf "Error: falta instanciar el container de la db! \n"
    printf "ejecutar: ./run-db.sh \n"
    exit 1
fi

if [[ ! -f .env ]];
then
    printf "Error: falta configurar las variables de entorno en el archivo .env \n"
    exit 1
fi

if [[ ! $(docker images | grep ml-geo-api) || $1 == "--build" ]];
then
    printf "creando imagen... \n"
    docker build -t ml-geo-api .
fi

printf "corriendo container... \n"

docker run --rm -it \
    --network ml \
    -p 127.0.0.1:8085:8080 \
    --env-file ./.env \
    --name ml-geo-api \
    ml-geo-api
