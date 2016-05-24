package net.parttimepolymath.message;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class EventMessage implements Serializable {
    private String messageId = UUID.randomUUID().toString();
    private String correlationId;
    private Instant eventTime;
    private Instant eventSent;
    private Instant eventStored;
    private String messageSource;
    private String sourceService;
    private String sourceVersion;
    private Set<String> tags = new TreeSet<>();
    private Set<String> related = new TreeSet<>();
    private Actor actor;
    private Action action;
    private Resource resource;

    private EventMessage(Builder builder) {
        correlationId = builder.correlationId;
        eventSent = builder.eventSent;
        eventStored = builder.eventStored;
        messageSource = builder.messageSource;
        sourceService = builder.sourceService;
        sourceVersion = builder.sourceVersion;
        tags.addAll(builder.tags);
        related.addAll(builder.related);
        actor = builder.actor;
        action = builder.action;
        resource = builder.resource;
        if (builder.eventTime == null) {
            eventTime = builder.clock == null ? Instant.now(Clock.systemDefaultZone()) : Instant.now(builder.clock);
        } else {
            eventTime = builder.eventTime;
        }
        if (!Strings.isNullOrEmpty(builder.messageId)) {
            messageId = builder.messageId;
        }
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public Instant getEventSent() {
        return eventSent;
    }

    public Instant getEventStored() {
        return eventStored;
    }

    public String getMessageSource() {
        return messageSource;
    }

    public String getSourceService() {
        return sourceService;
    }

    public String getSourceVersion() {
        return sourceVersion;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<String> getRelated() {
        return Collections.unmodifiableSet(related);
    }

    public Actor getActor() {
        return actor;
    }

    public Action getAction() {
        return action;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventMessage that = (EventMessage) o;
        return Objects.equal(messageId, that.messageId)
            && Objects.equal(correlationId, that.correlationId)
            && Objects.equal(eventTime, that.eventTime)
            && Objects.equal(eventSent, that.eventSent)
            && Objects.equal(eventStored, that.eventStored)
            && Objects.equal(messageSource, that.messageSource)
            && Objects.equal(sourceService, that.sourceService)
            && Objects.equal(sourceVersion, that.sourceVersion)
            && Objects.equal(tags, that.tags)
            && Objects.equal(related, that.related)
            && Objects.equal(actor, that.actor)
            && Objects.equal(action, that.action)
            && Objects.equal(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageId, correlationId, eventTime, eventSent, eventStored, messageSource, sourceService, sourceVersion, tags, related, actor, action, resource);
    }

    @Override
    public String toString() {
        return "EventMessage{" +
            "messageId='" + messageId + '\'' +
            ", correlationId='" + correlationId + '\'' +
            ", eventTime=" + eventTime +
            ", eventSent=" + eventSent +
            ", eventStored=" + eventStored +
            ", messageSource='" + messageSource + '\'' +
            ", sourceService='" + sourceService + '\'' +
            ", sourceVersion='" + sourceVersion + '\'' +
            ", tags=" + tags +
            ", related=" + related +
            ", actor=" + actor +
            ", action=" + action +
            ", resource=" + resource +
            '}';
    }

    public static class Builder {
        private String messageId;
        private Instant eventTime;
        private String correlationId;
        private Instant eventSent;
        private Instant eventStored;
        private String messageSource;
        private String sourceService;
        private String sourceVersion;
        private Set<String> tags = new TreeSet<>();
        private Set<String> related = new TreeSet<>();
        private final Actor actor;
        private final Action action;
        private final Resource resource;
        private Clock clock;

        public Builder(Actor actor, Action action, Resource resource) {
            this.actor = actor;
            this.action = action;
            this.resource = resource;
        }

        public Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder withMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder withEventTime(Instant eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public Builder withCorrelationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder withEventSent(Instant eventSent) {
            this.eventSent = eventSent;
            return this;
        }

        public Builder withEventStored(Instant eventStored) {
            this.eventStored = eventStored;
            return this;
        }

        public Builder withMessageSource(String messageSource) {
            this.messageSource = messageSource;
            return this;
        }

        public Builder withSourceService(String sourceService) {
            this.sourceService = sourceService;
            return this;
        }

        public Builder withSourceVersion(String sourceVersion) {
            this.sourceVersion = sourceVersion;
            return this;
        }

        public Builder withTags(Set<String> tags) {
            if (tags != null) {
                this.tags.addAll(tags);
            }
            return this;
        }

        public Builder withTags(String... tags) {
            for (String tag : tags) {
                this.tags.add(tag);
            }
            return this;
        }

        public Builder withRelated(Set<String> related) {
            if (related != null) {
                this.related.addAll(related);
            }
            return this;
        }

        public Builder withRelated(String... related) {
            for (String rel : related) {
                this.related.add(rel);
            }
            return this;
        }

        public EventMessage build() {
            return new EventMessage(this);
        }
    }
}
