FROM sonarqube:6.0-alpine

MAINTAINER Roman Tsypuk <tsypuk.rb@gmail.com>

ENV TZ=Europe/Kiev
RUN echo $TZ | tee /etc/timezone
RUN dpkg-reconfigure --frontend noninteractive tzdata

#/bin/sh: dpkg-reconfigure: not found