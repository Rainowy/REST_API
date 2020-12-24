#!/bin/bash


mongoimport --host api-database --db humans --collection people --type json --file /mongo-seed/people.json --jsonArray
mongoimport --host api-database --db humans --collection counters --type json --file /mongo-seed/counters.json --jsonArray
