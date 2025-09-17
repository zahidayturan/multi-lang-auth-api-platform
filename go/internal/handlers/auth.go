package handlers

import (
	"auth-api/internal/models"
	"auth-api/internal/services"
	"auth-api/internal/store"
	"auth-api/pkg/httpdto"
	"net/http"

	"github.com/gin-gonic/gin"
)

type AuthHandler struct {
	AuthService *services.AuthService
}

// Register endpoint
func (h *AuthHandler) Register(c *gin.Context) {
	var req httpdto.RegisterRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid_request", "message": err.Error()})
		return
	}

	// hash password
	hash, err := h.AuthService.HashPassword(req.Password)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "hash_failed"})
		return
	}

	// user create
	user := models.User{
		Username: req.Username,
		Password: hash,
		Role:     "user", // default
	}
	if err := store.DB.Create(&user).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "user_exists_or_invalid", "message": err.Error()})
		return
	}

	c.JSON(http.StatusCreated, gin.H{
		"id":       user.ID,
		"username": user.Username,
		"role":     user.Role,
	})
}

// Login endpoint
func (h *AuthHandler) Login(c *gin.Context) {
	var req httpdto.LoginRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid_request", "message": err.Error()})
		return
	}

	var user models.User
	if err := store.DB.Where("username = ?", req.Username).First(&user).Error; err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "invalid_credentials"})
		return
	}

	if !h.AuthService.CheckPasswordHash(req.Password, user.Password) {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "invalid_credentials"})
		return
	}

	token, err := h.AuthService.GenerateJWT(&user)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "token_generation_failed"})
		return
	}

	c.JSON(http.StatusOK, httpdto.AuthResponse{Token: token})
}
