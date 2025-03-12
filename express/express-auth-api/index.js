import express from 'express';
import bodyParser from 'body-parser';
import dotenv from 'dotenv';
import authRoutes from './routes/authRoutes.js';

dotenv.config(); // Load environment variables

const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(bodyParser.json());

// Auth API routes
app.use('/api/auth', authRoutes);

// Main endpoint
app.get('/', (req, res) => {
    res.send('Hello from the Auth API!');
});

app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});
