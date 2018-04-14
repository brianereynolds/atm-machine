# ATM Machine Project

## Introduction
Software for an ATM machine. The software is responsible for validation customer account details
and performing basic operations including balance inquires and cash withdrawals.

## Quick start

### Gradle
```
gradle bootRun
```

### Docker
```
docker pull brianereynolds/atm-machine

docker run -p 7000:7000 brianereynolds/atm-machine
```

Open http://localhost:7000/ in your browser

## Swagger
A Swagger definition is also available.
```
http://localhost:7000/v2/api-docs
```

## CI/CD
# Github: https://github.com/brianereynolds/atm-machine
# Travis CI: 

## Kubernetes Monitoring

*Health*: ```http://localhost:7000/healthz```

A healthy server returns HTTP 200

*Ready*: ```http://localhost:7000/readinessz```

A ready server returns HTTP 200
