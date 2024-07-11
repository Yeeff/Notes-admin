#!/bin/bash

# Exit on any error
set -e

# Function to print usage
usage() {
  echo "Usage: $0 [-d <database name>] [-u <database user>] [-p <database password>]"
  exit 1
}

# Parse command line arguments
while getopts ":d:u:p:" opt; do
  case ${opt} in
    d)
      DB_NAME=$OPTARG
      ;;
    u)
      DB_USER=$OPTARG
      ;;
    p)
      DB_PASSWORD=$OPTARG
      ;;
    *)
      usage
      ;;
  esac
done

# Check if all parameters are provided
if [ -z "$DB_NAME" ] || [ -z "$DB_USER" ] || [ -z "$DB_PASSWORD" ]; then
  usage
fi

# Install frontend dependencies and build
echo "Installing frontend dependencies..."
cd frontend
npm install
npm run start
cd ..

# Set up PostgreSQL database
echo "Setting up PostgreSQL database..."
psql -U $DB_USER -c "DROP DATABASE IF EXISTS $DB_NAME;"
psql -U $DB_USER -c "CREATE DATABASE $DB_NAME;"
psql -U $DB_USER -c "CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD';"
psql -U $DB_USER -c "GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;"

# Run Spring Boot backend
echo "Running Spring Boot backend..."
cd backend
./mvnw spring-boot:run

echo "Application started successfully"
