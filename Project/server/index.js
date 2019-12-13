const express = require('express');
const mongoose = require('mongoose')
const cookieSession = require('cookie-session');
const cors = require('cors');

const passport = require('passport');
const keys = require('./config/keys');
const logger = require('./services/logger');
require('./models/Item');
require('./models/User');
require('./services/passport');

mongoose.connect(keys.mongoURI);

const app = express();
app.use(cors());
app.use('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "http://localhost:5000");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    res.header('Access-Control-Allow-Credentials', true);
    next(); 
    });
app.options('*', cors());    
app.use(logger);
app.use(express.json());
app.use(
    cookieSession({
        maxAge: 24*60*60*1000,
        keys: [keys.cookieKey]
    })
);

app.use(passport.initialize());
app.use(passport.session());

require('./routes/authRoutes')(app);
require('./routes/homeRoute')(app);
require('./routes/itemRoutes')(app);

const PORT  = process.env.PORT || 5000;
app.listen(PORT);


