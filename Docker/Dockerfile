FROM centos:7

RUN yum install -y wget \
    && cd /opt \
    && wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u91-b14/jdk-8u91-linux-x64.tar.gz" \
    && tar xzf jdk-8u91-linux-x64.tar.gz \
    && alternatives --install /usr/bin/java java /opt/jdk1.8.0_91/bin/java 2 \
    && alternatives --install /usr/bin/jar jar /opt/jdk1.8.0_91/bin/jar 2 \
    && alternatives --set jar /opt/jdk1.8.0_91/bin/jar \
    && echo "export JAVA_HOME=/opt/jdk1.8.0_91" >> ~/.bashrc \
    && echo "export JRE_HOME=/opt/jdk1.8.0_91/jre" >> ~/.bashrc \
    && echo "export PATH=$PATH:JAVA_HOME/bin:$JRE_HOME/bin"  >> ~/.bashrc \
    && cd / \    
    && wget http://54.209.160.169:8081/artifactory/libs-release-local/net/parttimepolymath/mapconfigtest/1.1/mapconfigtest-1.1-jar-with-dependencies.jar

CMD ["java", "-jar", "/mapconfigtest-1.1-jar-with-dependencies.jar"]

