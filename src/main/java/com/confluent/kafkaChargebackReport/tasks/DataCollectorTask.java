package com.confluent.kafkaChargebackReport.tasks;

import com.confluent.kafkaChargebackReport.service.DataCollectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DataCollectorTask {

    @Autowired
    DataCollectorService dataCollectorService;


    private static final Logger log = LoggerFactory.getLogger(DataCollectorTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Scheduled(cron = "${taskcron}")
    public void reportCurrentTime()  {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        dataCollectorService.saveUserAndTopicData(1, calendar.getTime());

        log.info("Report ran for {}", dateFormat.format(calendar.getTime()));
    }
}
