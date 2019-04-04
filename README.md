# The booking-system template.

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)
[![Java Docs](https://png.pngtree.com/element_pic/17/02/09/8cd5cb93144a40bb4fdc66fb64b41ae5.jpg)](https://fragaly.github.io/booking-system/)

* Javadocs are available [here](https://fragaly.github.io/booking-system/).

* To restore data in [Atlas](https://www.mongodb.com/cloud/atlas) cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

* To run the application using [gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html) the next command and follow the [link](localhost:8080 "Application Homepage"): 
```
gradlew bootRun
``` 
or run the [Docker](https://www.docker.com/resources/what-container):
```
docker run -p 8080:8080 fragaly/booking-system --memoryreservation 256M
```
**!** *-m is 512M set on the build stage, please override it if you need less or more memory*
