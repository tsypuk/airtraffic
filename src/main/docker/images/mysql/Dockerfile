FROM mysql:5.7.13

MAINTAINER Roman Tsypuk <tsypuk.rb@gmail.com>

ENV TZ=Europe/Kiev
RUN echo $TZ | tee /etc/timezone
RUN dpkg-reconfigure --frontend noninteractive tzdata

# ADD script for creating schema
#ADD schema.sql /docker-entrypoint-initdb.d