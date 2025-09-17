package handlers

import (
	"auth-api/internal/models"
	"auth-api/internal/store"
	"net/http"

	"github.com/gin-gonic/gin"
)

type UserHandler struct{}

func (h *UserHandler) Profile(c *gin.Context) {
	username := c.GetString("username")

	var user models.User
	if err := store.DB.Where("username = ?", username).First(&user).Error; err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "user_not_found"})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"id":       user.ID,
		"username": user.Username,
		"role":     user.Role,
	})
}

// only admin can access
func (h *UserHandler) AdminTest(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{"message": "Hello Admin! Access granted."})
}
