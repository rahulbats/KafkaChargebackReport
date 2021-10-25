package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.Cluster;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
public interface ClusterRepository extends JpaRepository<Cluster, String> {
}