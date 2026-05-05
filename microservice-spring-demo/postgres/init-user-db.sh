#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dbshipping;
    CREATE DATABASE dbinvoicing;
    CREATE DATABASE dborder;
EOSQL
