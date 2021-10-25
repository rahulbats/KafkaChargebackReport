package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.DataCollectorScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DataCollectorScheduleStatusRepository extends JpaRepository<DataCollectorScheduleStatus, Date> {
}