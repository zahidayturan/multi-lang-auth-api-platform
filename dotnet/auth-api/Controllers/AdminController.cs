using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace AuthApi.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AdminController : ControllerBase
{
    [Authorize(Roles = "admin")]
    [HttpGet("test")]
    public IActionResult Test()
    {
        return Ok("Hello Admin! This endpoint is restricted.");
    }
}
