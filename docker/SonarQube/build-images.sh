#/!bin/bash
docker build --network=host --tag sonar-scanner-image:latest --build-arg SONAR_HOST="http://localhost:9000" --build-arg SONAR_LOGIN_TOKEN="4e96433cfc1d7f5a6d9c3a0525d591694c412495" .
