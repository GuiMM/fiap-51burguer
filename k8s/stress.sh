#!/bin/bash
set -B                  # enable brace expansion
for i in {1..1000000}; do
  curl -s -k 'GET' localhost:8080/swagger-ui/index.html
  sleep $1
done
