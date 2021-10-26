package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaTopic;
import com.confluent.kafkaChargebackReport.model.KafkaTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(itemResourceDescription = @Description("Kafka topic identified by clusterid_topicname"))
public interface KafkaTopicRepository extends JpaRepository<KafkaTopic, KafkaTopicId> {
}