# EXA - Spring Boot Application with Docker and PostgreSQL

This project is a simple application using **Spring Boot**, **PostgreSQL**, and **Docker**. It demonstrates how to create an isolated development environment with Docker, where all the necessary services (backend, database, etc.) are running in containers.

## Technologies

- **Java** 17
- **Spring Boot** 2.7.3
- **PostgreSQL** 13
- **Docker** and **Docker Compose**
- **Maven** for dependency management

## Features

- Connection to PostgreSQL database using Spring Data JPA.
- Simple RESTful API example.
- JWT-based authentication and authorization.
- Application built, packaged, and run inside Docker containers.

## Prerequisites

Before running this project, you need to ensure that the following tools are installed:

- **Docker**: [Official Documentation](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Official Documentation](https://docs.docker.com/compose/install/)

Make sure to verify that the following commands are correctly installed:

```bash
docker --version
docker-compose --version
```

## Running the application

### 1. Clone the repository

First clone the repository to your local machine:

```bash
git clone https://github.com/bastosgm/exa.git
cd exa
```

### 2. Build and start the application with Docker:

Run the following command to build the Docker images and start the services defined in the docker-compose.yml file:

```bash
docker-compose up --build
```

This command will:

- Build the application Docker image.
- Start the `app` (Spring Boot application) and `db` (PostgreSQL database) containers.
- Expose the application on port `8080` and the database on port `5432`.

### 3. Wait for the application to start:

The first time you run `docker-compose up --build`, Docker will download the necessary images (if not already present) and build the project. This might take some time, depending on your internet connection and system performance.

## Acessing the application

After running `docker-compose up`, the application will be available at:
- Backend (API): http://localhost:8080
- PostgreSQL Database: http://localhost:5432

## Directory Structure

The project structure is organized as follows:

```bash
├── src/                    # Application source code
│   ├── main/
│   │   ├── java/           # Java code of the application
│   │   └── resources/      # Configuration files
├── Dockerfile              # Defines how to build the Spring Boot application image
├── docker-compose.yml      # Defines Docker containers and services
├── pom.xml                 # Maven configuration file
├── README.md               # Presentation file
```

## Testing the Application

After running the application with docker-compose up --build, you can test the API using tools like Postman or cURL. Here's an example of a simple cURL test:

```bash
curl -X GET http://localhost:8080/users/1
```

You can find all available routes here:
- Swagger documentation: http://localhost:8080/swagger-ui/index.html

## Contributing

Even being as simples as possible, contributions are welcome! If you have any improvements or fixes, please feel fre and open a pull request.

## License

This project is licensed under the `MIT License`.

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=bastosgm_exa)