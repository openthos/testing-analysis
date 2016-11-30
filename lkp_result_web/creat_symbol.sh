#!/bin/sh
apt-get install -y  apache2 
apt-get install -y  php5
apt-get install -y  libapache2-mod-php5

cp ./* -R  /var/www/html
cd /var/www/html
ln -s /mnt/freenas/result/ .
ln -s /mnt/freenas/summary/ .
ln -s /mnt/freenas/compile/ .
cd /var/www		
chmod 777 -R html
