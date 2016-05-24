package net.parttimepolymath.registrations;

import com.google.common.base.Objects;

public class StreamStatus {
    private String registrationId;
    private String queueName;
    private int queueSize;
    private int pendingCount;

    public StreamStatus() {
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getQueueSize() {
        return queueSize;
    }

    @Override
    public String toString() {
        return "StreamStatus{registrationId='" + registrationId + '\'' +
            ", queueName='" + queueName + '\'' +
            ", queueSize=" + queueSize + ", " +
            "pendingCount=" + pendingCount + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StreamStatus that = (StreamStatus) o;
        return queueSize == that.queueSize
            && Objects.equal(registrationId, that.registrationId)
            && Objects.equal(queueName, that.queueName)
            && pendingCount == that.pendingCount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registrationId, queueName, queueSize, pendingCount);
    }
}
