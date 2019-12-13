const passport = require('passport');
const mongoose = require('mongoose');
const User = mongoose.model('users');
module.exports = (app) => {
    app.get(
        '/auth/google', 
        passport.authenticate('google', {
        scope: ['profile', 'email']
    }))

    app.get('/auth/google/callback', passport.authenticate('google'),(req,res)=>{
        res.redirect('/shop')
    });

    app.get('/api/logout', (req, res) => {
        req.logout();
        res.send(req.user);
    })

    app.get('/api/current_user', (req, res) => {
        console.log(req.User);
        res.send(req.user);
    })
};