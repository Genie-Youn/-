# README #

This project is web application server for chr, NumnuM team.

### Stack overview ###

* Java 8 + Spring 4.1.1
* MySQL 5.7
* ElasticSearch 2.3.2
* Redis 3.2.0
* spring-data-redis 1.7.1

### Team NumnuM ###

* Genie Youn 
* Hyesoo Jang
* Yujin Jang

### API Docs ###

**Stroe** 
     
```
#!curl

curl -X GET /stores
```

*Parameters*

| Element    | Optional   | Type  |   Description       |
| -----------|:-----------| ------|:--------------------|
| seqAccount | O          | Long  | User Sequence Number|