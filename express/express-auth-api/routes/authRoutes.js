import express from 'express';
import { register, login, protectedRoute } from '../controllers/authController.js';
import { authenticateJWT } from '../middleware/authMiddleware.js';

const router = express.Router();

// User register route
router.post('/register', register);

// User login route
router.post('/login', login);

// Protected route
router.get('/protected', authenticateJWT, protectedRoute);

export default router;
