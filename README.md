# ImageServer
HTTP Server for Serving Images from storage device to website. 

The goal of this project is to build a robust falt-tolerant API for serving images from my person webserver to my personal website
and making it easy to back up photos to different locations either on the cloud or home servers.


### Build and Deploy
This app is designed to run inside of a docker container. To build the container one must be in the root directory of this project.

Build:
  1. `mvn clean install`
  2. `docker build -f DockerFile -t imageserver .`

Running the Container:
  1. `docker run -p 8080:8080 --name=imagerest --link imagedb:imagedb -d imageserver  -rm `


To test to see if the application is running correct, a sample image is included in the repo. The docker container is 
bound on port 8080 to your local machine. You can go to http://localhost:8080/image?id=0 and see the example image. 


## Creating PostgresDB backend
The app uses a standard postgres implementation. One can pull the container `postgres` with the command
`docker pull postgres`


To run the container,
`docker run -p 5432:5432 -e POSTGRES_PASSWORD=<pw> --name imagedb -d postgres -rm`

### Backing up container
To create a backup of the container run
`docker exec -t <container ID>  pg_dumpall -c -U postgres > dump_`date +%d-%m-%Y"_"%H_%M_%S`.sql\`

### Restoring from backup
To restore a new container from a previous backup
`cat dump_14-12-2018_13_34_44.sql | docker exec -i <container ID> psql -U postgres`



## Database Design

Catalog Table Schema 

| uploadTime | uuid  | name          |
| :--------: | :---: | :-----------: |
| TIMESTAMP  | UUID  | TEXT NOT NULL |


Thumbnail Metadata Table Schema 

| path          | checksum      | uuid  | name          | type          | timeTaken |
| :-----------: | :-----------: | :---: | :-----------: | :-----------: | :-------: |
| TEXT NOT NULL | TEXT NOT NULL | UUID  | TEXT NOT NULL | TEXT NOT NULL | TIMESTAMP |


Full-size Metadata Table Schema

| path          | checksum      | uuid  | name          | type          | timeTaken |
| :-----------: | :-----------: | :---: | :-----------: | :-----------: | :-------: |
| TEXT NOT NULL | TEXT NOT NULL | UUID  | TEXT NOT NULL | TEXT NOT NULL | TIMESTAMP |


## Test with CURL command
To upload a photo to the database, use the command:
`curl -X POST  -F "file=@<filename>" -H "Content-Type:multipart/form-data" http://localhost:8080/image/upload`

## Future Status
This app will eventually be integrated with a database backend for storing the image location and various metadata. Also images will be
cached on a Redis server in another container. 




# Road Map and Stories
    - API to upload one image
        - API to upload range of images
        - Read Metadata from image uploaded
        - Handle jpeg
        - Saves image to configured place on either the system or container
        - Updates Database with metadata
        - Handles .NEF
    - API to get one image
        - API to get range of images
    - Database schema for uploading and cataloging image
    - Retrieving image based on date
    - Color profiles so query by color and like images
    - ML on images for categorizing
 
 
 Road Map: 
    
    1. API to upload one image (JPEG)
        a. Saves image to configured place on either the system or container
        b. Read Metadata from image uploaded
        c. Updates Database with basic metadata 
            - Date Taken
            - Date Uploaded
            - Checksum of image
    2. API to get one image (JPEG)
        a. Retrieve image based on date
        b. Retrieve image based on last uploaded
        c. Retrieve image small
        d. Retrieve image full sized
    3. API to upload one image (NEF) {Implements all of 1's stories)
    4. API to get one image (NEF) {Implements all of 2's stories}
    5. Categorize Images
        a. Use Color profile
        b. Use edge transforms
        c. Resnet models (?)
        
