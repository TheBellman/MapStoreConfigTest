package net.parttimepolymath.utility;

import com.google.common.base.Strings;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.SetConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * reimplementation of the Spring FactoryBean
 */
public class HazelcastInstanceFactory {

    private static final int FIVE_MINUTES = 300;
    private final HazelcastInstance instance;
    private static Logger logger = LoggerFactory.getLogger(HazelcastInstanceFactory.class);

    public HazelcastInstanceFactory(int registrationTtl) {
        this(GroupConfig.DEFAULT_GROUP_NAME, GroupConfig.DEFAULT_GROUP_PASSWORD, registrationTtl, "",
            NetworkConfig.DEFAULT_PORT, MulticastConfig.DEFAULT_MULTICAST_PORT, MulticastConfig.DEFAULT_MULTICAST_GROUP);
    }

    public HazelcastInstanceFactory(String clusterName, String clusterPassword, int registrationTtl, String trustedInterface, int port, int multicastPort, String multicastGroup) {

        MulticastConfig multicastConfig = new MulticastConfig()
            .setEnabled(true)
            .setMulticastGroup(multicastGroup)
            .setMulticastPort(multicastPort)
            .addTrustedInterface(trustedInterface);

        JoinConfig joinConfig = new JoinConfig()
            .setMulticastConfig(multicastConfig);

        NetworkConfig networkConfig = new NetworkConfig()
            .setPort(port)
            .setPortAutoIncrement(true)
            .addOutboundPort(0)
            .setJoin(joinConfig);

        Config config = new Config();
        config.setNetworkConfig(networkConfig);
        config.getGroupConfig()
            .setName(makeName(clusterName))
            .setPassword(clusterPassword == null ? GroupConfig.DEFAULT_GROUP_PASSWORD : clusterPassword);
        config.setProperty("hazelcast.phone.home.enabled", "false");
        config.getQueueConfigs().put("*", new QueueConfig().setEmptyQueueTtl(registrationTtl + FIVE_MINUTES));
        config.getSetConfigs().put("*", new SetConfig().setBackupCount(1));

        logger.info("Constructed HazelcastInstance configuration: {}", config);

        instance = Hazelcast.newHazelcastInstance(config);
    }

    private String makeName(String clusterName) {
        if (Strings.isNullOrEmpty(clusterName) || clusterName.equals(GroupConfig.DEFAULT_GROUP_NAME)) {
            return String.format("%s:%s", GroupConfig.DEFAULT_GROUP_NAME, UUID.randomUUID().toString());
        }

        return clusterName;
    }

    public HazelcastInstance getObject() throws Exception {
        return instance;
    }

    public Class<?> getObjectType() {
        return HazelcastInstance.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        logger.info("calling instance.shutdown()");
        instance.getLifecycleService().shutdown();
//        instance.shutdown();
    }
}
