# Architecture Documentation

This document describes the system architecture, design patterns, and technical decisions for the Autobank application.

## System Overview

Autobank is a modern web application built using a layered architecture pattern with clear separation of concerns. The system follows Spring Boot conventions and implements RESTful API design principles.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (Not in scope)                  │
└─────────────────────────┬───────────────────────────────────┘
                          │ HTTP/REST API
┌─────────────────────────▼───────────────────────────────────┐
│                   Spring Boot Application                   │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   Security  │  │    Web      │  │   Error     │         │
│  │   Filter    │  │   Config    │  │  Handling   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│                    Controller Layer                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │    Auth     │  │   Receipt   │  │  Committee  │         │
│  │ Controller  │  │ Controller  │  │ Controller  │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
│  ┌─────────────┐  ┌─────────────┐                          │
│  │   Admin     │  │  Economic   │                          │
│  │ Controller  │  │ Controller  │                          │
│  └─────────────┘  └─────────────┘                          │
├─────────────────────────────────────────────────────────────┤
│                     Service Layer                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   Receipt   │  │    Auth     │  │  Committee  │         │
│  │   Service   │  │   Service   │  │   Service   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │    Blob     │  │ Attachment  │  │   Review    │         │
│  │   Service   │  │   Service   │  │   Service   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│                   Repository Layer                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   Receipt   │  │    User     │  │  Committee  │         │
│  │ Repository  │  │ Repository  │  │ Repository  │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│                     Data Layer                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   JPA/      │  │   Entity    │  │    DTO      │         │
│  │ Hibernate   │  │   Models    │  │   Classes   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────┐
│                External Dependencies                        │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   Azure     │  │    Auth0    │  │   Azure     │         │
│  │ SQL Server  │  │    OAuth2   │  │    Blob     │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
```

## Architectural Patterns

### 1. Layered Architecture

The application follows a traditional layered architecture with clear separation of concerns:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and orchestration
- **Repository Layer**: Manages data access and persistence
- **Data Layer**: Entity models and DTOs

### 2. Dependency Injection

Uses Spring's dependency injection container for:
- Loose coupling between components
- Easy testing and mocking
- Configuration management
- Bean lifecycle management

### 3. Repository Pattern

Implements Spring Data JPA repositories for:
- Abstraction over data access
- Automatic query generation
- Custom query methods
- Transaction management

### 4. DTO Pattern

Uses Data Transfer Objects for:
- API request/response models
- Data transformation between layers
- Validation and serialization
- Decoupling internal models from API contracts

## Technology Stack

### Core Framework
- **Spring Boot 3.x**: Main application framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data access layer
- **Spring Web**: REST API development

### Database & Persistence
- **Microsoft SQL Server**: Primary database (Azure)
- **Hibernate**: ORM implementation
- **HikariCP**: Connection pooling (default in Spring Boot)

### Authentication & Security
- **OAuth2**: Authentication protocol
- **JWT**: Token-based authentication
- **Auth0**: Identity provider
- **Spring Security**: Security framework

### File Storage
- **Azure Blob Storage**: File attachment storage
- **Azure Storage SDK**: Integration library

### Build & Deployment
- **Gradle**: Build automation
- **Docker**: Containerization
- **Azure**: Cloud platform

## Component Details

### Controller Layer

#### Responsibilities
- HTTP request/response handling
- Input validation
- Authentication/authorization checks
- Error handling
- Response formatting

#### Key Controllers
- `AuthenticationController`: User authentication
- `ReceiptController`: Receipt management for users
- `AdminReceiptController`: Admin receipt operations
- `CommitteeController`: Committee management
- `EconomicrequestController`: Economic request handling

#### Design Patterns
- RESTful API design
- HTTP status code conventions
- Consistent error responses
- Request/response DTOs

### Service Layer

#### Responsibilities
- Business logic implementation
- Transaction management
- Data validation
- External service integration
- Complex operations orchestration

#### Key Services
- `ReceiptService`: Core receipt business logic
- `ReceiptAdminService`: Admin-specific operations
- `AuthenticationService`: User authentication logic
- `BlobService`: File storage operations
- `CommitteeService`: Committee management

#### Design Patterns
- Service facade pattern
- Transaction boundaries
- Business rule validation
- External service abstraction

### Repository Layer

#### Responsibilities
- Data access abstraction
- Query execution
- Entity mapping
- Database transaction handling

#### Key Repositories
- `ReceiptRepository`: Receipt data access
- `OnlineUserRepository`: User data access
- `CommitteeRepository`: Committee data access
- `AttachmentRepository`: File metadata access

#### Features
- Spring Data JPA auto-implementation
- Custom query methods
- Specification pattern for complex queries
- Pagination and sorting support

### Data Layer

#### Entity Models
- JPA entity annotations
- Relationship mappings
- Validation constraints
- Audit fields

#### DTOs
- Request/response models
- Data transformation
- Validation annotations
- Serialization control

## Security Architecture

### Authentication Flow

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │    │  Autobank   │    │    Auth0    │
│             │    │   Server    │    │             │
└──────┬──────┘    └──────┬──────┘    └──────┬──────┘
       │                  │                  │
       │ 1. Login Request │                  │
       ├─────────────────►│                  │
       │                  │ 2. Redirect to   │
       │                  │    Auth0         │
       │                  ├─────────────────►│
       │                  │                  │
       │ 3. Auth0 Login   │                  │
       ├─────────────────────────────────────►│
       │                  │                  │
       │ 4. JWT Token     │                  │
       ◄─────────────────────────────────────┤
       │                  │                  │
       │ 5. API Request   │                  │
       │    with JWT      │                  │
       ├─────────────────►│                  │
       │                  │ 6. Validate JWT  │
       │                  ├─────────────────►│
       │                  │                  │
       │                  │ 7. Token Valid   │
       │                  ◄─────────────────┤
       │                  │                  │
       │ 8. API Response  │                  │
       ◄─────────────────┤                  │
```

