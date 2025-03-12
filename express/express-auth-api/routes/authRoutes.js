const express = require('express');
const router = express.Router();
const { register, login, protectedRoute } = require('../controllers/authController');
const { authenticateJWT } = require('../middleware/authMiddleware');

// User register route
router.post('/register', register);

// User login route
router.post('/login', login);

// Protected route
router.get('/protected', authenticateJWT, protectedRoute);

module.exports = router;
