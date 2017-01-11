TODO items:
<ul>
<li>Add logstash and fileBeat</li>done
<li>Add integration with ELK</li>done
<li>Add MockMvc tests <BR></li>done
<li>Optimize calls to DB</li>
<li>Docker Timing problem</li>
<li>logstash multifilters</li>
<li>Create MD documentation</li>
<li>Push to GIT</li>
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

Build app image
docker build --rm --label airtraffic_with_FileBeat_TimeZone -t 'airtraffic:latest' .

Build mysql image
docker build --rm --label airtraffic_mysql -t 'airtraffic_mysql:latest' .

