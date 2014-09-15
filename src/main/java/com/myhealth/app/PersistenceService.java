package com.myhealth.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhealth.model.User;

/**
 * The update model
 * 
 * @author nelson.okello
 * 
 */
@Service(value = "persistenceService")
public class PersistenceService {

  private @Autowired
  KafkaProducerService kafkaProducer;

  public boolean createUser(final User u) {
    Map<String, String> createUserEvent = new HashMap<String, String>();
    createUserEvent.put("topic", "ACCOUNT");
    createUserEvent.put("action", "CREATE");
    createUserEvent.put("content", u.toEventString());

    return true; // kafkaProducer.send(String.valueOf(u.getId()), createUserEvent);
  }

  public boolean createSocialNetworkAccount(final User u) {
    Map<String, String> createUserEvent = new HashMap<String, String>();
    createUserEvent.put("topic", "ACCOUNT");
    createUserEvent.put("action", "CREATE_FB");
    createUserEvent.put("content", u.toEventString());

    return true; // kafkaProducer.send(String.valueOf(u.getId()), createUserEvent);
  }

}
