Repository contains:

1. Custom docker images for:

<ul>
<li>Mysql</li>
<li>ELK</li>
<li>Spring boot app with installed filebeat</li>
<li>SonarQube</li>
</ul>

2. Ansible runbook to build all docker images and start containers.

3. Spring boot application to query Dump1090 REST service and dump flights info to DB.

TODO items:
<ul>
<li>Add MockMvc tests <BR></li>
<li>Optimize calls to DB</li>
<li>Docker Timing problem</li>
<li>logstash multifilters add tags</li>
<li>Create MD documentation with buttons</li>
<li>Add spring sleuth and zipkin correlation ID</li>
<li>Add Angular UI to display flights, calculate the radius of antenna</li>
<li>Integrate with google, yandex maps</li>
</ul>


#######
https://wiki.alpinelinux.org/wiki/Setting_the_timezone
Setting the timezone
Note: Only for NON uclibc installs!!!
glibc based installs use different timezone setup.

apk add tzdata
ls /usr/share/zoneinfo
Suppose you want to use Brussels First copy the proper zone to localtime

cp /usr/share/zoneinfo/Europe/Brussels /etc/localtime
Now specify your timezone

echo "Europe/Brussels" >  /etc/timezone
date
Result: Wed Mar 8 00:46:05 CET 2006

You can now remove the other timezones

apk del tzdata

##########
How to manually build images, add containers and run app.
// TODO possibly this need to be automated with ansible for local build:
Build app image

//TODO Add variables to ansible build docker script
//TODO add jenkins image and configure to this repository.

docker build --rm --label airtraffic_with_FileBeat_TimeZone -t 'airtraffic_app:latest' .

Build mysql image
docker build --rm --label airtraffic_mysql -t 'airtraffic_mysql:latest' .
TODO Add creating the schema in MYSQL

Build ELK image
docker build --rm --label airtraffic_elk -t 'airtraffic_elk:latest' .
TODO Add copying the configuration file of logstash to ELK

Build sonarQube image
docker build --rm --label airtraffic_sonar -t 'airtraffic_sonar:latest' .


To configure ELK logstash pattern use http://grokdebug.herokuapp.com
For example:
2017-01-07 13:15:07.732  INFO 29830 --- [pool-2-thread-1] u.i.s.Dump1090SheduledPollingService     : QueryDump1090 200 2
%{TIMESTAMP_ISO8601:logtime}%{SPACE}%{LOGLEVEL:loglevel} %{SPACE}%{DATA:pid}---\s+\[%{DATA:thread}\]\s+%{DATA:class}\s+:\s+%{WORD:rest} %{SPACE}%{NUMBER:response} %{SPACE}%{NUMBER:flightsCount}



