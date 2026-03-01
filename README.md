# MyRummy / RummyTriangle

Online rummy application – partially developed Java/Spring Boot app with core domain logic, game mechanics, and auth. Commission model: earn from winner's fee per game.

## Quick Start

### Prerequisites
- Java 8+ (or 11+ recommended)
- Maven
- PostgreSQL (or use Docker)

### 1. Database Setup

```bash
# Create database
createdb myrummydb

# Or with Docker
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:14
createdb -h localhost -U postgres myrummydb
```

### 2. Configure

Copy `RummyTriangle/src/main/resources/application.properties.example` values into `application.properties`, or set env vars:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/myrummydb
export SPRING_DATASOURCE_USERNAME=myrole
export SPRING_DATASOURCE_PASSWORD=your_password
```

### 3. Run

```bash
cd RummyTriangle
mvn spring-boot:run
```

App runs at `http://localhost:8085`. Login via form (users must exist in DB with BCrypt-hashed passwords).

### First-Time User Setup

On first run (empty DB), the app automatically creates an admin user:
- **Username:** `admin`
- **Password:** `admin123`  
Change this password after first login. Set `app.seed-admin=false` to disable auto-seed.

## Project Structure

```
RummyTriangle/
├── src/main/java/com/RummyTriangle/
│   ├── domain/          # Cards, Deck, CardGroup, User, etc.
│   └── service/         # GameService, LaunchApp, Security, REST
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## Run with Docker

```bash
cd RummyTriangle && mvn package -DskipTests
cd ..
DB_PASSWORD=yourpassword docker-compose up -d
```

App at http://localhost:8085. Login, then go to **Play Rummy** or `/game.html`.

## Migration & Roadmap

See **[MIGRATION_PLAN.md](MIGRATION_PLAN.md)** for:
- Language recommendation (Java modernization vs TypeScript rewrite)
- Phase 1 fixes (implemented)
- Phase 2–4 enhancements (security, game logic, real-time, deployment)
- Guardrails and deployment path

## License

Proprietary.
