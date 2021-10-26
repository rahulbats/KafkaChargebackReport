package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaTopicActivity;
import com.confluent.kafkaChargebackReport.model.KafkaTopicActivityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;


@RestResource(exported = false)
public interface KafkaTopicActivityRepository  extends CrudRepository<KafkaTopicActivity, KafkaTopicActivityId> {
}
