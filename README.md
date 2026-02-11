# NeighbourHood Backend (Continuously developing)

A Spring Boot backend application for an estate community platform that enables residents to connect, share posts, and help each other through quest requests.

## Features

- **User Authentication**
  - JWT-based authentication

- **Post Management**
  - Create, read, and like posts
  - Support for multiple post types (requests, announcements, etc.)
  - Supabase Storage for file uploading (currently only images)
  - Real-time updates via WebSocket

- **Real-time Notifications + Chatroom **
  - WebSocket
  - Live post updates
  - Functional Chatroom features
  - Like notifications

- **File Storage**
  - Integration with Supabase Storage
  - Image upload and management
  - Secure file handling

## Prerequisites
  - PostgreSQL database (or Supabase account)
  - Supabase project for storage


## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd neighbourHood-backend
```

### 2. Configure the application

a. Build .env
b. Update `src/main/resources/application.properties` with your database and Supabase credentials.

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Project Structure

```
neighbourHood-backend/
├── src/
│   ├── main/
│   │   ├── java/com/app/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── GlobalCorsConfig.java
│   │   │   │   └── WebSocketConfig.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   └── ApiResponse.java
│   │   │   ├── feature/
│   │   │   │   ├── auth/           # Authentication module
│   │   │   │   │   ├── config/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── dto/
│   │   │   │   │   ├── filter/
│   │   │   │   │   ├── model/
│   │   │   │   │   ├── repository/
│   │   │   │   │   ├── service/
│   │   │   │   │   └── util/
│   │   │   │   ├── notifications/  # Notification module
│   │   │   │   ├── photo/          # Photo management
│   │   │   │   ├── post/           # Post module
│   │   │   │   └── storage/        # File storage
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

