package main

import (
	"auth-api/internal/config"
	"auth-api/internal/store"
	"github.com/gin-gonic/gin"
	"log"
)

func main() {
	// config load
	cfg := config.LoadConfig()

	// DB init
	store.InitDB(cfg.DatabaseURL)

	// router
	r := gin.Default()

	// basic health check endpoint
	r.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"status": "ok",
		})
	})

	addr := ":" + cfg.Port
	log.Println("Server running at http://localhost" + addr)
	if err := r.Run(addr); err != nil {
		log.Fatal(err)
	}
}
