#!/bin/bash

if [[ ! $(docker network ls | grep ml) ]];
then
    printf "creando docker network... \n"
    docker network create ml
fi

printf "corriendo container db... \n"

docker run --rm -it \
    --network ml \
    --volume ml-geo-api-db:/var/lib/postgresql/data/ \
    --env POSTGRES_USER=ml \
    --env POSTGRES_PASSWORD=ml \
    --env POSTGRES_DB=ml \
    --name ml-geo-api-db \
    postgres:12.4
