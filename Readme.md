# Read Me First

Prerequisites: Docker

Before starting the app run: docker compose -f docker-compose.yaml up  


## Requirements

Build a REST application from scratch that could serve as a work planning service.

Business requirements:

- A worker has shifts
- A shift is 8 hours long
- A worker never has two shifts on the same day
- It is a 24 hour timetable 0-8, 8-16, 16-24


Preferably write a couple of units tests.

## Endpoints

### Save Worker
```
{
    "workerName": "Example Name"
}
```

### Get worker by id

```
http://localhost:8080/worker/1
```

### Filter shifts by worker id and dates
```
http://localhost:8080/shift?workerId=1&dateStart=01-11-2022&dateEnd=30-11-2022
```

### Save Shift
```
{
    "worker":{
        "workerId" : 1
    },
    "shiftDay": "17-11-2022",
    "shiftTime": "SHIFT_0_8"
}
```


todos: 
add logs
tests
add validation on worker
sonarlint issues