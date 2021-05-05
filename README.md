# Introduction 

Then purpose of this application is to help to generate Covid19 Dashboard.

# Technical Design

The Covid Service API is a Spring Boot application that uses spring tools(e.g RestController) to expose a REST API which wil be used by dashboard application. 

Currently, the REST API exposes one operation (GET) that can be performed to check the specific details of covid19 data. 


# Endpoints

The table below contains a list with all the endpoints available in Covid Service API and the permissions needed in order to access them:

| Endpoint | Description/Purpose |
| --- | --- | --- |
| GET /api/covid/getByLocation | Get Covid Data by Location |
| GET /api/covid/getByFilter | Get Covid Data by some filters |
| GET /api/covid/getByThreshold | Get Covid Data by Threshold value |


# Database

The Elastisearch index(covid19) used by the Covid Service API

# Improvement
Can use some ETL tool like logstash, Nifi for reading and processing the csv file.
Can use some streaming like Spark Streaming or Kafka Streaming here. 

# Examples of Endpoint Request
localhost:8080/api/covid/getByLocation?latitude=33.93911&longitude=67.709953&distanceInKm=1000&totalPopulation=1000&threshHold=30

localhost:8080/api/covid/getByThreshold?threshold=10000

localhost:8080/api/covid/getByFilter?countryRegion=Australia&provinceState=Australian Capital Territory

# Example cron expressions
0 * * ? * * -  for each  minute
