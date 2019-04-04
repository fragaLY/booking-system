# The booking-system template.

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)

[Java Docs](https://fragaly.github.io/booking-system/)
[Docker_Hub](https://hub.docker.com/r/fragaly/booking-system)

* To restore data in [Atlas](https://www.mongodb.com/cloud/atlas) cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

* To run the application using [gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html) 
```
gradlew bootRun
``` 
OR run the [Docker](https://www.docker.com/resources/what-container) image:
```
docker run -p 8080:8080 fragaly/booking-system -m 512M --memoryreservation 256M
```
Feel free follow the [link](localhost:8080 "Application Homepage") and check the REST API.
