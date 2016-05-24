package net.parttimepolymath.queue;

import net.parttimepolymath.message.Action;
import net.parttimepolymath.message.Actor;
import net.parttimepolymath.message.Details;
import net.parttimepolymath.message.EventMessage;
import net.parttimepolymath.message.Resource;
import net.parttimepolymath.registrations.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * dummy implementation that does little more than log.
 */
public class QueueManagerImpl implements QueueManager {
    private static final Logger LOG = LoggerFactory.getLogger(QueueManagerImpl.class);

    @Override
    public String getQueueName(Registration registration) {
        return registration.getRegistrationId();
    }

    @Override
    public void dropQueue(String queueName) {
        LOG.info("dropQueue({})", queueName);
    }

    @Override
    public void send(EventMessage message, String queueName) {
        LOG.info("send({}, {})", message.getMessageId(), queueName);
    }

    @Override
    public EventMessage get(String queueName) {
        Actor player = new Actor("PLAYER", "98552920", "", new Details("text/plain", "What a piece of work is a man, how noble in reason"));
        Action action = new Action("PLAY", new Details("application/json", "{price : 100, play : \"play/98552920/bingo/d034127a-ce7c-11e5-ab30-625662870761\"}"),
            true, new Details("application/json", "{prize : 1000}"));
        Resource resource = new Resource("IWG", "/api/game/bingo", "10.190.231.33:8030", new Details("text/plain", "how infinite in faculties, in form and moving"));

        return new EventMessage.Builder(player, action, resource)
            .withCorrelationId("79380f4a-d97a-11e5-b5d2-0a1d41d68578")
            .withMessageSource("10.190.230.29")
            .withSourceService("dummy-service")
            .withSourceVersion("1.0.886-1")
            .withTags("PLAY", "TEST")
            .withRelated("f9ab3494-ce7c-11e5-ab30-625662870761")
            .build();
    }

    @Override
    public void acknowledge(String queueName, List<String> messages) {
        LOG.info("acknowledge({})", queueName);
    }

    @Override
    public int getQueueSize(String queueName) {
        return 42;
    }

    @Override
    public int getPendingCount(String queueName) {
        return 42;
    }

    @Override
    public void close() throws IOException {
        LOG.info("close()");
    }
}
