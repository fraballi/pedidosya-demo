# Microservices Demo for PedidosYa

## Introduction

>### Technologies
>
> - Docker v. 18.09.3
> - Apache Cassandra v. 3.11.4
> - Apache Kafka v. 2.11.0
> - SpringBoot v. 2.1.5

>### Packages
>
> - com.pedidosya  (e.g Entry-point SpringBoot Application)
> - com.pedidosya.domain  (e.g domain models)
> - com.pedidosya.repositories
> - com.pedidosya.services
> - com.pedidosya.controllers
> - com.pedidosya.tests

>### Keyspace/Tables
> *Keyspace*: **pedidosya_ks**
> *Tables*:
> - OpinionByUser
> - OpinionByStore
> - OpinionByShopping
> - StoreRatingAverage 
>

```bash
cqlsh> SELECT * FROM pedidosya_ks.opinion_by_user;

 userid                               | createdat  | rating | storeid                              | id                                   | comments       | shoppingid
--------------------------------------+------------+--------+--------------------------------------+--------------------------------------+----------------+--------------------------------------
 f04665b0-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      3 | edf5bee1-895d-11e9-a2ba-c3b5389c81dc | f04665b1-895d-11e9-a2ba-c3b5389c81dc |    Not so good | f04665b2-895d-11e9-a2ba-c3b5389c81dc
 f04665a2-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      5 | edf5bee1-895d-11e9-a2ba-c3b5389c81dc | f04665a3-895d-11e9-a2ba-c3b5389c81dc |      Excellent | f04665a4-895d-11e9-a2ba-c3b5389c81dc
 f04665aa-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      4 | f04665ab-895d-11e9-a2ba-c3b5389c81dc | f04665ac-895d-11e9-a2ba-c3b5389c81dc | Could be great | f04665ad-895d-11e9-a2ba-c3b5389c81dc
 edf5bee0-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      1 | f045c960-895d-11e9-a2ba-c3b5389c81dc | f04665a0-895d-11e9-a2ba-c3b5389c81dc |          Sucks | f04665a1-895d-11e9-a2ba-c3b5389c81dc
 edf5bee0-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      2 | f04665a5-895d-11e9-a2ba-c3b5389c81dc | f04665a6-895d-11e9-a2ba-c3b5389c81dc | Could be worst | f04665a7-895d-11e9-a2ba-c3b5389c81dc
 edf5bee0-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      3 | edf5bee1-895d-11e9-a2ba-c3b5389c81dc | f04665ae-895d-11e9-a2ba-c3b5389c81dc |    Not so good | f04665af-895d-11e9-a2ba-c3b5389c81dc
 edf5bee0-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      5 | edf5bee1-895d-11e9-a2ba-c3b5389c81dc | f04665b7-895d-11e9-a2ba-c3b5389c81dc |      Excellent | f04665b8-895d-11e9-a2ba-c3b5389c81dc
 edf5bee0-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      5 | edf5bee1-895d-11e9-a2ba-c3b5389c81dc | f04665a8-895d-11e9-a2ba-c3b5389c81dc |      Excellent | f04665a9-895d-11e9-a2ba-c3b5389c81dc
 f04665b3-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      2 | f04665b4-895d-11e9-a2ba-c3b5389c81dc | f04665b5-895d-11e9-a2ba-c3b5389c81dc | Could be worst | f04665b6-895d-11e9-a2ba-c3b5389c81dc
 f04665b9-895d-11e9-a2ba-c3b5389c81dc | 2019-06-07 |      1 | f04665ba-895d-11e9-a2ba-c3b5389c81dc | f04665bb-895d-11e9-a2ba-c3b5389c81dc |          Sucks | f04665bc-895d-11e9-a2ba-c3b5389c81dc


cqlsh> select * from pedidosya_ks.opinion_by_store;

 storeid                              | createdat  | rating | shoppingid                           | id                                   | comments
--------------------------------------+------------+--------+--------------------------------------+--------------------------------------+----------------
 260cadc0-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      1 | 260cadc2-8968-11e9-ad64-495830482ff7 | 261b05a0-8968-11e9-ad64-495830482ff7 |          Sucks
 260cadd5-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      2 | 260cadd7-8968-11e9-ad64-495830482ff7 | 261b05a7-8968-11e9-ad64-495830482ff7 | Could be worst
 260cadc6-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      2 | 260cadc8-8968-11e9-ad64-495830482ff7 | 261b05a2-8968-11e9-ad64-495830482ff7 | Could be worst
 260caddb-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      1 | 260caddd-8968-11e9-ad64-495830482ff7 | 261b05a9-8968-11e9-ad64-495830482ff7 |          Sucks
 23b21bf1-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      3 | 260cadd0-8968-11e9-ad64-495830482ff7 | 261b05a5-8968-11e9-ad64-495830482ff7 |    Not so good
 23b21bf1-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      3 | 260cadd3-8968-11e9-ad64-495830482ff7 | 261b05a6-8968-11e9-ad64-495830482ff7 |    Not so good
 23b21bf1-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      5 | 260cadc5-8968-11e9-ad64-495830482ff7 | 261b05a1-8968-11e9-ad64-495830482ff7 |      Excellent
 23b21bf1-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      5 | 260cadca-8968-11e9-ad64-495830482ff7 | 261b05a3-8968-11e9-ad64-495830482ff7 |      Excellent
 23b21bf1-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      5 | 260cadd9-8968-11e9-ad64-495830482ff7 | 261b05a8-8968-11e9-ad64-495830482ff7 |      Excellent
 260cadcc-8968-11e9-ad64-495830482ff7 | 2019-06-07 |      4 | 260cadce-8968-11e9-ad64-495830482ff7 | 261b05a4-8968-11e9-ad64-495830482ff7 | Could be great
 
 
 cqlsh> select * from pedidosya_ks.opinion_by_shopping;

 id                                   | shoppingid                           | rating | createdat  | comments
--------------------------------------+--------------------------------------+--------+------------+----------------
 3f716c09-8969-11e9-933d-4f207862642b | 3f57c99c-8969-11e9-933d-4f207862642b |      1 | 2019-06-07 |          Sucks
 3f716c00-8969-11e9-933d-4f207862642b | 3f57c981-8969-11e9-933d-4f207862642b |      1 | 2019-06-07 |          Sucks
 3f716c04-8969-11e9-933d-4f207862642b | 3f57c98d-8969-11e9-933d-4f207862642b |      4 | 2019-06-07 | Could be great
 3f716c03-8969-11e9-933d-4f207862642b | 3f57c989-8969-11e9-933d-4f207862642b |      5 | 2019-06-07 |      Excellent
 3f716c05-8969-11e9-933d-4f207862642b | 3f57c98f-8969-11e9-933d-4f207862642b |      3 | 2019-06-07 |    Not so good
 3f716c02-8969-11e9-933d-4f207862642b | 3f57c987-8969-11e9-933d-4f207862642b |      2 | 2019-06-07 | Could be worst
 3f716c01-8969-11e9-933d-4f207862642b | 3f57c984-8969-11e9-933d-4f207862642b |      5 | 2019-06-07 |      Excellent
 3f716c07-8969-11e9-933d-4f207862642b | 3f57c996-8969-11e9-933d-4f207862642b |      2 | 2019-06-07 | Could be worst
 3f716c06-8969-11e9-933d-4f207862642b | 3f57c992-8969-11e9-933d-4f207862642b |      3 | 2019-06-07 |    Not so good
 3f716c08-8969-11e9-933d-4f207862642b | 3f57c998-8969-11e9-933d-4f207862642b |      5 | 2019-06-07 |      Excellent
 
```

>### Step-by-step
>
>Step 1) Run command: *'docker-compose up'* or *'docker-compose -f <path-to-compose-folder> up'* inside of *src/main/resources* (e.g 'docker-compose.yml') 
>
>Step 2) Run command: *'docker ps'* and check both containers are up: **'cassandra-pedidosya'** and **'kafka-pedidosya'**
>
>Step 3) Run command *'telnet localhost 9042'* to check Cassandra availability
>
>Step 4) Run command *'telnet localhost 9092'* to check Kafka availability
>
>Step 5) Run unit tests in *src/test/java* (e.g: *'com.pedidosya.tests.OpinionControllerTests'* and *'com.pedidosya.tests.OpinionRepositoryTests'*)
