# Configuration Management System

A modern configuration management system built with Java Spring Boot microservices backend and TypeScript/Angular frontend. The system allows centralized management and distribution of application configurations with access control.

## Architecture Overview

### Backend Architecture
- **Microservices**:
  - User Service : Handles authentication and user registration
  - Config Service : Manages configuration entries
- **Database**: MySQL 8.0
- **Security**: JWT-based authentication
- **API**: RESTful endpoints with OpenAPI/Swagger documentation

### Frontend Architecture
- **Framework**: Angular 15 with TypeScript
- **UI Components**: Material UI/Custom components

## Backend Setup Instructions

### Using Docker Compose

1. Clone the repository:
```bash
git clone https://github.com/billychl1/configuration-management-system.git
cd configuration-management-system
```

2. Start the services using Docker Compose:
```bash
cd backend
docker-compose up --build
```

This will start:
- MySQL database (port 3307)
- User Service (port 8081)
- Config Service (port 8082)

## Frontend Setup Instructions

1. Install dependencies:
```bash
cd frontend
npm install
```

2. Start development server:
```bash
npm start
```

The frontend application will be available at `http://localhost:4200`.

## API Testing Guide

### Authentication Endpoints

```bash
# Register New User
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com"
  }'

# Login
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}'
```

### Configuration Endpoints

```bash
# Get All Configurations
curl -X GET http://localhost:8082/api/configs \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Create Configuration
curl -X POST http://localhost:8082/api/configs \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "key": "test.key2",
    "value": "test value2",
    "description": "Test configuratio2"
  }'

# Update Configuration
curl -X PUT http://localhost:8082/api/configs/{id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "key": "test.key",
    "value": "updated value",
    "description": "Updated configuration"
  }'

# Delete Configuration
curl -X DELETE http://localhost:8082/api/configs/{id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### API Documentation

- Swagger UI is available at:
  - User Service: http://localhost:8081/swagger-ui.html
  - Config Service: http://localhost:8082/swagger-ui.html
- OpenAPI specs:
  - User Service: http://localhost:8081/v3/api-docs
  - Config Service: http://localhost:8082/v3/api-docs