FROM openjdk:8-jre-alpine
#FROM moander/java8
MAINTAINER Roman Tsypuk <tsypuk.rb@gmail.com>

#################################
# Build and environment variables
#################################
ENV FILEBEAT_VERSION 1.1.1
ENV FILEBEAT_URL https://download.elastic.co/beats/filebeat/filebeat-${FILEBEAT_VERSION}-x86_64.tar.gz
ENV FILEBEAT_HOME /opt/filebeat-${FILEBEAT_VERSION}-x86_64
ENV PATH $PATH:${FILEBEAT_HOME}
ENV AIR_SLEEP 10
ENV TERM xterm
ENV TZ=Europe/Kiev
#ENV SPRING_PROFILES_ACTIVE docker

# Adding directly the jar-file
ADD  build/libs/airtraffic-0.0.1-SNAPSHOT.jar airtraffic.jar
RUN sh -c 'touch /airtraffic.jar'

#######################
# FileBeat Installation
#######################
# Fix the issue with wget and certificates:
# https://github.com/Yelp/dumb-init/issues/73
RUN apk --no-cache add --update ca-certificates bash wget ca-certificates openssl wget curl python mc vim tzdata \
    && update-ca-certificates

RUN cp /usr/share/zoneinfo/$TZ /etc/localtime
RUN echo $TZ > /etc/timezone
RUN apk del tzdata

WORKDIR /opt/

RUN wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/unreleased/glibc-2.23-r3.apk" \
         "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/unreleased/glibc-bin-2.23-r3.apk" \
    && apk add --allow-untrusted glibc-2.23-r3.apk glibc-bin-2.23-r3.apk

RUN curl -sL ${FILEBEAT_URL} | tar xz -C .

ADD src/main/docker/filebeat.yml /opt/filebeat-${FILEBEAT_VERSION}-x86_64/filebeat.yml

VOLUME /tmp

######################################
# Starting FileBeat and airtraffic.jar
######################################
CMD echo "Starting FileBeat..." \
    && /opt/filebeat-${FILEBEAT_VERSION}-x86_64/filebeat -c /opt/filebeat-${FILEBEAT_VERSION}-x86_64/filebeat.yml > /dev/null \
    & echo "AirTraffic application will start in ${AIR_SLEEP}s..." \
    && sleep ${AIR_SLEEP} \
    && java -Djava.security.egd=file:/dev/./urandom -jar /airtraffic.jar