
# Java Servlets & JSP Application with PostgreSQL

This project is a web application developed using Java Servlets and JSP, backed by a PostgreSQL database. It includes functionality for managing orders, dishes, users, and reviews.

## Database Setup

Before running the application, set up the PostgreSQL database by executing the following SQL commands:

```sql
-- Create orders table
CREATE TABLE orders (
    orderId SERIAL PRIMARY KEY,
    userId INT NOT NULL,
    dishId INT NOT NULL,
    orderDateTime TIMESTAMP NOT NULL,
    status VARCHAR(255),
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (dishId) REFERENCES dishes(dishId)
);

-- Create dishes table
CREATE TABLE dishes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    complexity_of_execution INT NOT NULL, -- Time in minutes
    isAvailable BOOLEAN DEFAULT TRUE
);

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create reviews table
CREATE TABLE reviews (
    reviewId SERIAL PRIMARY KEY,
    dishId INT NOT NULL,
    comment TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    FOREIGN KEY (dishId) REFERENCES dishes(id)
);
```

## Configuration

In the `DataConnectionPool` class, replace the database connection settings with your own:

```java
config.setJdbcUrl("your_database_url"); // Example: jdbc:postgresql://localhost/Restaurant
config.setUsername("your_username");
config.setPassword("your_password");
```

## Running the Application

- Ensure you have Java and PostgreSQL installed on your system.
- Configure the database connection as described above.
- Compile and run the application on a Java-supported server, such as Apache Tomcat.

## Downloading and Launching Locally

1. Clone the repository to your local machine.
2. Ensure PostgreSQL is running and the database is set up as described.
3. Update the database connection settings in `DataConnectionPool`.
4. Deploy the application to a servlet container like Tomcat.

For contributors and users looking to run the application locally, ensure all dependencies are resolved, and follow the running instructions detailed above.

## Additional information
1. The DataConnectionPool class implements the Singleton pattern. The DataConnectionPool class initializes a HikariDataSource instance once and reuses it throughout the application.
2. The repositories like OrderRepository, DishRepository, and ReviewRepository follow the Data Access Object pattern. This pattern provides an abstract interface to some type of database or other persistence mechanism.
3. Java Servlets and JSP inherently follow the MVC pattern. The JSP files act as the View, the Servlets act as the Controller, and the Java classes used for handling data (like Order, Dish, User, etc.) represent the Model.