# GeoApi

Aplicación que dada una dirección IP, encuentre el país al que pertenece, y
muestra:
- El nombre y código ISO del país
- Los idiomas oficiales del país
- Hora(s) actual(es) en el país (si el país cubre más de una zona horaria, mostrar
  todas)
- Distancia estimada entre Buenos Aires y el país, en km.
- Moneda local, y su cotización actual en dólares (si está disponible)

Tambien se puede consultar a modo de reporte
- Distancia más lejana a Buenos Aires desde la cual se haya consultado el servicio
- Distancia más cercana a Buenos Aires desde la cual se haya consultado el servicio
- Distancia promedio de todas las ejecuciones que se hayan hecho del servicio.

### Instrucciones
Si se quiere correr localmente lo que necesitamos es:

- Clonar este repo: https://github.com/plavaggi/GeoExcersice
- Tener Docker instalado

Ejecutar dentro del repo los siquientes comandos:

Primero creamos una network
```sh
docker network create ml
```
Corremos la imagen de la db
```sh
docker run --rm -it --network ml --volume ml-geo-api-db:/var/lib/postgresql/data/ --env POSTGRES_USER=ml --env POSTGRES_PASSWORD=ml --env POSTGRES_DB=ml --name ml-geo-api-db postgres:12.4
```
Creamos la imagen de la api
```sh
docker build -t ml-geo-api .
```
Corremos la imagen de la api
```sh
docker run --rm -it --network ml -p 127.0.0.1:8085:8080 --env-file ./.env --name ml-geo-api ml-geo-api
```
Listo, abrir en navegador a eleccion http://127.0.0.1:8085/

Si estas usando sistema operativo Linux, dentro del repo hay dos script bash para ejecutar todos estos comandos de manera automatica.
Bastaria ejecutar en orden:
```sh
./run-db.sh
```
```sh
./run-api.sh
```

### Interfaz


Trace ip: En http://127.0.0.1:8085/index tenes la interfaz basica de consulta donde se puede buscar la ip deseada.

Reportes: Desde el home de la api tenes en la esquina superior derecha el boton de "Reporte" (http://127.0.0.1:8085/report), que te lleva a los 3 diferentes tipos de consultas.