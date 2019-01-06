#!/bin/bash -ex
password=${1}
backup=${2}

docker run -p 5432:5432 -e POSTGRES_PASSWORD=${password} --name imagedb -d postgres -rm
container_id=`docker inspect --format="{{.Id}}" imagedb`
# Sleep for 5 seconds. TODO: Remove this and wait for image to be running instead of just waiting a random about of time.
sleep 5

cat ${backup} | docker exec -i ${container_id} psql -U postgres
echo "Initialized Docker"
