package net.parttimepolymath.message;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Resource implements Serializable {
    private final String type;
    private final String resource;
    private final String service;
    private final Details details;
    private final Map<String, String> attributes = new TreeMap<>();

    public Resource(final String type, final String resource, final String service, final Details details, Map<String, String> attributes) {
        this.type = type;
        this.resource = resource;
        this.service = service;
        this.details = details;
        if (attributes != null) {
            this.attributes.putAll(attributes);
        }
    }

    public Resource(final String type, final String resource, final String service) {
        this(type, resource, service, null, null);
    }

    public Resource(final String type, final String resource, final String service, final Details details) {
        this(type, resource, service, details, null);
    }

    public String getType() {
        return type;
    }

    public String getResource() {
        return resource;
    }

    public String getService() {
        return service;
    }

    public Details getDetails() {
        return details;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource1 = (Resource) o;
        return Objects.equal(type, resource1.type) && Objects.equal(resource, resource1.resource) && Objects.equal(service, resource1.service) && Objects.equal(details, resource1.details);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, resource, service, details);
    }

    @Override
    public String toString() {
        return "Resource{" +
            "type='" + type + '\'' +
            ", resource='" + resource + '\'' +
            ", service='" + service + '\'' +
            ", details=" + details +
            '}';
    }
}
