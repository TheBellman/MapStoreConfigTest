package net.parttimepolymath.registrations;

import com.hazelcast.map.AbstractEntryProcessor;

import java.time.Clock;
import java.util.Map;

public class RegistrationToucher extends AbstractEntryProcessor<String, RegistrationWrapper> {

    private Clock clock;

    public RegistrationToucher(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Object process(Map.Entry<String, RegistrationWrapper> entry) {
        RegistrationWrapper wrapper = entry.getValue();
        if (wrapper != null) {
            wrapper.touch(clock);
            entry.setValue(wrapper);
        }
        return entry.getValue();
    }
}
