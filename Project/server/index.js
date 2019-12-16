const express = require('express');
const mongoose = require('mongoose')
const cookieSession = require('cookie-session');

const passport = require('passport');
const keys = require('./config/keys');
const logger = require('./services/logger');
require('./models/Item');
require('./models/User');
require('./models/Cart');
require('./services/passport');

mongoose.connect(keys.mongoURI);
const app = express();
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
require('./routes/itemRoutes')(app);
require('./routes/cartRoutes')(app);

const PORT  = process.env.PORT || 5000;
app.listen(PORT);


