package net.parttimepolymath.registrations;

import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import net.parttimepolymath.queue.QueueManager;
import net.parttimepolymath.queue.QueueRepository;

/**
 * re-implementation of the actual Spring InitializingBean.
 */
public class RegistrationCacheProvider {
    private static final int WRITE_BATCH_SIZE = 50;
    private static final int WRITE_DELAY_SECONDS = 2;

    private final IMap<String, RegistrationWrapper> cache;

    public RegistrationCacheProvider(HazelcastInstance hazelcastInstance, final QueueManager queueManager, final QueueRepository queueRepository) throws Exception {
        MapConfig config = hazelcastInstance.getConfig().getMapConfig("ElsRegistrationCache");
        MapStoreConfig msConfig = config.getMapStoreConfig();
        msConfig.setEnabled(true)
            .setInitialLoadMode(MapStoreConfig.InitialLoadMode.LAZY)
            .setWriteDelaySeconds(WRITE_DELAY_SECONDS)
            .setWriteBatchSize(WRITE_BATCH_SIZE)
            .setImplementation(new RegistrationCacheStore(queueRepository));
        IMap<String, RegistrationWrapper> regMap = hazelcastInstance.getMap("ElsRegistrationCache");
        regMap.addEntryListener(new RegistrationCacheListener(queueManager, hazelcastInstance), true);

        cache = regMap;
    }

    public IMap<String, RegistrationWrapper> getRegistrationCache() {
        return cache;
    }

}
