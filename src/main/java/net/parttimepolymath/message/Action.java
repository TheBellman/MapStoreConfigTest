package net.parttimepolymath.message;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Action implements Serializable {
    private final String type;
    private final Details actionDetails;
    private final boolean success;
    private final Details successDetails;
    private final Map<String, String> attributes = new TreeMap<>();

    public Action(final String type, final Details actionDetails, final boolean success, final Details successDetails, Map<String, String> attributes) {
        this.type = type;
        this.actionDetails = actionDetails;
        this.success = success;
        this.successDetails = successDetails;
        if (attributes != null) {
            this.attributes.putAll(attributes);
        }
    }

    public Action(final String type, final Details actionDetails, final boolean success, final Details successDetails) {
        this(type, actionDetails, success, successDetails, null);
    }

    public Action(final String type, final boolean success, final Details successDetails) {
        this(type, null, success, successDetails, null);
    }

    public Action(final String type, final Details actionDetails, final boolean success) {
        this(type, actionDetails, success, null, null);
    }

    public Action(final String type, final boolean success) {
        this(type, null, success, null, null);
    }

    public String getType() {
        return type;
    }

    public Details getActionDetails() {
        return actionDetails;
    }

    public boolean isSuccess() {
        return success;
    }

    public Details getSuccessDetails() {
        return successDetails;
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
        Action action = (Action) o;
        return success == action.success && Objects.equal(type, action.type) && Objects.equal(actionDetails, action.actionDetails) && Objects.equal(successDetails, action.successDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, actionDetails, success, successDetails);
    }

    @Override
    public String toString() {
        return "Action{" +
            "type='" + type + '\'' +
            ", actionDetails=" + actionDetails +
            ", success=" + success +
            ", successDetails=" + successDetails +
            '}';
    }
}
