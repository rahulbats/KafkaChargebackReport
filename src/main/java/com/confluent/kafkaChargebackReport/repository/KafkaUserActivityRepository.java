package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.KafkaUserActivity;
import com.confluent.kafkaChargebackReport.model.KafkaUserActivityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface KafkaUserActivityRepository extends CrudRepository<KafkaUserActivity, KafkaUserActivityId> {

}
