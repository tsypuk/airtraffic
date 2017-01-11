FROM sebp/elk

MAINTAINER Roman Tsypuk <tsypuk.rb@gmail.com>

ENV TZ=Europe/Kiev
RUN echo $TZ | tee /etc/timezone
RUN dpkg-reconfigure --frontend noninteractive tzdata

# Add configuration file of logstash no ssl 5044 listen
ADD logstash.yml /etc/logstash/conf.d/02-beats-input.conf