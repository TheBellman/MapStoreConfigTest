package net.parttimepolymath.registrations;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.listener.EntryEvictedListener;
import net.parttimepolymath.queue.QueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationCacheListener implements EntryEvictedListener<String, RegistrationWrapper> {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationCacheListener.class);

    private final QueueManager queueManager;
    private final HazelcastInstance hazelcastInstance;

    public RegistrationCacheListener(QueueManager queueManager, HazelcastInstance hazelcastInstance) {
        this.queueManager = queueManager;
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public void entryEvicted(EntryEvent<String, RegistrationWrapper> event) {
        RegistrationWrapper wrapper = event.getOldValue();
        LOG.info("Registration {} evicted after idle timeout", wrapper.getRegistration().getRegistrationId());
        hazelcastInstance.getMap("ElsRegistrationCache").remove(event.getKey());
        queueManager.dropQueue(wrapper.getQueueName());
    }
}
