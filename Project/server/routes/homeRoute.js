const mongoose = require('mongoose');
module.exports = (app) => {
    app.get('/home', (req, res) => {
        res.send("Welcome You are now logged in");
    })
};