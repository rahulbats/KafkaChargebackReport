package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaTopicActivity;
import com.confluent.kafkaChargebackReport.model.KafkaUserActivity;
import com.confluent.kafkaChargebackReport.model.KafkaUserActivityId;
import org.springframework.data.repository.CrudRepository;

public interface KafkaUserActivityRepository extends CrudRepository<KafkaUserActivity, KafkaUserActivityId> {

}
