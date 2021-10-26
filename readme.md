# Welcome to Kafka chargeback report 
Maintaining a Kafka cluster needs several resources 
and if the cluster is shared with multiple teams it is important
to figure out who is using how much, so that costs can appropriately be shared.

## Requirements
- Calculates user level fetch/produce data
- Calculates topic level fetch/produce/storage data
- A periodic data collection process runs based on configured frequency
- Uses Postgress database tables to figure out which users and which topics need to be monitored.
- Uses Prometheus to figure out metrics data and this service should have access to the prometheus urls

## Technical details 
- This project uses maven, to create the jar use `mvn clean install`
- Pickup the jar from `target/KafkaChargebackReport-0.0.1-SNAPSHOT.jar`
- Run the jar using `java -jar -Dspring.config.location=[properties file] KafkaChargebackReport-0.0.1-SNAPSHOT.jar`


## Contents of the properties file 
- Example properties file is in `src/main/resources/application.properties`
- Change the database details according to your postgress cluster 
