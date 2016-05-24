package net.parttimepolymath.registrations;

import com.google.common.base.Objects;
import net.parttimepolymath.message.EventMessage;

import java.io.Serializable;

public class Criterion implements Serializable {
    private String actorType;
    private String actionType;
    private String resourceType;

    public Criterion(Criterion original) {
        actorType = original.getActorType();
        actionType = original.getActionType();
        resourceType = original.getResourceType();
    }

    private Criterion(Builder builder) {
        actorType = builder.actorType;
        actionType = builder.actionType;
        resourceType = builder.resourceType;
    }

    public String getActorType() {
        return actorType;
    }

    public String getActionType() {
        return actionType;
    }

    public String getResourceType() {
        return resourceType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Criterion criterion = (Criterion) other;
        return Objects.equal(actorType, criterion.actorType)
            && Objects.equal(actionType, criterion.actionType)
            && Objects.equal(resourceType, criterion.resourceType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actorType, actionType, resourceType);
    }

    public boolean matches(final EventMessage message) {
        if (message == null) {
            return false;
        }

        return optionalMatch(actorType, message.getActor().getType())
            && optionalMatch(actionType, message.getAction().getType())
            && optionalMatch(resourceType, message.getResource().getType());
    }

    private boolean optionalMatch(final String criterion, final String value) {
        return criterion == null || criterion.equals(value);
    }

    @Override
    public String toString() {
        return "Criterion{actorType='" + actorType + '\'' +
            ", actionType='" + actionType + '\'' +
            ", resourceType='" + resourceType + "\'}";
    }

    public static class Builder {
        private String actorType;
        private String actionType;
        private String resourceType;

        public Builder withActorType(final String actorType) {
            this.actorType = actorType;
            return this;
        }

        public Builder withActionType(final String actionType) {
            this.actionType = actionType;
            return this;
        }

        public Builder withResourceType(final String resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public Criterion build() {
            return new Criterion(this);
        }
    }
}
