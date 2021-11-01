package com.confluent.kafkaChargebackReport.service;

import com.confluent.kafkaChargebackReport.model.*;
import com.confluent.kafkaChargebackReport.repository.*;
import com.confluent.kafkaChargebackReport.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataCollectorService {

    final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private KafkaUserActivityRepository kafkaUserActivityRepository;

    @Autowired
    private KafkaTopicActivityRepository kafkaTopicActivityRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClusterRepository clusterRepository;

    @Autowired
    private KafkaTopicRepository kafkaTopicRepository;

    @Autowired
    private DataCollectorScheduleStatusRepository dataCollectorScheduleStatusRepository;


    ObjectMapper mapper = new ObjectMapper();

    public Long getFetchedUserBytes(String prometheusURL, String userId, int numberOfDays, long date) {
        String prometheusURLWithParams= prometheusURL+"?query=sum(avg_over_time(kafka_server_fetch_byte_rate{user=\""+userId+"\",}["+numberOfDays+"d]))&time="+date;
        return getPrometheusData(prometheusURLWithParams);
    }


    public Long getProducedUserBytes(String prometheusURL, String user, int numberOfDays, long date) {
        return getPrometheusData( prometheusURL+"?query=sum(avg_over_time(kafka_server_produce_byte_rate{user=\""+user+"\",}["+numberOfDays+"d]))&time="+date);

    }


    public Long getFetchedTopicBytes(String prometheusURL, String topicName, int numberOfDays, long date)  {
        return getPrometheusData( prometheusURL+"?query=sum(avg_over_time(kafka_server_brokertopicmetrics_meanrate{topic=\""+topicName+"\",name=\"BytesOutPerSec\"}["+numberOfDays+"d]))&time="+date);
    }


    public Long getProducedTopicBytes(String prometheusURL, String topicName, int numberOfDays, long date)  {
        return getPrometheusData( prometheusURL+"?query=sum(avg_over_time(kafka_server_brokertopicmetrics_meanrate{topic=\""+topicName+"\",name=\"BytesInPerSec\"}["+numberOfDays+"d]))&time="+date);
    }

    public Long getTopicStorageBytes(String prometheusURL, String topic, int numberOfDays, long date)  {
        return getPrometheusData(prometheusURL+"?query=sum(avg_over_time(kafka_log_log_value{topic=\""+topic+"\",name=\"Size\"}["+numberOfDays+"d]))&time="+date);
    }

    public Long getTopicRecords(String prometheusURL, String topic, int numberOfDays, long date)  {
        return getPrometheusData(prometheusURL+"?query=sum(avg_over_time(kafka_server_brokertopicmetrics_meanrate{topic=\""+topic+"\",name=\"MessagesInPerSec\"}["+numberOfDays+"d]))&time="+date);
    }


    public void runReport(Date date)  {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {

            setDataCollectorStatus(Constants.STATUS_INITIALIZED, date);
            saveUserAndTopicData(1, date.getTime()/1000);
            setDataCollectorStatus(Constants.STATUS_SUCCESFUL, date);

            logger.info("Report ran for {} on {}", dateFormat.format(date.getTime()),dateFormat.format(new Date()));
        } catch (Exception e) {
            setDataCollectorStatus(Constants.STATUS_FAILED, date);
        }

    }

    public Long getPrometheusData(String prometheusURLWithParams) {
        try{
            URL url = new URL(prometheusURLWithParams);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            rd.close();
            JsonNode responseNode = mapper.readTree(response.toString());
            return (responseNode.get("data").get("result").get(0).get("value")).get(0).asLong();

        } catch ( IOException e) {
           logger.error("This prometheus url errored out: "+ prometheusURLWithParams);
           throw new RuntimeException(e);
        } catch (NullPointerException e) {
            return 0L;
        }

    }

    public void setDataCollectorStatus(int status, Date date) {
        DataCollectorScheduleStatus dataCollectorScheduleStatus = new DataCollectorScheduleStatus();
        dataCollectorScheduleStatus.setRunDate(date);
        dataCollectorScheduleStatus.setStatus(status);
        dataCollectorScheduleStatusRepository.save(dataCollectorScheduleStatus);
    }

    @Transactional
    public void saveUserAndTopicData(int numberOfDays, long endDate) {
        List<User> users = userRepository.findAll();
        List<KafkaUserActivity> userActivities = users.parallelStream().map(user -> {
           String userId = user.getUserKey().getUserId();
           String clusterId = user.getUserKey().getClusterId();
           Optional<Cluster> cluster = clusterRepository.findById(clusterId);
           if(cluster.isPresent()){
               String prometheusURL = cluster.get().getPrometheusURL()+"/api/v1/query";
               return new KafkaUserActivity(new KafkaUserActivityId(userId, clusterId, new Date()), getFetchedUserBytes(prometheusURL,userId, 1, endDate), getProducedUserBytes(prometheusURL,userId, 1, endDate));
           }
           return null;
        }).filter(activity->activity!=null)
                 .collect(Collectors.toList());

        kafkaUserActivityRepository.saveAll(userActivities);

        List<KafkaTopic> topics = kafkaTopicRepository.findAll();
        List<KafkaTopicActivity> topicActivities =topics.parallelStream().map(kafkaTopic -> {
           String topicName = kafkaTopic.getKafkaTopicId().getTopicName();
           String clusterId = kafkaTopic.getKafkaTopicId().getClusterId();
           Optional<Cluster> cluster = clusterRepository.findById(clusterId);
                    if(cluster.isPresent()){

                            String prometheusURL = cluster.get().getPrometheusURL()+"/api/v1/query";
                            return new KafkaTopicActivity(new KafkaTopicActivityId(topicName, clusterId, new Date()), getFetchedTopicBytes(prometheusURL,topicName,1,endDate), getProducedTopicBytes(prometheusURL,topicName,1,endDate),
                                    getTopicStorageBytes(prometheusURL,topicName,1,endDate),
                                    getTopicRecords(prometheusURL,topicName,numberOfDays,endDate));


                    }
                    return null;
                }).filter(activity->activity!=null)
                .collect(Collectors.toList());
        kafkaTopicActivityRepository.saveAll(topicActivities);
    }


}
