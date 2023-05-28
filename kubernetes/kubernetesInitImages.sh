#!/bin/bash

kubectl apply -f event-api-rest-deployment.yaml,event-api-rest-service.yaml,event-mysql-deployment.yaml,event-mysql-service.yaml,event-server-deployment.yaml,event-server-service.yaml,kubernetes-default-networkpolicy.yaml
