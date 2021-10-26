package com.confluent.kafkaChargebackReport.controller;

import com.confluent.kafkaChargebackReport.service.DataCollectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Api( tags = "Data Collector")
@RestController
public class DataCollectorController {
    @Autowired
    DataCollectorService dataCollectorService;

    @GetMapping(path ="/fetchedbytes/{cluster}/{user}/{date}/{numberOfDays}", produces = "application/json")
    public Long getFetchedUserBytes(@PathVariable String cluster,@PathVariable String user,@PathVariable String date, @PathVariable int numberOfDays) throws  ParseException {
       // mailService.sendBill();
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return dataCollectorService.getFetchedUserBytes(cluster,user, numberOfDays, calendar.getTime().getTime()/1000);
    }

    @GetMapping(path ="/producedbytes/{cluster}/{user}/{date}/{numberOfDays}", produces = "application/json")
    public Long getProducedUserBytes(@PathVariable String cluster,@PathVariable String user,@PathVariable String date, @PathVariable int numberOfDays) throws  ParseException {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return dataCollectorService.getProducedUserBytes(cluster,user, numberOfDays, calendar.getTime().getTime()/1000);
    }

    @ApiOperation(value = "Run report",tags = {"run-report"})
    @GetMapping(path ="/runreport/{date}", produces = "application/json")
    public void runReport( @ApiParam("Date in yyyy-MM-dd format") @PathVariable() String date) throws  ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        dataCollectorService.runReport(calendar.getTime());
    }

    }
