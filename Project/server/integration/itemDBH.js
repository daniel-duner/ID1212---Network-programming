const mongoose = require("mongoose");
const Item = mongoose.model("items");
module.exports = {
    addItem: async (item) => {
        const {name, imageURL, price, quantity, description} = item;
        const existingItem = await Item.findOne({name: name});
        if(!existingItem){
            new Item({
                name: name,
                imageURL: imageURL,
                price: price, 
                quantity: quantity,
                description: description
            }).save();
            return{
                status: 201,
                data: "success, item was created"
            }
        }   else {
                return {
                    status: 404,
                    data: "An item with that name already exists"
                }
            }  
    },
    getAllItems: async () => {
        const result = await Item.find({});
        return {
            status: 200,
            data: result
        }
    }

}

