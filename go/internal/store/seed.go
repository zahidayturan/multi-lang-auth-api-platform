package store

import (
	"auth-api/internal/config"
	"auth-api/internal/models"
	"golang.org/x/crypto/bcrypt"
	"log"
)

func SeedAdmin() {
	cfg := config.LoadConfig()
	adminPassword := cfg.AdminPassword // from env
	var count int64
	DB.Model(&models.User{}).Where("role = ?", "admin").Count(&count)

	if count == 0 {
		hashedPassword, _ := bcrypt.GenerateFromPassword([]byte(adminPassword), bcrypt.DefaultCost)
		admin := models.User{
			Username: "admin",
			Password: string(hashedPassword),
			Role:     "admin",
		}
		if err := DB.Create(&admin).Error; err != nil {
			log.Println("failed to create admin user:", err)
		} else {
			log.Println("✅ Admin user created (username: admin)")
		}
	}
}
