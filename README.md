# AuthService

Application should start running https://auth-service-oprs.onrender.com/api by default

## Exploring REST APIs

### Auth

| Method | Url |
| ------ | --- |
| POST   | /auth/register |
```bash
{
    "username": "admin",
    "password": "12345",
    "email": "admin@gmail.com",
    "phoneNumber": "***"
}
```

| Method | Url |
| ------ | --- |
| POST   | /auth/login |

```bash
{
    "username": "admin",
    "password": "12345"
}
```
