package com.confluent.kafkaChargebackReport.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KAFKA_TOPIC")
@Entity
public class KafkaTopic {
    @EmbeddedId
    private KafkaTopicId kafkaTopicId;


    public KafkaTopicId getKafkaTopicId() {
        return kafkaTopicId;
    }

    public void setKafkaTopicId(KafkaTopicId kafkaTopicId) {
        this.kafkaTopicId = kafkaTopicId;
    }
}