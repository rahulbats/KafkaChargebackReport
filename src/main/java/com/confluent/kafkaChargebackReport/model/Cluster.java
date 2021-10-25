package com.confluent.kafkaChargebackReport.model;

import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "CLUSTER")
@ApiModel(description = "Kafka cluster with prometheus")
@Entity
public class Cluster {
    @Id
    @Column(name = "CLUSTER_ID", nullable = false)
    private String clusterId;

    @Column(name = "PROMETHEUS_URL", nullable = false)
    private String prometheusURL;



    public String getPrometheusURL() {
        return prometheusURL;
    }

    public void setPrometheusURL(String prometheusURL) {
        this.prometheusURL = prometheusURL;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }
}