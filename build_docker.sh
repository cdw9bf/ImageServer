#! /bin/bash -ex

mvn clean install
docker build -f Dockerfile -t imageserver .
