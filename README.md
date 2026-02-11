# NeighbourHood Backend (Continuously developing)

A Spring Boot backend application for a neighbourhood community platform that enables residents to connect, share posts, and help each other through quest requests.

## Features

- **User Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (USER, ADMIN)
  - Secure password encryption with BCrypt

- **Post Management**
  - Create, read, and like posts
  - Support for multiple post types (quest requests, announcements, etc.)
  - Image upload support via Supabase Storage
  - Real-time updates via WebSocket

- **Real-time Notifications + Chatroom **
  - WebSocket integration with STOMP protocol
  - Live post updates
  - Functional Chatroom features
  - Like notifications

- **File Storage**
  - Integration with Supabase Storage
  - Image upload and management
  - Secure file handling

## Tech Stack

- **Framework**: Spring Boot 3.2.2
- **Language**: Java 17
- **Database**: PostgreSQL (via Supabase)
- **Security**: Spring Security + JWT
- **WebSocket**: STOMP over SockJS
- **Build Tool**: Maven
- **Storage**: Supabase Storage

## Prerequisites

- Java 17
- Maven 3.6+
- PostgreSQL database (or Supabase account)
- Supabase project for storage

## Configuration

Create an `application.properties` file in `src/main/resources/`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://your-supabase-url:5432/postgres
spring.datasource.username=your-username
spring.datasource.password=your-password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.enable_lazy_load_no_trans=true

# Supabase Storage Configuration
supabase.url=https://your-project.supabase.co
supabase.key=your-supabase-anon-key
supabase.bucket=your-bucket-name

```

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd neighbourHood-backend
```

### 2. Configure the application

Update `src/main/resources/application.properties` with your database and Supabase credentials.

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Authentication Endpoints

#### Register
```http
POST /api/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

#### Login
```http
POST /api/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "code": "200",
  "success": true,
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

### Post Endpoints

#### Create Post
```http
POST /api/post/create-post
Authorization: Bearer <jwt-token>
Content-Type: multipart/form-data

title: "Need help with gardening"
content: "Looking for someone to help with garden maintenance"
type: 0
redeemPoints: 50
request_type: 3
payment_method: 0
is_important: true
startTime: 2024-03-20T10:00:00
endTime: 2024-03-20T12:00:00
files: [image1.jpg, image2.jpg]
```

#### Get All Posts
```http
GET /api/post/
Authorization: Bearer <jwt-token>
```

#### Get Post by ID
```http
GET /api/post/{id}
Authorization: Bearer <jwt-token>
```

#### Like/Unlike Post
```http
POST /api/post/like-post
Authorization: Bearer <jwt-token>
Content-Type: application/json

58
```

#### Accept Quest
```http
POST /api/post/accept-post
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "postId": 58,
  "userId": "uuid-here"
}
```

## WebSocket Integration

### Connection Endpoint
```
ws://localhost:8080/ws
```

### Subscribe to Post Updates
```
SUBSCRIBE /topic/post/{postId}
```

When a post is liked, all subscribers receive real-time updates with the updated post data.

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

## Security

- All endpoints except `/api/login`, `/api/register`, and `/ws/**`(will setup config later) require JWT authentication
- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours (configurable)
- CORS is configured to allow requests from the frontend

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify your database credentials in `application.properties`
   - Ensure PostgreSQL is running
   - Check if the database exists

2. **JWT Token Invalid**
   - Ensure the JWT secret is properly configured
   - Check if the token has expired
   - Verify the token is included in the Authorization header

3. **File Upload Failed**
   - Check Supabase credentials
   - Verify bucket permissions
   - Ensure file size is within limits (10MB)

4. **WebSocket Connection Failed**
   - Verify CORS configuration
   - Check if `/ws/**` is permitted in SecurityConfig
   - Ensure frontend is connecting to the correct endpoint

## License

This project is licensed under the MIT License.

## Authors

- Ken Wong
