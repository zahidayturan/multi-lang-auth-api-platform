const bcrypt = require('bcryptjs');

class User {
    constructor(username, email, password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    static async findByEmail(email) {
        // Veritabanı sorgusu
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
        // Veritabanına kullanıcı ekle
        // await db.query('INSERT INTO users (username, email, password) VALUES (?, ?, ?)', [user.username, user.email, user.password]);
    }
}

module.exports = User;
