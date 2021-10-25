package com.confluent.kafkaChargebackReport.model;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "KAFKA_USER_ACTIVITY")
public class KafkaUserActivity extends KafkaActivity{

    @Column(name = "BYTES_CONSUMED")
    private long bytesConsumed;
    @Column(name = "BYTES_PRODUCED")
    private long bytesProduced;


    @EmbeddedId
    private KafkaUserActivityId kafkaUserActivityId;

    public long getBytesConsumed() {
        return bytesConsumed;
    }

    public void setBytesConsumed(long bytesConsumed) {
        this.bytesConsumed = bytesConsumed;
    }

    public long getBytesProduced() {
        return bytesProduced;
    }

    public void setBytesProduced(long bytesProduced) {
        this.bytesProduced = bytesProduced;
    }

    public  KafkaUserActivity(){}

    public  KafkaUserActivity(KafkaUserActivityId kafkaUserActivityId, long bytesConsumed, long bytesProduced) {
            this.kafkaUserActivityId = kafkaUserActivityId;
            this.bytesConsumed = bytesConsumed;
            this.bytesProduced = bytesProduced;
    }
}
