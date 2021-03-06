# The booking-system template

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)

| Links        | Description     |
| ------------- |:-------------:|
| [Java Docs](https://fragaly.github.io/booking-system/)     | The latest javadocs |
| [Docker Hub](https://hub.docker.com/r/fragaly/booking-system)   | The docker hub |

> To restore data in [Atlas](https://www.mongodb.com/cloud/atlas) cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

# How to up the application:
* > To up the application using [gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html) run the script and follow the [link](http://localhost:8080): ```gradlew bootRun``` 

*(!) If you are using Windows, please, switch to* **master-windows** *branch.*

* > To up with [Docker Compose](https://docs.docker.com/compose/) run the script:

|Script| Links        | Description     |
|----| ------------- |:-------------:|
|```gradle docker```|   | Create images from Dockerfiles|
|```docker-compose up -d```| [REST API](http://localhost:8080) | Compose the containers and up application |
| | [Statistics](http://localhost:8081/containers/)   | Google CAdvisor |
| | [Kibana](http://localhost:5601)   | Kibana |

* > Work with a swarm:

|Run Swarm| Leave Swarm|
|---------| -----------|
| ```swarm/swarm-cluster.sh```| ```docker swarm leave --x``` |

# How to debug the application:

* > To debug the application feel free to use the exposed 5080 port. The example for IntelliJ IDEA is below:

![debug](screenshot/debug.jpg)

* > To analyze the application in Java VisualVM feel free to use the exposed 38080 port. The example:

![jvm](screenshot/visualvm_jmx_connection.jpg)