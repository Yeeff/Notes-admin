# frontend/Dockerfile

# Use a Node.js base image
FROM node:22.4-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the application
RUN npm run build

# Install serve to serve the build files
RUN npm install -g serve

# Serve the application
CMD ["serve", "-s", "build"]
