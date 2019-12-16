const mongoose = require('mongoose');
const {Schema} = mongoose;

const cartSchema = new Schema( {
    user: String,
    items: [{
        id: String,
        name: String,
        imageURL: String,
        price: Number,
        quantity: Number,
        description: String
    }]
});

mongoose.model('carts', cartSchema);