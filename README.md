# ToDoApp [![Build Status](https://travis-ci.com/arora-sahil/ToDoApp.svg?branch=master)](https://travis-ci.com/arora-sahil/ToDoApp)
It is made to keep track of your upcoming day to day tasks.

## Installation

Use devSql profile if we want to persist the data in SQL. It uses h2 by default. See properties file for the port and configurations. 

Add this in Run configuration of main application.

```
spring.profiles.active = default or devSql
```

## Note: 
There are no extra configurations required to run unit tests and integration tests. Just set the profile if you want to SQL.



