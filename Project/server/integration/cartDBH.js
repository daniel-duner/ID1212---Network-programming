const find = require("../services/find");
const mongoose = require("mongoose");
const Item = mongoose.model("items");
const Cart = mongoose.model("carts");
module.exports = {
    addItem: async (id,user) => {
        const item = await Item.findById(id);
        const existingCart = await Cart.findOne({user: user});
        if (!existingCart) {
            new Cart({
              user: user,
              items: [
                {
                  id: item._id,
                  name: item.name,
                  imageURL: item.imageURL,
                  quantity: 1,
                  price: item.price
                }
              ]
            }).save();
          } else {
            const index = await find.itemId(item._id, existingCart.items);
            if (index == null) {
              existingCart.items.push({
                id: item._id,
                name: item.name,
                imageURL: item.imageURL,
                quantity: 1,
                price: item.price
              });
              existingCart.save();
            } else {
              Cart.find({});
              existingCart.items[index].quantity++;
              existingCart.save();
              return({
                  status: 200,
                  data: existingCart.items
            })
            }
        }
        return({
            status: 201,
            data: existingCart.items
        })
    },
    deleteItem: async (id, user) =>{
    const existingCart = await Cart.findOne({ user: user });
    if (!existingCart) {
        return {
            status: 404,
            data: "No cart was found"
        }
    } else {
      const index = await find.itemInCartId(id, existingCart.items);
      if(index == null){
        return {
            status: 404,
            data: `Item with id: ${item} was not found`
        }  
      }
      else if (existingCart.items[index].quantity>1) {
          console.log("index", index)
        existingCart.items[index].quantity--;
      } else {
            if(index == 0){
                existingCart.items.shift();
            } else  {
                existingCart.items.splice(index, 1);
            }
        }
        existingCart.save();
        return {
            status: 200,
            data: existingCart.items
        }
      }
    },
    deleteAll: async (user) => {
      const existingCart = await Cart.findOne({ user: user });
      if (!existingCart) {
        return {
          status: 404,
          data: "No cart was found"
        }
      } else {
        existingCart.items = [];
        existingCart.save();
        return {
          status: 200,
          data: existingCart.items
        }
      }
    },
    getCart: async (user) =>{
      const cart = await Cart.find({ user: user});
      if (cart == []) {
        return{
          status: 200,
          data: "No items in cart"
        }
      } else {
        return {
          status: 200,
          data: cart[0].items
        }
      }
    }

}

