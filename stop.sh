#!/bin/bash

# Stop and remove application container
echo "stopping all"
docker-compose -f docker-compose.yml down
