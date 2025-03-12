const express = require('express');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const authRoutes = require('./routes/authRoutes');

dotenv.config(); // Ortam değişkenleri

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
