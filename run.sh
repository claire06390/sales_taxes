#!/bin/bash


echo "Creating sales taxes application container"
docker-compose -f docker-compose.yml up -d --build