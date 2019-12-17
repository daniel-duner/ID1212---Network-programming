const mongoose = require('mongoose');
const User = mongoose.model('users');

module.exports = {
    findUser: async (id) => {
        return await User.findOne({googleId: id});
    },
    addUser: async (user) => {
        return await new User({googleId: user}).save();
    },

}