### Authorization Model

#### Role-Based Access Control
- **Regular Users**: Can create and view their own receipts
- **Admin Users**: Can view all receipts and create reviews
- **Committee-Based**: Admin status determined by committee membership

#### Security Features
- JWT token validation
- Audience validation
- Issuer validation
- Environment-based security (dev/prod)
- CORS configuration

## Data Flow

### Receipt Creation Flow

```
1. User submits receipt via API
2. Controller validates request
3. Service processes business logic
4. Attachments uploaded to Azure Blob
5. Receipt saved to database
6. Response returned to user
```

### Receipt Review Flow

```
1. Admin requests receipt list
2. Service checks admin privileges
3. Repository fetches all receipts
4. Admin selects receipt to review
5. Admin submits review decision
6. Service validates and saves review
7. Receipt status updated
```

## Configuration Management

### Environment-Specific Configuration

#### Development
- Security disabled for easier testing
- Local database connections
- Debug logging enabled
- CORS permissive settings

#### Production
- Full security enabled
- Azure-hosted services
- Optimized logging
- Strict CORS policies

### Configuration Sources
- `application.properties`: Base configuration
- `application-local.properties`: Local overrides
- Environment variables: Sensitive data
- Azure configuration: Cloud-specific settings

## Performance Considerations

### Database Optimization
- Proper indexing strategy
- Connection pooling
- Query optimization
- Pagination for large datasets

### Caching Strategy
- JPA second-level cache (if needed)
- Application-level caching
- HTTP caching headers
- Static resource caching

### File Storage
- Azure Blob Storage for scalability
- Asynchronous file uploads
- File size limitations
- Content type validation

## Error Handling

### Exception Hierarchy
- Global exception handler
- Custom business exceptions
- Validation error handling
- HTTP status code mapping

### Error Response Format
```json
{
  "error": "Error Type",
  "message": "Human-readable message",
  "timestamp": "2023-12-01T10:30:00Z",
  "path": "/api/endpoint"
}
```

## Monitoring & Observability

### Logging Strategy
- Structured logging with SLF4J
- Different log levels per environment
- Request/response logging
- Error tracking and alerting

### Health Checks
- Spring Boot Actuator endpoints
- Database connectivity checks
- External service health
- Application metrics

## Scalability Considerations

### Horizontal Scaling
- Stateless application design
- Database connection pooling
- Load balancer compatibility
- Session management

### Vertical Scaling
- JVM tuning parameters
- Memory optimization
- CPU utilization
- I/O performance

## Security Best Practices

### Data Protection
- Sensitive data encryption
- SQL injection prevention
- XSS protection
- CSRF protection

### Authentication Security
- JWT token expiration
- Secure token storage
- Rate limiting (future)
- Audit logging

## Future Enhancements

### Planned Improvements
1. **Caching Layer**: Redis for performance
2. **Message Queue**: Asynchronous processing
3. **API Versioning**: Backward compatibility
4. **Rate Limiting**: API protection
5. **Audit Trail**: Complete action logging
6. **Soft Deletes**: Data retention
7. **Full-Text Search**: Advanced search capabilities
8. **Notification System**: Email/SMS alerts

### Technical Debt
1. Complete economic request implementation
2. Improve error handling consistency
3. Add comprehensive unit tests
4. Implement API documentation (OpenAPI/Swagger)
5. Add performance monitoring
6. Implement proper logging strategy
