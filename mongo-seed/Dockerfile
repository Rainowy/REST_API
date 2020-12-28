FROM mongo:4
COPY people.json /people.json
COPY counters.json /counters.json
CMD mongoimport --host api-database --db humans --collection people --type json --file /people.json --jsonArray ;  mongoimport --host api-database --db humans --collection counters --type json --file /counters.json --jsonArray
