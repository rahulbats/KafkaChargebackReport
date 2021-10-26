package com.confluent.kafkaChargebackReport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

@Configuration
public class SpringfoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .paths(Predicate.not(PathSelectors.regex("/error.*|/profile")))
                .build()
                .tags(new Tag("Data Collector","Data collector rest service"),
                        new Tag("Cluster Entity","CRUD operations for Kafka Cluster"),
                        //new Tag("DataCollectorScheduleStatus Entity","CRUD operations for data collector status"),
                        new Tag("KafkaTopic Entity","CRUD operation for the Kafka topics for which usage data needs to be collected"),
                        //new Tag("KafkaTopicActivity Entity","CRUD operations for the Kafka topic based activity which includes Produced, Consumed, Storage bytes"),
                        //new Tag("KafkaUserActivity Entity","CRUD operations for User based activity"),
                        new Tag("User Entity","CRUD operations for Users of Kafka"));
    }
}