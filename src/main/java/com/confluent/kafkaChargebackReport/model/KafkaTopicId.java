package com.confluent.kafkaChargebackReport.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class KafkaTopicId implements Serializable {
    @Column(name = "CLUSTER_ID", nullable = false)
    private String clusterId;

    @Column(name = "TOPIC_NAME", nullable = false)
    private String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KafkaTopicId that = (KafkaTopicId) o;
        return Objects.equals(clusterId, that.clusterId) && Objects.equals(topicName, that.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterId, topicName);
    }
}