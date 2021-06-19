# n26-coding-challenge

## Description

## Building, Test, and Run

#### Docker (recommended)

Installation steps may differ from machine to machine. Please follow instructions accordingly:
For reference, I have built this project using:
- Docker 20.10.7 @ https://docs.docker.com/get-docker/

A `Dockerfile` has been added to the project's root directory, easing dependency management and deployment.
- Building the Docker image: `docker build . -t n26`
- Running the Docker image: `docker run n26 -p 8080`

---

A `docker-compose` file has (also) been added to the project's root directory.
This time, the intention is to allow other developers to code within a containerized environment.
This is a quickest way to develop, at the expense of not having a really good integration with IDEs and text editors...
- Developing from within a "*N26 container*": `docker-compose run --service-ports dev /bin/bash`
  - Once inside the container, all _Maven_ commands apply (e.g. `mvn test`)

#### Locally

Installation steps may differ from machine to machine. Please follow instructions accordingly:
For reference, I have built this project using:
- Java 1.8 ([OpenJDK8](https://openjdk.java.net/install/)) @ https://www.java.com/download/
- Maven 3.8.1 @ https://maven.apache.org/)
- Linux - Manjaro 21.0.7 (64bit)

**Commands:**
- Building the project (with locally installed dependencies): `mvn clean install`
- Testing the project (with locally installed dependencies): `mvn test`
- Running the project (with locally installed dependencies): `mvn test`


## Design

## Requirements
### Hard
- [x] Application runs in Maven
  - [ ] `mvn clean install` and `mvn clean integration-test` complete succesfully
    - > This project uses the same `pom.xml` present in the "skeleton" provided by N26. Nothing has been changed
- [x] API is thread-safe (i.e. supports concurrent requests)
  - > `TransactionRepository` is built around a `ConcurrentHashMap` 
- [x] Works without a database (including in-memory databases)
  - > See`InMemoryTransactionRepository` class
- [x] Service does **not** store all transactions in memory all the time
- [x] Irrelevant transactions are discarded
  - > See `ScheduleTransactionForExpiration` use case
- [x] Project has unit tests
  - > Lots of them!
- [x] No changes have been done to integration tests @ `src/it`
- [ ] Solution is production-ready

### Soft

#### Insert Transactions

Transactions are stored in a `ConcurrentHashMap`.
An expiration mechanism has been build and is called after each insertion.
Once transactions are no longer useful (i.e. older than 1 minute) they get discarded from the storage.

- [x] Time Complexity is O(1)

#### Get Statistics

All relevant transactions are queried using the `TransactionRepository` abstraction.
`Statistics` are computed on-demand (but can be hooked to an "eventing" system at a later point in time, so as to provide instant results).

- [ ] Time Complexity is O(1)

#### Remove Transactions
Transactions are stored in a `ConcurrentHashMap`.

- [x] Time Complexity is O(1)
