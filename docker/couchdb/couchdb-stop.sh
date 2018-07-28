#!/bin/bash

name="ods-couchdb"
state=$(docker inspect --format "{{.State.Running}}" $name 2>/dev/null)
if [[ "$state" == "true" ]]; then
	echo "Container '$name' is running. Stop and remove container..."
	sudo docker stop $name 1>/dev/null
	sudo docker rm $name 1>/dev/null
fi