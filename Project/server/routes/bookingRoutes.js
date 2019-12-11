const mongoose = require('mongoose');
const User = mongoose.model('users');
module.exports = (app) => {
    app.post('/book', (req, res) => {
        req.logout();
    })
};