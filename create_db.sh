#!/bin/bash

DB_NAME="postgres"
DB_USER="postgres"
DB_PASSWORD="postgres"

export PGPASSWORD=$DB_PASSWORD

db_exists=$(psql -U $DB_USER -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME';")

if [[ $db_exists == "1" ]]; then
  echo "Error: База данных '$DB_NAME' уже создана."
  exit 1
else
  psql -U $DB_USER -c "CREATE DATABASE $DB_NAME;"
  echo "База данных '$DB_NAME' создалась успешно."
fi