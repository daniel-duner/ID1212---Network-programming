const mongoose = require('mongoose');
const {Schema} = mongoose;

const itemSchema = new Schema( {
    name: String,
    imageURL: String,
    price: Number,
    quantity: Number,
    description: String
});

mongoose.model('items', itemSchema);