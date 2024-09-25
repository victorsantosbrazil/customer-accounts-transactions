<h1 align="center">
Customer accounts transactions
</h1>

<h4 align="center">
v1.0.0
</h4>

## 📰 Description
The Customer Account and Transactions Service is a robust application designed to manage financial 
operations for banking and fintech platforms. It provides a comprehensive solution for handling 
customer accounts and processing various types of financial transactions. The service offers secure,
scalable, and efficient management of account information, balance tracking, and transaction processing,
while adhering to industry standards for data protection and regulatory compliance. It serves as an ideal 
foundation for developing sophisticated financial applications in today's digital banking environment.

##  📔 Table of Contents
<!--ts-->
   * [How to use](#-how-to-use)
      * [Setup](#setup)
      * [Run](#run)
        * [Pre-requirements](#pre-requirements)
          * [Local enviroment](#local-environment)
        * [Host or VM](#host-or-vm)
        * [Container](#container)
   * [Environment Variables](#-environment-variables)
   * [Tools](#-tools)
<!--te-->

## 🎮 How to use

###  Setup

Clone repository
```
git clone git@github.com:victorsantosbrazil/customer-accounts-transactions.git
```

Enter the project folder
```
cd customer-accounts-transactions
```

Install
```
./mvnw clean install
```

### Run

#### Pre-requirements
* [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [PostgreSQL database](https://www.postgresql.org/download/)
* [Docker](https://docs.docker.com/engine/install/)

##### Local environment
* To quickly set up a local environment, run:
```
docker-compose up
```

#### Host or VM
```
./mvnw spring-boot:run
```

#### Container

Before running the application, you need to create a docker image, do this by running the following command:
```
docker build -t customer-accounts-transactions .
```

To run:
```
docker run customer-accounts-transactions
```
#### 

## 🌍 Environment variables
This application uses the following environment variables for configuration. You can set these variables when running 
the application locally or in a container.

### Database Configuration

| Variable Name | Description              | Default Value |
|---------------|--------------------------|---------------|
| `DB_HOST`     | Database server hostname | localhost     |
| `DB_PORT`     | Database server port     | 5432          |
| `DB_SCHEMA`   | Database schema          | db            |
| `DB_USERNAME` | Database username        | dbuser        |
| `DB_PASSWORD` | Database password        | dbpass        |

## 🔨 Tools

The following tools were used to build this project:

* [Spring 3+](https://spring.io/) 
* [Maven](https://maven.apache.org/) 
* [Spotless](https://github.com/diffplug/spotless/tree/main/plugin-maven) 
* [JaCoCo](https://github.com/jacoco/jacoco)
* [OWASP Dependency Check](https://jeremylong.github.io/DependencyCheck/dependency-check-maven/)
* [PostgreSQL](https://www.postgresql.org/) 
* [Flyway](https://www.red-gate.com/products/flyway/community/) 
* [Docker](https://www.docker.com/)