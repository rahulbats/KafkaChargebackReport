package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaUserActivity;
import com.confluent.kafkaChargebackReport.model.KafkaUserActivityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface KafkaUserActivityRepository extends CrudRepository<KafkaUserActivity, KafkaUserActivityId> {

}
