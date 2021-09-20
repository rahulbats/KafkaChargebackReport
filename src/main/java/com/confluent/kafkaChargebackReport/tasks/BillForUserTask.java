package com.confluent.kafkaChargebackReport.tasks;

import com.confluent.kafkaChargebackReport.service.BillForUserService;
import com.confluent.kafkaChargebackReport.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

@Component
public class BillForUserTask {

    @Autowired
    BillForUserService billForUserService;

    @Autowired
    MailService mailService;

    @Value("${users}")
    String users;

    private static final Logger log = LoggerFactory.getLogger(BillForUserTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "${taskcron}")
    public void reportCurrentTime() throws IOException {
        int numberOfDays = 0;
        String[] userArray = users.split(",");
        for(String user:userArray) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH,-1);
            YearMonth yearMonthObject = YearMonth.of(now.get(Calendar.YEAR), now.get(Calendar.MONTH));
            numberOfDays = yearMonthObject.lengthOfMonth();
            long fetchedBytes = billForUserService.getFetchedBytes(user, numberOfDays);
            long producedBytes = billForUserService.getProducedBytes(user, numberOfDays);

        }
        log.info("number of days in month "+numberOfDays);
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
