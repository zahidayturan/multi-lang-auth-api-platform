using AuthApi.Models;

namespace AuthApi.Services;

public interface IAuthService
{
    string GenerateToken(User user);
}
