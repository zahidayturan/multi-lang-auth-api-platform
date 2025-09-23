using AuthApi.Data;
using AuthApi.Models;
using AuthApi.Services;
using BCrypt.Net;
using Microsoft.AspNetCore.Mvc;

namespace AuthApi.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController : ControllerBase
{
    private readonly AppDbContext _db;
    private readonly IAuthService _authService;

    public AuthController(AppDbContext db, IAuthService authService)
    {
        _db = db;
        _authService = authService;
    }

    [HttpPost("register")]
    public IActionResult Register(AuthRequest request)
    {
        if (_db.Users.Any(u => u.Username == request.Username))
            return BadRequest("User already exists");

        var user = new User
        {
            Username = request.Username,
            PasswordHash = BCrypt.Net.BCrypt.HashPassword(request.Password),
            Role = "user"
        };

        _db.Users.Add(user);
        _db.SaveChanges();

        return Ok("User registered");
    }

    [HttpPost("login")]
    public IActionResult Login(AuthRequest request)
    {
        var user = _db.Users.FirstOrDefault(u => u.Username == request.Username);
        if (user == null || !BCrypt.Net.BCrypt.Verify(request.Password, user.PasswordHash))
            return Unauthorized("Invalid credentials");

        var token = _authService.GenerateToken(user);
        return Ok(new AuthResponse { Token = token });
    }
}
