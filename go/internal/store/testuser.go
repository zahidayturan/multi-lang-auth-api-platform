package store

import "auth-api/internal/models"

func DBUserForTest() *models.User {
	return &models.User{
		ID:       1,
		Username: "demo",
		Role:     "admin",
	}
}
