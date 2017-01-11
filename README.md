TODO items:
<ul>
<li>Add logstash and fileBeat</li>done
<li>Add integration with ELK</li>done
<li>Add MockMvc tests <BR></li>done
<li>Optimize calls to DB</li>
<li>Docker Timing problem</li>
<li>logstash multifilters add tags</li>
<li>Create MD documentation with buttons</li>
<li>Push to GIT</li>done
<li>Add spring sleuth and zipkin</li>
<li>Create ansible for deployment</li>
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



