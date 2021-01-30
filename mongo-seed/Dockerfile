FROM mongo:4
COPY people.json /people.json
COPY counters.json /counters.json
CMD mongoimport --host people_database --db humans --collection people --type json --drop --file /people.json --jsonArray ;  mongoimport --host people_git database --db humans --collection counters --type json --drop --file /counters.json --jsonArray
