import bcrypt from 'bcryptjs';

class User {
    constructor(username, email, password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    static async findByEmail(email) {
        // Database query
        // return db.query('SELECT * FROM users WHERE email = ?', [email]);
        return null;
    }

    static async hashPassword(password) {
        const salt = await bcrypt.genSalt(10);
        return bcrypt.hash(password, salt);
    }

    static async comparePassword(inputPassword, storedPassword) {
        return bcrypt.compare(inputPassword, storedPassword);
    }

    static async addUser(user) {
        // Add user to the database
        // await db.query('INSERT INTO users (username, email, password) VALUES (?, ?, ?)', [user.username, user.email, user.password]);
    }
}

export default User;
