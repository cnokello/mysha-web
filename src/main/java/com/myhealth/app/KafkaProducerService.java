package com.myhealth.app;

import java.util.Map;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Kafka producer class that handles sending of messages to Kafka distributed message broker
 * 
 * @author nelson.okello
 * 
 */
@Service(value = "kafkaProducerService")
public class KafkaProducerService {

  private Producer<String, String> producer;

  private @Value("${broker.list}")
  String brokerList;

  private @Value("${serializer.class}")
  String serializerClass;

  private @Value("${partitioner.class}")
  String partitionerClass;

  private @Value("${app.topic.name}")
  String appTopicName;

  public void init() {
    Properties props = new Properties();
    props.put("metadata.broker.list", brokerList);
    props.put("serializer.class", serializerClass);
    props.put("partitioner.class", partitionerClass);
    props.put("request.required.acks", "1");

    ProducerConfig config = new ProducerConfig(props);
    producer = new Producer<String, String>(config);
  }

  /**
   * Sends a message to a specified topic
   * 
   * @param userId
   *          The user who generated this event
   * @param event
   *          The event generated
   * @return Returns true if sending is successfaul, false otherwise
   */
  public boolean send(final String userId, final Map<String, String> event) {
    if (producer == null) {
      init();
    }

    if (producer != null) {
      if (event != null) {
        final String eventTopic = event.get("topic");
        final String eventAction = event.get("action");
        final String content = event.get("content");

        final String msg = eventTopic + "#" + eventAction + "#" + content;
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(appTopicName, userId,
            msg);
        producer.send(data);
      }

    } else {
      return false;
    }

    return true;
  }
}
