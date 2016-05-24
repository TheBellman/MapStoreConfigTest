package net.parttimepolymath.queue;

import net.parttimepolymath.message.EventMessage;
import net.parttimepolymath.registrations.RegistrationWrapper;

import java.util.List;
import java.util.Set;

public interface QueueRepository {

    RegistrationWrapper getRegistration(String registrationId);

    void removeRegistration(String registrationId);

    List<String> fetchRegistrationIds();

    void storeRegistration(RegistrationWrapper registrationWrapper);

    void updateRegistration(RegistrationWrapper registrationWrapper);

    void storeEventMessage(String queueName, EventMessage value);

    void removeEventMessage(String queueName, String messageId);

    EventMessage getEventMessage(String queueName, String messageId);

    List<String> fetchMessageIds(String queueName);

    void storeQueueMessage(String queueName, Long key, EventMessage value);

    void removeQueueMessage(String queueName, Long key);

    Set<Long> fetchQueueKeys(String queueName);

    EventMessage getQueueMessage(String queueName, Long key) ;
}
