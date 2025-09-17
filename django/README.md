# **django_auth_api**

A simple REST API built with Django and Django Rest Framework for user authentication and JWT-based authorization. The API supports user registration, login, and token refresh, allowing secure access to protected endpoints.

## **Project Structure**

```
django_auth_api/          # Project root directory
│── users/                # User management Django app
│   ├── migrations/       # Django ORM database migrations
│   ├── __init__.py       
│   ├── admin.py          # (Optional, can be left empty)
│   ├── apps.py           
│   ├── models.py         # (Uses default User model)
│   ├── serializers.py    # Serializers for user and JWT-related operations
│   ├── views.py          # API views (register, login, protected)
│   ├── urls.py           # App-specific API routes
│   ├── tests.py          # Optional test file
│
│── api/                  # Main project files
│   ├── __init__.py
│   ├── settings.py       # Django project settings (.env configuration)
│   ├── urls.py           # Main URL routing
│   ├── wsgi.py           # WSGI server configuration
│   ├── asgi.py           # ASGI server configuration (for async APIs if required)
│
│── .env                  # Environment variables (SECRET_KEY, JWT expiry times, etc.)
│── .gitignore            # Ignores unnecessary files for git
│── manage.py             # Django management command
│── requirements.txt      # Required dependencies (`pip freeze > requirements.txt`)
```

## **Installation**

1. **Clone the repository**:
    ```bash
    git clone https://github.com/zahidayturan/multi-lang-auth-api-platform/tree/main/django
    cd django_auth_api
    ```

2. **Create a virtual environment** (optional but recommended):
    ```bash
    python -m venv venv
    source venv/bin/activate  # On Windows use venv\Scripts\activate
    ```

3. **Install the dependencies**:
    ```bash
    pip install -r requirements.txt
    ```

4. **Set up the `.env` file**:
    Create a `.env` file in the project root with the following content:
    ```env
    SECRET_KEY=<your-secret-key>
    DEBUG=True
    ALLOWED_HOSTS=127.0.0.1,localhost
    JWT_ACCESS_TOKEN_LIFETIME=15  # in minutes
    JWT_REFRESH_TOKEN_LIFETIME=7  # in days
    BASE_FRONT_URL=<your-front-url>
    ```

5. **Apply migrations**:
    ```bash
    python manage.py makemigrations
    python manage.py migrate
    ```

6. **Run the development server**:
    ```bash
    python manage.py runserver
    ```

    Your API will be available at `http://127.0.0.1:8000/`.

---

## **Endpoints**

### **1. User Registration**

- **URL**: `/api/users/register/`
- **Method**: `POST`
- **Description**: Allows new users to register by providing their `username`, `password`, `email`, `first_name`, and `last_name`.
- **Request Body**:
    ```json
    {
        "username": "testuser",
        "password": "testpassword",
        "email": "test@example.com",
        "first_name": "Test",
        "last_name": "User"
    }
    ```

- **Response**:
    - Status: `201 Created`
    - Body: User object (password will be omitted):
    ```json
    {
        "username": "testuser",
        "email": "test@example.com",
        "first_name": "Test",
        "last_name": "User"
    }
    ```

---

### **2. User Login (JWT Authentication)**

- **URL**: `/api/users/login/`
- **Method**: `POST`
- **Description**: Allows existing users to log in and receive an `access` and `refresh` token.
- **Request Body**:
    ```json
    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```

- **Response**:
    - Status: `200 OK`
    - Body: JWT Tokens
    ```json
    {
        "access": "access_token",
        "refresh": "refresh_token"
    }
    ```

---

### **3. Token Refresh**

- **URL**: `/api/users/refresh/`
- **Method**: `POST`
- **Description**: Refresh the JWT `access` token using the `refresh` token.
- **Request Body**:
    ```json
    {
        "refresh": "refresh_token"
    }
    ```

- **Response**:
    - Status: `200 OK`
    - Body: New JWT `access` token
    ```json
    {
        "access": "new_access_token"
    }
    ```

---

### **4. Protected View (Requires Authentication)**

- **URL**: `/api/users/protected/`
- **Method**: `GET`
- **Description**: A protected endpoint that requires a valid JWT `access` token to access.
- **Authorization**: Bearer token in the `Authorization` header.
    ```bash
    Authorization: Bearer <access_token>
    ```

- **Response**:
    - Status: `200 OK` (if token is valid)
    - Body: A welcome message containing the username of the logged-in user
    ```json
    {
        "message": "Hello, testuser! This data is protected and can only be accessed with a valid token."
    }
    ```

    - Status: `401 Unauthorized` (if token is invalid or missing)

