const mongoose = require('mongoose');
const {Schema} = mongoose;

const cartSchema = new Schema( {
    items: [{int, String}],
    value: Number,
    description: String
});

mongoose.model('cart', cartSchema);