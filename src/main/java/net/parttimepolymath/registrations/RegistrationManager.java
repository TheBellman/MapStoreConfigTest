package net.parttimepolymath.registrations;

import net.parttimepolymath.message.EventMessage;

import java.util.List;

public interface RegistrationManager {

    boolean hasRegistration(String registrationId);

    Registration getRegistration(String registrationId);

    List<Registration> getRegistrations();

    boolean registerInterest(Registration registration);

    void touch(String registrationId);

    List<String> getMatchingQueues(EventMessage message);

    String getQueueName(String registrationId);

    List<StreamStatus> getStatus();
}
