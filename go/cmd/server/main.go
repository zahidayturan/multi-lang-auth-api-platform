package main

import (
	"auth-api/internal/config"
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

	// auth service create
	authService := &services.AuthService{
		JWTSecret:   cfg.JWTSecret,
		JWTExpireIn: time.Hour, // 1 hour
	}

	// router
	r := gin.Default()

	// basit health endpoint
	r.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"status": "ok",
		})
	})

	// test token endpoint
	r.GET("/token-test", func(c *gin.Context) {
		dummyUser := store.DBUserForTest() // bunu az sonra göstereceğim
		token, _ := authService.GenerateJWT(dummyUser)
		c.JSON(200, gin.H{"token": token})
	})

	addr := ":" + cfg.Port
	log.Println("Server running at http://localhost" + addr)
	if err := r.Run(addr); err != nil {
		log.Fatal(err)
	}
}
