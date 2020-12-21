FROM mongo:3.2.4
COPY init.json /init.json
CMD mongoimport --host api-database --db humans --collection people --type json --file /init.json --jsonArray
