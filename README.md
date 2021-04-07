# MapStoreConfigTest

This is a test to try to demonstrate the MapStoreConfig bug experienced in a HazelCast cluster. It is extracted from the actual server code that exhibits the problem.
That code is a Spring web application, bundled into a WAR and run from within a Tomcat instance. This code thus has a lot of additional bits and pieces to allow the key pieces to be built,
while modifying them from the originals as little as possible.

## Building

This assumes Java 8 (I built with 1.8.0_51-b16 on Mac OS), and Maven 3.3.3 or better in your execution path. Check the code out from GIT and build:

    mvn package


this should produce

    target/mapconfigtest-1.2-SNAPSHOT-jar-with-dependencies.jar

## Running

*to be completed - I was running inside docker*

    usage: mapconfigtest
      -?,--help      print this message
      -t,--trusted <arg>   trusted interface
      -v,--version   print version

Executing with no options should start the embedded HazelcastInstance and try to invoke the map request that is failing. Halting the process *should* invoke the correct shutdown hooks to attempt a clean shutdown

    java -jar target/mapconfigtest-1.0-SNAPSHOT-jar-with-dependencies.jar
    2016-05-24 10:24:32,775 INFO  [main] net.parttimepolymath.Server: Server construction starts
    2016-05-24 10:24:32,797 INFO  [main] net.parttimepolymath.utility.HazelcastInstanceFactory: Constructed HazelcastInstance configuration: Config{groupConfig=GroupConfig [name=dev_docker, password=*******], properties={hazelcast.phone.home.enabled=false}, networkConfig=NetworkConfig{publicAddress='null', port=5701, portCount=100, portAutoIncrement=true, join=JoinConfig{multicastConfig=MulticastConfig [enabled=true, multicastGroup=224.2.2.3, multicastPort=54327, multicastTimeToLive=32, multicastTimeoutSeconds=2, trustedInterfaces=[127.0.0.*,172.17.0.*], loopbackModeEnabled=false], tcpIpConfig=TcpIpConfig [enabled=false, connectionTimeoutSeconds=5, members=[], requiredMember=null], awsConfig=AwsConfig{enabled=false, region='us-east-1', securityGroupName='null', tagKey='null', tagValue='null', hostHeader='ec2.amazonaws.com', iamRole='null', connectionTimeoutSeconds=5}, discoveryProvidersConfig=com.hazelcast.config.DiscoveryConfig@cac736f}, interfaces=InterfacesConfig{enabled=false, interfaces=[]}, sslConfig=null, socketInterceptorConfig=null, symmetricEncryptionConfig=null}, mapConfigs={}, topicConfigs={}, reliableTopicConfigs={}, queueConfigs={*=QueueConfig{name='null', listenerConfigs=null, backupCount=1, asyncBackupCount=0, maxSize=0, emptyQueueTtl=7500, queueStoreConfig=null, statisticsEnabled=true}}, multiMapConfigs={}, executorConfigs={}, semaphoreConfigs={}, ringbufferConfigs={}, wanReplicationConfigs={}, listenerConfigs=[], partitionGroupConfig=PartitionGroupConfig{enabled=false, groupType=PER_MEMBER, memberGroupConfigs=[]}, managementCenterConfig=ManagementCenterConfig{enabled=false, url='null', updateInterval=3}, securityConfig=SecurityConfig{enabled=false, memberCredentialsConfig=CredentialsFactoryConfig{className='null', implementation=null, properties={}}, memberLoginModuleConfigs=[], clientLoginModuleConfigs=[], clientPolicyConfig=PermissionPolicyConfig{className='null', implementation=null, properties={}}, clientPermissionConfigs=[]}, liteMember=false}
    May 24, 2016 11:24:32 AM com.hazelcast.instance.DefaultAddressPicker
    INFO: [LOCAL] [dev_docker] [3.6.2] Prefer IPv4 stack is true.
    May 24, 2016 11:24:32 AM com.hazelcast.instance.DefaultAddressPicker
    INFO: [LOCAL] [dev_docker] [3.6.2] Picked Address[192.168.130.236]:5701, using socket ServerSocket[addr=/0:0:0:0:0:0:0:0,localport=5701], bind any local is true
    May 24, 2016 11:24:32 AM com.hazelcast.system
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Hazelcast 3.6.2 (20160405 - 0f88699) starting at Address[192.168.130.236]:5701
    May 24, 2016 11:24:32 AM com.hazelcast.system
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
    May 24, 2016 11:24:32 AM com.hazelcast.system
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Configured Hazelcast Serialization version : 1
    May 24, 2016 11:24:32 AM com.hazelcast.spi.OperationService
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Backpressure is disabled
    May 24, 2016 11:24:33 AM com.hazelcast.spi.impl.operationexecutor.classic.ClassicOperationExecutor
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Starting with 4 generic operation threads and 8 partition operation threads.
    May 24, 2016 11:24:33 AM com.hazelcast.instance.Node
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Creating MulticastJoiner
    May 24, 2016 11:24:33 AM com.hazelcast.core.LifecycleService
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Address[192.168.130.236]:5701 is STARTING
    May 24, 2016 11:24:33 AM com.hazelcast.nio.tcp.nonblocking.NonBlockingIOThreadingModel
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] TcpIpConnectionManager configured with Non Blocking IO-threading model: 3 input threads and 3 output threads
    May 24, 2016 11:24:36 AM com.hazelcast.cluster.impl.MulticastJoiner
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2]


    Members [1] {
	    Member [192.168.130.236]:5701 this
    }

    May 24, 2016 11:24:36 AM com.hazelcast.core.LifecycleService
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Address[192.168.130.236]:5701 is STARTED
    2016-05-24 10:24:36,131 INFO  [main] net.parttimepolymath.Server: Server construction ends
    2016-05-24 10:24:36,131 INFO  [main] net.parttimepolymath.Server: Server start begins
    May 24, 2016 11:24:36 AM com.hazelcast.partition.InternalPartitionService
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Initializing cluster partition table arrangement...
    2016-05-24 10:24:36,155 INFO  [cached1] net.parttimepolymath.registrations.RegistrationCacheStore: loadAllKeys()
    2016-05-24 10:24:36,155 INFO  [cached1] net.parttimepolymath.queue.QueueManagerImpl: fetchRegistrationIds()
    2016-05-24 10:24:36,225 INFO  [main] net.parttimepolymath.Server: Server close ends - 0 registrations known
    2016-05-24 10:24:36,225 INFO  [main] net.parttimepolymath.Server: Server touch 0
    2016-05-24 10:24:37,226 INFO  [main] net.parttimepolymath.Server: Server touch 1
    2016-05-24 10:24:38,231 INFO  [main] net.parttimepolymath.Server: Server touch 2
    ^C
    2016-05-24 10:24:38,627 INFO  [Thread-1] net.parttimepolymath.Server: Server start begins
    2016-05-24 10:24:38,627 INFO  [Thread-1] net.parttimepolymath.queue.QueueManagerImpl: close()
    2016-05-24 10:24:38,627 INFO  [Thread-1] net.parttimepolymath.utility.HazelcastInstanceFactory: calling instance.shutdown()
    May 24, 2016 11:24:38 AM com.hazelcast.instance.Node
    INFO: [192.168.130.236]:5701 [dev_docker] [3.6.2] Running shutdown hook... Current state: ACTIVE
    2016-05-24 10:24:38,678 INFO  [Thread-1] net.parttimepolymath.Server: Server close ends

## Running with Docker

You will notice when you check out this repository that there is a Docker folder containing a *Dockerfile*, which you may find convenient for running several instances of the test.  From the commandline, assuming you have Docker installed and your environment is correctly configured:

    cd Docker
    docker build -t test/mapstoreconfigtest .

after quite a bit of grinding, the image will be available for you to execute. Note that this docker image is built on top of Centos 7, pulls a Java 8 JDK from Oracle, and finally pulls the 1.1 release of this tool from my private Artifactory. Getting your own build of this tool onto the Docker image is left as an exercise for the student. To run instances:

    docker run --name test1 test/mapstoreconfigtest
    docker run --name test2 test/mapstoreconfigtest

halting the Docker process cleanly closes the running instance.
