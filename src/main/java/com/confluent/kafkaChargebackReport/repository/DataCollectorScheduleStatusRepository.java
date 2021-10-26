package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.DataCollectorScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
@RestResource(exported = false)
public interface DataCollectorScheduleStatusRepository extends JpaRepository<DataCollectorScheduleStatus, Date> {
}