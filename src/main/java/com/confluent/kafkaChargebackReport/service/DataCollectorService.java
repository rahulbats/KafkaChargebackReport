package com.confluent.kafkaChargebackReport.service;

import com.confluent.kafkaChargebackReport.repository.KafkaTopicRepository;
import com.confluent.kafkaChargebackReport.repository.UserRepository;
import com.confluent.kafkaChargebackReport.model.*;
import com.confluent.kafkaChargebackReport.repository.ClusterRepository;
import com.confluent.kafkaChargebackReport.repository.KafkaTopicActivityRepository;
import com.confluent.kafkaChargebackReport.repository.KafkaUserActivityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DataCollectorService {

    final Log logger = LogFactory.getLog(getClass());


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



    ObjectMapper mapper = new ObjectMapper();

    public Long getFetchedUserBytes(String prometheusURL, String userId, int numberOfDays, Date date) {
        String prometheusURLWithParams= prometheusURL+"?query=sum(avg_over_time(kafka_server_fetch_byte_rate{user=\""+userId+"\",}["+numberOfDays+"h]))&time="+date.getTime();
        return getPrometheusData(prometheusURLWithParams);
    }


    public Long getProducedUserBytes(String prometheusURL, String user, int numberOfDays, Date date) {
        return getPrometheusData( prometheusURL+"?query=sum(avg_over_time(kafka_server_produce_byte_rate{user=\""+user+"\",}["+numberOfDays+"h]))&time="+date.getTime());

    }


    public Long getFetchedTopicBytes(String prometheusURL, String topicName, int numberOfDays, Date date)  {
        return getPrometheusData( prometheusURL+"?query=sum(avg_over_time(kafka_server_brokertopicmetrics_meanrate{topic="+topicName+",name=\"BytesOutPerSec\"}["+numberOfDays+"h]))&time="+date.getTime());
    }


    public Long getProducedTopicBytes(String prometheusURL, String topicName, int numberOfDays, Date date)  {
        return getPrometheusData( prometheusURL+"?query=sum(avg_over_time(kafka_server_brokertopicmetrics_meanrate{topic="+topicName+",name=\"BytesInPerSec\"}["+numberOfDays+"h]))&time="+date.getTime());
    }

    public Long getTopicStorageBytes(String prometheusURL, String topic, int numberOfDays, Date date)  {
        return getPrometheusData(prometheusURL+"?query=sum(avg_over_time(kafka_log_log_value{topic=\""+topic+"\",name=\"Size\"}["+numberOfDays+"h]))&time="+date.getTime());
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
            Long dataBytes = ((ArrayNode)responseNode.get("data").get("result").get(0).get("value")).get(0).asLong();
            return dataBytes;

        } catch ( IOException e) {
           throw new RuntimeException(e);
        }

    }



    public void saveUserAndTopicData(int numberOfDays, Date endDate) {
        List<User> users = userRepository.findAll();
        List<KafkaUserActivity> userActivities = users.parallelStream().map(user -> {
           String userId = user.getUserKey().getUserId();
           String clusterId = user.getUserKey().getClusterId();
           Optional<Cluster> cluster = clusterRepository.findById(clusterId);
           if(cluster!=null){
               String prometheusURL = cluster.get().getPrometheusURL();
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
                    if(cluster!=null){

                            String prometheusURL = cluster.get().getPrometheusURL();
                            return new KafkaTopicActivity(new KafkaTopicActivityId(topicName, clusterId, new Date()), getFetchedTopicBytes(prometheusURL,topicName,1,endDate), getProducedTopicBytes(prometheusURL,topicName,1,endDate), getTopicStorageBytes(prometheusURL,topicName,1,endDate));


                    }
                    return null;
                }).filter(activity->activity!=null)
                .collect(Collectors.toList());
        kafkaTopicActivityRepository.saveAll(topicActivities);
    }


}
