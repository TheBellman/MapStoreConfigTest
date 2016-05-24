package net.parttimepolymath.queue;

import net.parttimepolymath.message.EventMessage;
import net.parttimepolymath.registrations.Registration;

import java.io.Closeable;
import java.util.List;

public interface QueueManager extends Closeable {
    String getQueueName(Registration registration);

    void dropQueue(String queueName);

    void send(EventMessage message, String queueName);

    EventMessage get(String queueName);

    void acknowledge(String queueName, List<String> messages);

    int getQueueSize(String queueName);

    int getPendingCount(String queueName);
}
