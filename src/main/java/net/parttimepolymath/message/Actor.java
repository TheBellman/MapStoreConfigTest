package net.parttimepolymath.message;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Actor implements Serializable {
    private final String type;
    private final String identifier;
    private final String actingFor;
    private final Details details;
    private final Map<String, String> attributes = new TreeMap<>();

    public Actor(final String type, final String identifier, final String actingFor,
                 final Details details, final Map<String, String> attributes) {
        this.type = type;
        this.identifier = identifier;
        this.actingFor = actingFor;
        this.details = details;
        if (attributes != null) {
            this.attributes.putAll(attributes);
        }
    }

    public Actor(final String type, final String identifier, final String actingFor, final Details details) {
        this(type, identifier, actingFor, details, null);
    }

    public Actor(final String type, final String identifier, final String actingFor) {
        this(type, identifier, actingFor, null, null);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getActingFor() {
        return actingFor;
    }

    public Details getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Actor actor = (Actor) o;
        return Objects.equal(type, actor.type) && Objects.equal(identifier, actor.identifier) && Objects.equal(actingFor, actor.actingFor) && Objects.equal(details, actor.details);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, identifier, actingFor, details);
    }

    @Override
    public String toString() {
        return "Actor{" +
            "type='" + type + '\'' +
            ", identifier='" + identifier + '\'' +
            ", actingFor='" + actingFor + '\'' +
            ", details=" + details +
            '}';
    }
}
