package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, String> {
}