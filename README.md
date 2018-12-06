# ImageServer
HTTP Server for Serving Images from storage device to website. 

The goal of this project is to build a robust falt-tolerant API for serving images from my person webserver to my personal website
and making it easy to back up photos to different locations either on the cloud or home servers.


### Build and Deploy
This app is designed to run inside of a docker container. To build the container one must be in the root directory of this project.

Steps:
  1. `mvn clean install`
  2. `docker build -f DockerFile -t imageserver .`

Running the container:
  1. `docker run -p 8080:8080 imageserver`


## Future Status
This app will eventually be integrated with a database backend for storing the image location and various metadata. Also images will be
cached on a Redis server in another container. 
