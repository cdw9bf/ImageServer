#! /bin/bash -ex

mvn clean install
# TODO: Find why this isn't working
docker build -f Dockerfile -t imageserver .
