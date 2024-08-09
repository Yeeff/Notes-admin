# **Notes Application**

This project is a application for managing text notes. The application is built using React for the frontend, Spring Boot for the backend, and PostgreSQL as the database.

## **Features**

- **Create, edit, and delete notes**
- **Archive and unarchive notes**
- **List active and archived notes**
- **Add and remove tags**
- **Role-based security with JWT authentication**
- **Tag-based filtering and management**

## **Technologies Used**

- **React**
- **Spring Boot**
- **Spring Security with JWT**
- **PostgreSQL**

## **Getting Started**

### **Prerequisites**

- **Bash/Zsh shell for running the script**
- **(Optional) Docker**

### **Running the Application**

You can run the application using either the provided shell script or Docker Compose.

#### **Option 1: Using the Shell Script**

1. **Clone the repository**:
    ```sh
    git clone <repository-url>
    cd <repository-folder>
    ```

2. **Run the shell script**:
    ```
    ./start.sh -d mydb -u myuser -p mypass
    ```

This will set up the database schema, build the application, and start all the required components.

#### **Option 2: Using Docker Compose**

1. **Clone the repository**:
    ```sh
    git clone <repository-url>
    cd <repository-folder>
    ```

2. **Run Docker Compose**:
    ```sh
    docker-compose up
    ```

This will build the application and start the frontend, backend, and PostgreSQL database.

### **Accessing the Application**

- **Frontend**: [http://localhost:3000/](http://localhost:3000/)
- **Backend API**: [http://localhost:8080/api](http://localhost:8080/api)

## **Project Structure**

- **frontend/**: React application
- **backend/**: Spring Boot application
- **database/**: PostgreSQL database setup
- **start.sh**: Shell script to set up and run the application
- **docker-compose.yml**: Docker Compose configuration file

## **Security and Authentication**

The application uses JWT for securing the API endpoints. The following two test users are available:

- **username**: daniel **Password**: 1234
- **username**: santiago **Password**: 1234

These users have predefined roles and can be used to test the security and authentication features of the application.