---

### **5.1. Create an Admin User**

- **URL**: Terminal (command line)
- **Method**: `python manage.py createsuperuser`
- **Description**: Use Django's `createsuperuser` command to create an admin user.
- **Command**:
    ```bash
    python manage.py createsuperuser
    ```
- **Requested Information**:
    - **Username**: `admin`
    - **Email**: `admin@example.com`
    - **Password**: `password123`

This user will be created with **is_staff=True** and **is_superuser=True**.

---

### **5.2. User Login (JWT Authentication)**

- **URL**: `/api/users/login/`
- **Method**: `POST`
- **Description**: Allows existing users to log in and receive an **access** and **refresh** token.
- **Request Body**:
    ```json
    {
        "username": "admin",
        "password": "password123"
    }
    ```
- **Response**:
    - Status: `200 OK`
    - Body: JWT Tokens
    ```json
    {
        "access": "access_token",
        "refresh": "refresh_token"
    }
    ```
    **Note**: Copy the **`access` token**, as we will use it to access the **admin-only** endpoint.

---

### **5.3. Access the Admin Endpoint**

- **URL**: `/api/users/admin-only/`
- **Method**: `GET`
- **Description**: Admin-only endpoint.
- **Request Headers**:
    - **Authorization**: `Bearer <access-token>`
    - Replace `<access-token>` with the **access token** you received earlier.
- **Example Header**:
    ```bash
    Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...
    ```
- **Response** (Successful):
    - Status: `200 OK`
    - Body:
    ```json
    {
        "message": "Hello admin admin, this endpoint is exclusive to you!"
    }
    ```

---

### **5.4. Try with a Non-Admin User**

- **URL**: `/api/users/admin-only/`
- **Method**: `GET`
- **Description**: We will test the **403 Forbidden** error when trying to access the **admin-only** endpoint with a non-admin user.
- **User Information**:
    - **Username**: `normaluser`
    - **Password**: `12345678`
- **Request Body**:
    ```json
    {
        "username": "normaluser",
        "password": "12345678"
    }
    ```
    After logging in, you'll receive the **access token** for this user.

- **Response** (403 Forbidden):
    - Status: `403 Forbidden`
    - Body:
    ```json
    {
        "detail": "You do not have permission to perform this action."
    }
    ```
---


## **Settings**

The main settings for this project are configured in the `settings.py` file.

### **JWT Settings**

JWT settings are configurable via the `.env` file. The access token lifetime is set in minutes, and the refresh token lifetime is set in days.

```python
SIMPLE_JWT = {
    "ACCESS_TOKEN_LIFETIME": timedelta(minutes=int(os.getenv("JWT_ACCESS_TOKEN_LIFETIME", 15))),
    "REFRESH_TOKEN_LIFETIME": timedelta(days=int(os.getenv("JWT_REFRESH_TOKEN_LIFETIME", 7))),
}
```

### **Authentication and Permissions**

The `DEFAULT_AUTHENTICATION_CLASSES` setting ensures JWT authentication is used throughout the project.

```python
REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': (
        'rest_framework_simplejwt.authentication.JWTAuthentication',
    ),
}
```

### **Installed Apps**

Make sure to add `rest_framework` and `users` to the `INSTALLED_APPS` list in `settings.py`:

```python
INSTALLED_APPS = [
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'rest_framework',
    'users',  # User management app
]
```

---

## **Testing**

You can test the API using tools like **Postman** or **cURL**.

1. **Register a new user**:
    ```bash
    POST http://127.0.0.1:8000/api/users/register/
    Content-Type: application/json

    {
        "username": "testuser",
        "password": "testpassword",
        "email": "test@example.com",
        "first_name": "Test",
        "last_name": "User"
    }
    ```

2. **Login and obtain JWT tokens**:
    ```bash
    POST http://127.0.0.1:8000/api/users/login/
    Content-Type: application/json

    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```

3. **Token refresh**:
    ```bash
    POST http://127.0.0.1:8000/api/users/refresh/
    Content-Type: application/json

    {
        "refresh": "refresh_token"
    }
    ```

4. **Access protected endpoint**:
    ```bash
    GET http://127.0.0.1:8000/api/users/protected/
    Authorization: Bearer access_token
    ```

---

## **Conclusion**

This project provides a simple but effective way to handle user authentication in Django applications with JWT. It can be easily extended to include additional features like password reset, email verification, or user roles as needed.

For any questions or contributions, feel free to open an issue or pull request!