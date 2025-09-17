# Go REST Auth API

A simple **Go REST API** with **JWT authentication** and **role-based access** (admin/user).
PostgreSQL is used as the database, and environment variables are used for configuration.

---

## Features

* **User registration** (`/register`)
* **User login** (`/login`) with JWT token
* **Role-based access control** (`user` / `admin`)
* **Protected endpoints**:
  * `/profile` – user can view own profile
  * `/admin/test` – accessible only by admin
* **Admin user seeding** at application startup (password from `.env`)

---

## Requirements

* Go 1.20+
* PostgreSQL
* (Optional) pgAdmin for DB management

---

## Setup

1. **Clone the repository**

```bash
git clone https://github.com/zahidayturan/multi-lang-auth-api-platform/tree/main/go
cd go
```

2. **Create `.env` file**

```env
DATABASE_URL=postgres://<username>:<password>@<host?localhost>:<port?5432>/<db_name>?sslmode=disable
JWT_SECRET=supersecretkey123
PORT=8080
ADMIN_PASSWORD=admin123
```

3. **Install dependencies**

```bash
go mod tidy
```

4. **Run PostgreSQL** (example using Docker)

```bash
docker run --name authdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=auth_api -p 5432:5432 -d postgres
```

5. **Run the server**

```bash
go run ./cmd/server
```

* Server will start on `http://localhost:8080`
* Admin user is automatically created with username `admin` and password from `.env`.

---

## Endpoints

### Public

| Method | Endpoint  | Description         |
| ------ | --------- | ------------------- |
| POST   | /register | Register a new user |
| POST   | /login    | Login and get JWT   |
| GET    | /health   | Health check        |

### Protected (Auth required)

| Method | Endpoint     | Role       | Description         |
| ------ | ------------ | ---------- | ------------------- |
| GET    | /profile     | user/admin | View own profile    |
| GET    | /admin/test  | admin      | Admin test endpoint |

> JWT token should be sent in header:
> `Authorization: Bearer <token>`

---

## Usage Example

**Register**

```bash
curl -X POST http://localhost:8080/register \
-H "Content-Type: application/json" \
-d '{"username":"user1","password":"mypassword"}'
```

**Login**

```bash
curl -X POST http://localhost:8080/login \
-H "Content-Type: application/json" \
-d '{"username":"user1","password":"mypassword"}'
```

**Access protected endpoint**

```bash
curl http://localhost:8080/profile \
-H "Authorization: Bearer <TOKEN>"
```
**Check other endpoints**

---

## License

MIT License