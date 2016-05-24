package net.parttimepolymath.registrations;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Optional;

public class Registration implements Serializable {
    private String registrationId;
    private Criterion criterion;

    public Registration(Registration original) {
        registrationId = original.getRegistrationId();
        criterion = new Criterion(original.getCriterion());
    }

    public Registration(String registrationId, Criterion criterion) {
        this.registrationId = registrationId;
        this.criterion = criterion;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public Criterion getCriterion() {
        return Optional.ofNullable(criterion).orElse(new Criterion.Builder().build());
    }

    @Override
    public String toString() {
        return "Registration{registrationId='" + registrationId + '\'' + ", criterion=" + criterion + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Registration that = (Registration) o;
        return Objects.equal(registrationId, that.registrationId)
            && Objects.equal(criterion, that.criterion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registrationId, criterion);
    }
}
