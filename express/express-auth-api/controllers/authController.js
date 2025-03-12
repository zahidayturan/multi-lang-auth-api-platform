import User from '../models/user.js';
import jwt from 'jsonwebtoken';
import dotenv from 'dotenv';

dotenv.config();

// User register API endpoint
const register = async (req, res) => {
    const { username, email, password } = req.body;

    // User already exists check
    const existingUser = await User.findByEmail(email);
    if (existingUser) {
        return res.status(400).json({ error: 'User already exists' });
    }

    // Password hashing
    const hashedPassword = await User.hashPassword(password);

    // New user object
    const newUser = new User(username, email, hashedPassword);
    await User.addUser(newUser);

    res.status(201).json({ message: 'User created successfully' });
};

// User login API endpoint
const login = async (req, res) => {
    const { email, password } = req.body;

    // Find user by email
    const user = await User.findByEmail(email);
    if (!user) {
        return res.status(400).json({ error: 'Invalid email or password' });
    }

    // Check password
    const isMatch = await User.comparePassword(password, user.password);
    if (!isMatch) {
        return res.status(400).json({ error: 'Invalid email or password' });
    }

    // Create JWT token
    const token = jwt.sign(
        { email: user.email, username: user.username },
        process.env.JWT_SECRET,
        { expiresIn: process.env.JWT_EXP }
    );

    res.status(200).json({ message: 'Login successful', token });
};

// Protected route API endpoint
const protectedRoute = (req, res) => {
    res.status(200).json({ message: 'You have access to this protected route' });
};

export { register, login, protectedRoute };
