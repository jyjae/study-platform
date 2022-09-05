# switch.sh

#!/bin/bash

# Crawl current connected port of WAS
CURRENT_PORT=$(cat /home/ec2-user/service_url.inc  | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Nginx currently proxies to ${CURRENT_PORT}."

# Toggle port number
if [ ${CURRENT_PORT} -eq 9000 ]; then
    TARGET_PORT=9001
elif [ ${CURRENT_PORT} -eq 9001 ]; then
    TARGET_PORT=9000
else
    echo "> No WAS is connected to nginx"
    exit 1
fi

# Change proxying port into target port
echo "set \$service_url http://localhost:${TARGET_PORT};" | tee /home/ec2-user/service_url.inc

echo "> Now Nginx proxies to ${TARGET_PORT}."

# Reload nginx
sudo service nginx reload

echo "> Nginx reloaded."