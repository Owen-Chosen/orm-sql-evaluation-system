📖 **Overview**
This project presents a comparative performance analysis of Object-Relational Mapping (ORM) and Raw SQL in backend systems. 
It evaluates how both approaches behave under different workloads, dataset sizes, and concurrent user conditions.

The system is implemented as a Java Spring Boot RESTful API, with identical endpoints developed using both ORM (JPA/Hibernate) and Raw SQL (JDBC). 
Performance testing was conducted to measure response time, throughput, and scalability.

This repository supports the dissertation:
“Evaluating ORM vs Raw SQL in Backend Applications”

🎯 **Objectives**
1. Evaluate performance differences between ORM and Raw SQL
2. Analyse scalability across small, medium, and large datasets
3. Measure system behaviour under concurrent user loads
4. Identify trade-offs between abstraction and performance

🏗️ **System Architecture**
The application follows a layered architecture: Client → Controller → Service → Repository → Database
Controller Layer: Handles HTTP requests
Service Layer: Contains business logic
Repository Layer: Implements ORM and Raw SQL
Database: Stores users, products, orders, and order items

⚙️ **Technology Stack**
Backend: Java, Spring Boot
ORM: JPA / Hibernate
Raw SQL: JDBC
Database: MySQL / PostgreSQL (update if needed)
Testing Tool: Apache JMeter
Build Tool: Maven

🔌 **API Endpoints**
The following endpoints were implemented using both ORM and Raw SQL:
/users/{id}	GET	Retrieve user details
/products/{id}	GET	Retrieve product details
/orders/{id}	GET	Retrieve order and items
/orders/place	POST	Place a new order
/products/update-stock	PUT	Update product stock

🧪 **Performance Testing**
Performance tests were conducted using Apache JMeter under varying conditions:
Dataset Sizes
Small Dataset:
  1,000 users
  200 products
  5,000 orders
  15,000 order items
Medium Dataset:
  10,000 users
  2,000 products
  50,000 orders
  150,000 order items
Large Dataset:
  100,000 users
  20,000 products
  500,000 orders
  1,500,000 order items
Concurrent Users
  50 users
  100 users
  200 users
  500 users
  
📊 **Metrics Collected**
Average Response Time – Mean time taken per request
Minimum Response Time – Fastest response recorded
Maximum Response Time – Slowest response recorded
Throughput – Requests processed per second

🚀 **Getting Started**
Prerequisites
Java 17+
Maven
MySQL or PostgreSQL
Apache JMeter (for testing)
Installation
# Clone the repository
git clone https://github.com/your-username/orm-vs-sql-performance.git

# Navigate into the project
cd orm-vs-sql-performance

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
Database Setup
Create a database
Update application.properties:
spring.datasource.url=jdbc:mysql://localhost:5/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
Run dataset generation scripts (see /scripts folder)

📂 **Project Structure**
src/
 ├── controller/        # API endpoints
 ├── service/           # Business logic
 ├── repository/
 │    ├── orm/          # JPA/Hibernate implementation
 │    └── sql/          # JDBC implementation
 ├── model/             # Entity classes
 └── config/            # Configuration files

scripts/                # Dataset generation scripts
tests/                  # JMeter test plans
results/                # Performance outputs

📈 **Key Findings**
ORM and Raw SQL both deliver strong performance
Raw SQL performs slightly better in complex operations
Performance differences are generally minimal
System scales efficiently across large datasets and high concurrency
Database design and optimisation have greater impact than access method

📌 **Reproducibility**
All datasets, configurations, and test setups used in this study are included in this repository. This allows independent verification of results and ensures full reproducibility of experiments.

📚 **Academic Context**
This repository accompanies a postgraduate dissertation submitted as part of an MSc programme. It is intended for academic, educational, and research purposes.

📅 **Access**
Access Date: March 2026

👨‍💻 **Author**
Owen Onyechefu
MSc Computer Science

📜 **License**
This project is provided for academic use. You may reuse or adapt with proper attribution.
