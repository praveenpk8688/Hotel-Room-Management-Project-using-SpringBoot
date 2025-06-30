🏨 Hotel Room Management System

A robust, secure, and modular Hotel Room Management System built with Java Spring Boot, offering a powerful backend infrastructure for managing room reservations, guest information, and user access control. This backend can be seamlessly connected with frontends built in React, Angular, Vue, or Thymeleaf.

✨ Features Overview

✅ Room Management (CRUD)

Add: Register new rooms with room number, type (Standard, Deluxe, Suite), capacity, price, and availability.

Update: Edit room details like type, capacity, or pricing.

Delete: Remove rooms from inventory.

View: Fetch all rooms or filter by type and availability.

✅ Guest & Booking Management

Guest Profiles: Create/manage guest info (name, contact, address).

Book Rooms: Book rooms for specific dates with availability checks.

Booking History: Track all past and upcoming guest bookings.

Check-in/Check-out: Status updates that auto-manage room availability.

✅ Availability Tracking

Real-time room availability based on bookings and check-ins.

Filter available rooms by type and date range.

✅ Role-Based Access Control (RBAC)

Admin: Full privileges to manage all system entities.

User: Book rooms, manage own bookings and profile.

✅ RESTful API

Clean, RESTful endpoints using standard HTTP methods.

Easily integrable with frontend/mobile apps.

✅ Project Architecture

Clean modular structure: controller, service, repository, model, dto, config, and exception layers.

📁 Project Structure
src/
├── main/
│   ├── java/
│   │   └── com/example/hotelmanagement/
│   │       ├── config/         # Security, Swagger, etc.
│   │       ├── controller/     # REST Controllers
│   │       ├── model/          # JPA Entities
│   │       ├── repository/     # Data access interfaces
│   │       ├── service/        # Business logic
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── exception/      # Custom exceptions
│   │       └── HotelManagementApplication.java
│   └── resources/
│       ├── application.properties
│       ├── static/         # Optional (CSS/JS)
│       └── templates/      # Optional (Thymeleaf)
test/                      # JUnit & Mockito tests
🚀 Getting Started

Prerequisites

Java 17

Maven 3.x

MySQL Server

IDE (e.g., IntelliJ or STS)

Setup

Clone the Repo
git clone https://github.com/your-username/hotel-management-system.git
cd hotel-management-system
Database Setup

Create a MySQL DB (e.g., hotel_db).

Update src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Build & Run
mvn clean install
mvn spring-boot:run
RESTful API Examples

Room APIs

GET /api/rooms – Fetch all rooms

GET /api/rooms/{id} – Fetch room by ID

POST /api/rooms – Add new room

PUT /api/rooms/{id} – Update room

DELETE /api/rooms/{id} – Remove room

Guest APIs

POST /api/guests/register – Register guest

GET /api/guests/{id} – Get guest info

Booking APIs

POST /api/bookings – Book room

GET /api/bookings/{id} – Get booking details

GET /api/bookings/room/{roomId} – All bookings for room

Auth APIs

POST /api/auth/login – Authenticate user (JWT/session)

🔐 Security (Spring Security)

Authentication: via JWT or session

Authorization: RBAC (@PreAuthorize)

Password: Hashed using BCrypt

📂 Sample SQL Schema (via Hibernate)
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(20) DEFAULT 'ROLE_USER'
);

CREATE TABLE rooms (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) UNIQUE,
    room_type VARCHAR(50),
    capacity INT,
    price_per_night DECIMAL(10, 2),
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    room_id BIGINT,
    check_in_date DATE,
    check_out_date DATE,
    total_price DECIMAL(10, 2),
    booking_status VARCHAR(20) DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

CREATE TABLE services (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    service_name VARCHAR(100) UNIQUE,
    price DECIMAL(10, 2),
    description TEXT
);

CREATE TABLE booking_services (
    booking_id BIGINT,
    service_id BIGINT,
    PRIMARY KEY (booking_id, service_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (service_id) REFERENCES services(id)
);
 Contributing

Fork the repository

Create a feature branch

Commit your changes

Push and open a PR

📄 License

This project is licensed under the MIT License.
