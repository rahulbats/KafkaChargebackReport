package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaTopic;
import com.confluent.kafkaChargebackReport.model.KafkaTopicId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaTopicRepository extends JpaRepository<KafkaTopic, KafkaTopicId> {
}