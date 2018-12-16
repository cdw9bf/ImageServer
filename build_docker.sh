#! /bin/bash -ex

mvn clean install
docker build -f DockerFile -t imageserver .
