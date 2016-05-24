package net.parttimepolymath.queue;

import net.parttimepolymath.message.EventMessage;
import net.parttimepolymath.registrations.RegistrationWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * stub implementation of QueueRepository - the actual repository is wired to an underlying Cassandra data store.
 */
public class QueueRepositoryImpl implements QueueRepository {
    private static final Logger LOG = LoggerFactory.getLogger(QueueManagerImpl.class);

    @Override
    public RegistrationWrapper getRegistration(String registrationId) {
        LOG.info("getRegistration({})", registrationId);
        return null;
    }

    @Override
    public void removeRegistration(String registrationId) {
        LOG.info("removeRegistration({})", registrationId);

    }

    @Override
    public List<String> fetchRegistrationIds() {
        LOG.info("fetchRegistrationIds()");
        return Collections.emptyList();
    }

    @Override
    public void storeRegistration(RegistrationWrapper registrationWrapper) {
        LOG.info("storeRegistration({})", registrationWrapper);
    }

    @Override
    public void updateRegistration(RegistrationWrapper registrationWrapper) {
        LOG.info("updateRegistration({})", registrationWrapper);
    }

    @Override
    public void storeEventMessage(String queueName, EventMessage value) {
        LOG.info("storeEventMessage({}, {})", queueName, value);
    }

    @Override
    public void removeEventMessage(String queueName, String messageId) {
        LOG.info("removeEventMessage({}, {})", queueName, messageId);

    }

    @Override
    public EventMessage getEventMessage(String queueName, String messageId) {
        LOG.info("getEventMessage({}, {})", queueName, messageId);
        return null;
    }

    @Override
    public List<String> fetchMessageIds(String queueName) {
        LOG.info("fetchMessageIds({})", queueName);
        return Collections.emptyList();
    }

    @Override
    public void storeQueueMessage(String queueName, Long key, EventMessage value) {
        LOG.info("storeQueueMessage({}, {}, {}", queueName, key, value);
    }

    @Override
    public void removeQueueMessage(String queueName, Long key) {
        LOG.info("removeQueueMessage({}, {})", queueName, key);
    }

    @Override
    public Set<Long> fetchQueueKeys(String queueName) {
        LOG.info("fetchQueueKeys({})", queueName);
        return Collections.emptySet();
    }

    @Override
    public EventMessage getQueueMessage(String queueName, Long key) {
        LOG.info("getQueueMessage({}, key)", queueName, key);
        return null;
    }
}
