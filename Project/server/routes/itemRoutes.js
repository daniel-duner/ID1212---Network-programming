const mongoose = require('mongoose');
const Item = mongoose.model('items');
module.exports = (app) => {
    app.post('/api/item/add', async (req, res) => {
        const request = await req.body;
        console.log(request);
        const existingItem = await Item.findOne({name: request.name});
        if(!existingItem){
            new Item({
                name: request.name,
                imageURL: request.imageURL,
                price: request.price, 
                quantity: request.quantity,
                description: request.description
            }).save();
            res.send("Success");
        }   else {
                res.send("Item with that name already exist");
            }
    });

    app.get('/api/item/getAll',async (req, res) => {
        res.send(await Item.find({}));
    })
};