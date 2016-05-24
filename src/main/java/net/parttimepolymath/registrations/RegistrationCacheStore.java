package net.parttimepolymath.registrations;

import com.hazelcast.core.MapStore;
import net.parttimepolymath.queue.QueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RegistrationCacheStore implements MapStore<String, RegistrationWrapper> {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationCacheStore.class);

    private final QueueRepository queueRepository;

    public RegistrationCacheStore(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @Override
    public void store(String key, RegistrationWrapper value) {
        queueRepository.storeRegistration(value);
    }

    @Override
    public void delete(String key) {
        queueRepository.removeRegistration(key);
    }

    @Override
    public RegistrationWrapper load(String key) {
        LOG.info("load({})", key);
        return queueRepository.getRegistration(key);
    }

    @Override
    public Iterable<String> loadAllKeys() {
        LOG.info("loadAllKeys()");
        List<String> result= queueRepository.fetchRegistrationIds();
        return result.isEmpty() ? null : result;
    }

    @Override
    public void storeAll(Map<String, RegistrationWrapper> map) {
        LOG.info("storeAll()");
        map.forEach(this::store);
    }

    @Override
    public void deleteAll(Collection<String> keys) {
        LOG.info("deleteAll()");
        keys.parallelStream().forEach(this::delete);
    }

    @Override
    public Map<String, RegistrationWrapper> loadAll(Collection<String> keys) {
        LOG.info("loadAll()");
        return keys.parallelStream().collect(Collectors.toMap(key -> key, this::load));
    }


}
