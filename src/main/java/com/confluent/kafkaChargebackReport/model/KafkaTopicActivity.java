package com.confluent.kafkaChargebackReport.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KAFKA_TOPIC_ACTIVITY")
public class KafkaTopicActivity extends KafkaActivity{

    @Column(name = "STORAGE")
    private long storage;
    @Column(name = "BYTES_IN")
    private long bytesIn;

    @Column(name = "BYTES_OUT")
    private long bytesOut;



    @Column(name = "RECORDS")
    private long records;

    @EmbeddedId
    private KafkaTopicActivityId kafkaTopicActivityId;

    public long getStorage() {
        return storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }

    public long getBytesIn() {
        return bytesIn;
    }

    public void setBytesIn(long bytesIn) {
        this.bytesIn = bytesIn;
    }

    public long getBytesOut() {
        return bytesOut;
    }

    public void setBytesOut(long bytesOut) {
        this.bytesOut = bytesOut;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public KafkaTopicActivityId getKafkaTopicActivityId() {
        return kafkaTopicActivityId;
    }

    public void setKafkaTopicActivityId(KafkaTopicActivityId kafkaTopicActivityId) {
        this.kafkaTopicActivityId = kafkaTopicActivityId;
    }




    public KafkaTopicActivity(){}

    public KafkaTopicActivity(KafkaTopicActivityId kafkaTopicActivityId, long bytesIn, long bytesOut, long storage, long records) {
        this.kafkaTopicActivityId = kafkaTopicActivityId;
        this.bytesIn = bytesIn;
        this.bytesOut = bytesOut;
        this.storage = storage;
        this.records = records;
    }

}
