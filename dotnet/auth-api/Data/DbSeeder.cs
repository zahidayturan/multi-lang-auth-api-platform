using AuthApi.Models;
using BCrypt.Net;

namespace AuthApi.Data;

public static class DbSeeder
{
    public static void SeedAdmin(AppDbContext db, IConfiguration config)
    {
        if (!db.Users.Any(u => u.Role == "admin"))
        {
            var username = config["Admin:Username"];
            var password = config["Admin:Password"];

            var admin = new User
            {
                Username = username ?? "admin",
                PasswordHash = BCrypt.Net.BCrypt.HashPassword(password),
                Role = "admin"
            };

            db.Users.Add(admin);
            db.SaveChanges();
        }
    }
}
