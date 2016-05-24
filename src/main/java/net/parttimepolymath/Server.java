package net.parttimepolymath;

import com.hazelcast.core.HazelcastInstance;
import net.parttimepolymath.queue.QueueManager;
import net.parttimepolymath.queue.QueueManagerImpl;
import net.parttimepolymath.queue.QueueRepository;
import net.parttimepolymath.queue.QueueRepositoryImpl;
import net.parttimepolymath.registrations.Registration;
import net.parttimepolymath.registrations.RegistrationCacheProvider;
import net.parttimepolymath.registrations.RegistrationManager;
import net.parttimepolymath.registrations.RegistrationManagerImpl;
import net.parttimepolymath.utility.HazelcastInstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.List;

/**
 * dummy class to exercise the hazelcast map.
 */
public class Server implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private RegistrationManager registrationManager;

    private final QueueManager queueManager = new QueueManagerImpl();
    private final QueueRepository queueRepository = new QueueRepositoryImpl();
    private final HazelcastInstanceFactory hazelcastInstanceFactory;

    public Server() throws Exception {
        LOG.info("Server construction starts");

        /*
         * note that this is definitely overkill, but I am emulating the Spring wiring that set's up the factory bean, and in turn provides the (single) HazelcastInstance
         */
        hazelcastInstanceFactory = new HazelcastInstanceFactory("dev_docker", "letmein", 7200, "127.0.0.*,172.17.0.*", 5701, 54327, "224.2.2.3");
        HazelcastInstance hazelcastInstance = hazelcastInstanceFactory.getObject();

        /*
         * there are several different providers in the real server, this is the first that is actually invoked, and where the problem manifested.
         */
        RegistrationCacheProvider provider = new RegistrationCacheProvider(hazelcastInstance, queueManager, queueRepository);

        registrationManager = new RegistrationManagerImpl(7200, Clock.systemDefaultZone(), queueManager, provider);

        LOG.info("Server construction ends");

    }

    public void start() {
        LOG.info("Server start begins");
        List<Registration> result = registrationManager.getRegistrations();
        LOG.info("Server start ends - {} registrations known", result.size());
    }

    @Override
    public void close() throws Exception {
        LOG.info("Server close begins");
        queueManager.close();
        hazelcastInstanceFactory.destroy();
        LOG.info("Server close ends");

    }

    public void touch(int count) {
        LOG.info("Server touch {}", count);
    }
}
