!#/bin/bash

podman network create mynet
podman run --rm --name gas-station-company -d -p 8080:8080 --net mynet gas-station-company 
podman run --rm -d -e AMQ_USER=admin -e AMQ_PASSWORD=public -p 1883:1883 -p 8161:8161 --name artemis --net mynet quay.io/artemiscloud/activemq-artemis-broker
podman run --rm --name gas-station-factory -d -p 8081:8081 -e mp.messaging.outgoing.sensors.host=artemis -e quarkus.rest-client.gas-station-company.url=http://gas-station-company --net mynet gas-station-factory

