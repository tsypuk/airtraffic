FROM sonarqube:6.0-alpine
ENV TZ=Europe/Kiev
RUN echo $TZ | tee /etc/timezone
RUN dpkg-reconfigure --frontend noninteractive tzdata

#/bin/sh: dpkg-reconfigure: not found