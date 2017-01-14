# Airtraffic app for dump1090#

![SDR logo](https://github.com/tsypuk/airtraffic/blob/master/config/img/sdr.jpg)
![Orange PI logo](https://github.com/tsypuk/airtraffic/blob/master/config/img/orangepi.png)
![Docker logo](https://github.com/tsypuk/airtraffic/blob/master/config/img/docker.png)

[![Build Status](https://travis-ci.org/tsypuk/airtraffic.svg?branch=master)](https://travis-ci.org/tsypuk/airtraffic)
[![star this repo](http://githubbadges.com/star.svg?user=tsypuk&repo=airtraffic&style=default)](https://github.com/tsypuk/airtraffic)
[![fork this repo](http://githubbadges.com/fork.svg?user=tsypuk&repo=airtraffic&style=default)](https://github.com/tsypuk/airtraffic/fork)

---
OrangePI is deployed with java8, dump1090, has connected SDR RT2832 to it. 
The setup process to orangePI is fully automated with Ansible runbooks.

Spring boot application can be deployed to OrangePI or run on container. It polls dump1090 REST endpoint on orangePI, reads info about plains and persists to DB.  

## CUSTOM DOCKER IMAGES FOR
<ul>
<li>Mysql with precreated schema 'air'</li>
<li>ELK. Logstash with turned off ssl works on 5044</li>
<li>Spring boot app with installed filebeat, configured to send logs to ELK</li>
<li>SonarQube</li>
</ul>

## ANSIBLE RUNBOOKS

BuildAllDockerImages.yml
SetupOrangeRunbook.yml
DeploymentRunbook.yml

## TODO ITEMS

- [x] Created app
- [x] Add docker integration
- [x] Add MockMvc tests
- [ ] Optimize calls to DB
- [x] Docker Timing problem
- [x] Add fileBeat, ELK integration
- [ ] Add SonarQube code scan
- [x] Update MD documentation with buttons and images
- [ ] Add spring sleuth and zipkin correlation ID
- [ ] Add swager documentation
- [ ] Add restdoc generation on tests running
- [ ] Add springboot endpoint for future UI integration
- [ ] Add Angular UI to display flights, calculate the radius of antenna
- [ ] Integrate with google, yandex maps
- [ ] Use modules of ansible for docker
- [ ] Add monitoring system for containers
- [ ] Use roles in ansible


## NOTES

__This is a issue with the timezone.__

https://wiki.alpinelinux.org/wiki/Setting_the_timezone
_Setting the timezone
Note: Only for NON uclibc installs!!!
glibc based installs use different timezone setup._

_apk add tzdata
ls /usr/share/zoneinfo
Suppose you want to use Brussels First copy the proper zone to localtime_

_cp /usr/share/zoneinfo/Europe/Brussels /etc/localtime
Now specify your timezone_

_echo "Europe/Brussels" >  /etc/timezone
date
Result: Wed Mar 8 00:46:05 CET 2006_

_You can now remove the other timezones
apk del tzdata_

__You can manually build docker images with following commands__ 

Build app image

docker build --rm --label airtraffic_with_FileBeat_TimeZone -t 'airtraffic_app:latest' .

Build mysql image

docker build --rm --label airtraffic_mysql -t 'airtraffic_mysql:latest' .

Build ELK image

docker build --rm --label airtraffic_elk -t 'airtraffic_elk:latest' .

Build SonarQube image

docker build --rm --label airtraffic_sonar -t 'airtraffic_sonar:latest' .

Start application with all containers

docker-compose -f src/main/docker/app.yml up -d

__To construct logstash filters pattern use__ 
http://grokdebug.herokuapp.com

There is autodiscovery and online verifier
ex:
2017-01-07 13:15:07.732  INFO 29830 --- [pool-2-thread-1] u.i.s.Dump1090SheduledPollingService     : QueryDump1090 200 2
%{TIMESTAMP_ISO8601:logtime}%{SPACE}%{LOGLEVEL:loglevel} %{SPACE}%{DATA:pid}---\s+\[%{DATA:thread}\]\s+%{DATA:class}\s+:\s+%{WORD:rest} %{SPACE}%{NUMBER:response} %{SPACE}%{NUMBER:flightsCount}