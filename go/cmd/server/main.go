package main

import (
	"auth-api/internal/config"
	"auth-api/internal/handlers"
	"auth-api/internal/middleware"
	"auth-api/internal/services"
	"auth-api/internal/store"
	"github.com/gin-gonic/gin"
	"log"
	"time"
)

func main() {
	// config load
	cfg := config.LoadConfig()

	// DB init
	store.InitDB(cfg.DatabaseURL)
	store.SeedAdmin()

	// auth service create
	authService := &services.AuthService{
		JWTSecret:   cfg.JWTSecret,
		JWTExpireIn: time.Hour, // 1 hour
	}

	// handlers
	authHandler := &handlers.AuthHandler{AuthService: authService}
	userHandler := &handlers.UserHandler{}

	// router
	r := gin.Default()

	// public endpoints
	r.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{"status": "ok"})
	})
	r.POST("/register", authHandler.Register)
	r.POST("/login", authHandler.Login)

	// protected endpoints
	authGroup := r.Group("/")
	authGroup.Use(middleware.AuthMiddleware(authService))
	{
		authGroup.GET("/profile", userHandler.Profile)

		adminGroup := authGroup.Group("/admin")
		adminGroup.Use(middleware.RequireRole("admin"))
		{
			adminGroup.GET("/test", userHandler.AdminTest)
		}
	}

	addr := ":" + cfg.Port
	log.Println("Server running at http://localhost" + addr)
	if err := r.Run(addr); err != nil {
		log.Fatal(err)
	}
}