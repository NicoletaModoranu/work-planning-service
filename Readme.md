# Read Me First

Prerequisites: Docker

Before starting the app, run: 
```
docker compose -f docker-compose.yaml up  
```

## Requirements

Build a REST application from scratch that could serve as a work planning service.

Business requirements:

- A worker has shifts
- A shift is 8 hours long
- A worker never has two shifts on the same day
- It is a 24 hour timetable 0-8, 8-16, 16-24


Preferably write a couple of units tests.

## Model Details

### Shifts

A shift is defined by an id, shift day, shift time and the worker's id.

Shift day must be in the format _dd-MM-yyyy_.

Shift time is an enum with the following options: SHIFT_0_8, SHIFT_8_16, SHIFT_16_24.

When saving a shift, the shift day, shift time and worker's id are mandatory. See the Endpoints section for more details.
### Worker

A worker is defined by an _id_ and a _name_.

When saving a worker, the _name_ is mandatory.

## Endpoints

### Save Worker
POST http://localhost:8080/worker

Body:

```
{
    "workerName": "Example Name"
}
```

### Get worker by id
GET

```
http://localhost:8080/worker/1
```

### Filter shifts by worker id and dates

GET
```
http://localhost:8080/shift?workerId=1&dateStart=01-11-2022&dateEnd=30-11-2022
```

### Save Shift
POST http://localhost:8080/shift

Body: 
```
{
    "worker":{
        "workerId" : 1
    },
    "shiftDay": "17-11-2022",
    "shiftTime": "SHIFT_0_8"
}
```