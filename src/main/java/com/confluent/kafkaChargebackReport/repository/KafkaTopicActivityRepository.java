package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaTopicActivity;
import com.confluent.kafkaChargebackReport.model.KafkaTopicActivityId;
import com.confluent.kafkaChargebackReport.model.KafkaUserActivity;
import org.springframework.data.repository.CrudRepository;

public interface KafkaTopicActivityRepository  extends CrudRepository<KafkaTopicActivity, KafkaTopicActivityId> {
}
