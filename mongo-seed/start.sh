#!/bin/bash

sleep 30
mongoimport --host api-database --db humans --collection people --type json --file /init.json --jsonArray
