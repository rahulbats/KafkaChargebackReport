package com.confluent.kafkaChargebackReport.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@ApiModel
@Table(name = "KAFKA_TOPIC")
@Entity
public class KafkaTopic {
    @EmbeddedId
    private KafkaTopicId kafkaTopicId;

    @ApiModelProperty(example = "clusterid_topicname")
    public KafkaTopicId getKafkaTopicId() {
        return kafkaTopicId;
    }

    public void setKafkaTopicId(KafkaTopicId kafkaTopicId) {
        this.kafkaTopicId = kafkaTopicId;
    }
}