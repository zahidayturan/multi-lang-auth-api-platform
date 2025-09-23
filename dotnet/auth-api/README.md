# Multi-Language Auth API Platform / .NET

A simple authentication API built with .NET, using JWT for secure authentication. Local development runs on **port 5235**.

## Features

* User registration & login
* JWT-based authentication
* Role-based access (user / admin)
* PostgreSQL database support via Entity Framework Core

---

## Endpoints

- /api/auth/register

- /api/auth/login

- /api/user/profile

- /api/admin/test


## Setup

1. Clone the repository:

```bash
git clone https://github.com/zahidayturan/multi-lang-auth-api-platform
```

2. Navigate to the project folder:

```bash
cd dotnet/auth-api
```

3. Restore NuGet packages:

```bash
dotnet restore
```

4. Apply database migrations (PostgreSQL must be running):

```bash
dotnet ef database update
```

5. Run the API:

```bash
dotnet run
```

6. Test API using Postman or another REST client at:

```
http://localhost:5235
```

---

## Environment & Configuration

* Database connection string and other secrets are stored in `appsettings.Development.json` which is **.gitignored**.
* Make sure to configure your local PostgreSQL credentials in this file.

---

If you want, I can also **add a small “Example JWT usage in Postman” section** to make it very clear how to test the endpoints. This is handy for new developers or testers. Do you want me to add that?
