# n26-coding-challenge

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
