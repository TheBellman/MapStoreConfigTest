package net.parttimepolymath.registrations;

import com.google.common.base.Strings;
import com.hazelcast.core.IMap;
import net.parttimepolymath.message.EventMessage;
import net.parttimepolymath.queue.QueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * re-implementation of the actual Spring InitializingBean.
 */

public class RegistrationManagerImpl implements RegistrationManager {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationManagerImpl.class);

    private final int registrationTtl;
    private RegistrationToucher toucher;

    private final Clock clock;

    private final QueueManager queueManager;

    private final RegistrationCacheProvider registrationCacheProvider;

    private IMap<String, RegistrationWrapper> registrationCache;

    public RegistrationManagerImpl(int registrationTtl, Clock clock, QueueManager queueManager, RegistrationCacheProvider registrationCacheProvider) {
        this.registrationTtl = registrationTtl;
        this.clock = clock;
        this.queueManager = queueManager;
        this.registrationCacheProvider = registrationCacheProvider;

        registrationCache = this.registrationCacheProvider.getRegistrationCache();
        toucher = new RegistrationToucher(this.clock);
    }

    @Override
    public Registration getRegistration(final String registrationId) {
        if (Strings.isNullOrEmpty(registrationId)) {
            return null;
        }

        return Optional.ofNullable(registrationCache.get(registrationId))
            .map(it -> new Registration(it.getRegistration()))
            .orElse(null);
    }

    @Override
    public boolean hasRegistration(final String registrationId) {
        return getRegistration(registrationId) != null;
    }

    @Override
    public List<Registration> getRegistrations() {
        return registrationCache
            .values()
            .stream()
            .filter(Objects::nonNull)
            .map(it -> new Registration(it.getRegistration()))
            .collect(Collectors.toList());
    }

    @Override
    public boolean registerInterest(final Registration registration) {
        if (registration == null) {
            return false;
        }

        String registrationId = registration.getRegistrationId();

        RegistrationWrapper previous = registrationCache.get(registrationId);
        String queueName = queueManager.getQueueName(registration);
        registrationCache.put(registrationId, new RegistrationWrapper(queueName, new Registration(registration)), registrationTtl, TimeUnit.SECONDS);

        if (previous != null && !previous.getRegistration().getCriterion().equals(registration.getCriterion())) {
            LOG.info("Registration {} overwritten with new {}", registration.getRegistrationId(), registration.getCriterion());
        }

        return true;
    }

    @Override
    public void touch(String registrationId) {
        registrationCache.executeOnKey(registrationId, toucher);
    }

    @Override
    public List<String> getMatchingQueues(EventMessage message) {
        return registrationCache
            .values()
            .stream()
            .filter(Objects::nonNull)
            .filter(reg -> reg.getRegistration().getCriterion().matches(message))
            .map(RegistrationWrapper::getQueueName)
            .collect(Collectors.toList());
    }

    @Override
    public String getQueueName(String registrationId) {
        return Optional.ofNullable(registrationCache.get(registrationId))
            .map(RegistrationWrapper::getQueueName)
            .orElse(null);
    }

    @Override
    public List<StreamStatus> getStatus() {
        return registrationCache
            .values()
            .stream()
            .filter(Objects::nonNull)
            .map(it -> {
                String queueName = it.getQueueName();

                StreamStatus status = new StreamStatus();
                status.setRegistrationId(it.getRegistration().getRegistrationId());
                status.setQueueName(queueName);
                status.setQueueSize(queueManager.getQueueSize(queueName));
                status.setPendingCount(queueManager.getPendingCount(queueName));
                return status;
            })
            .collect(Collectors.toList());
    }
}
