package net.parttimepolymath.registrations;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;

public final class RegistrationWrapper implements Serializable {

    private final String queueName;
    private final Registration registration;
    private Instant lastUsed;

    public RegistrationWrapper(final String queueName, final Registration registration) {
        this.queueName = queueName;
        this.registration = registration;
    }

    public String getQueueName() {
        return queueName;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void touch(Clock clock) {
        lastUsed = Instant.now(clock);
    }
}

