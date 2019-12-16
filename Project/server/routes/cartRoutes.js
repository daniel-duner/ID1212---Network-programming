const find = require("../services/find");
const mongoose = require("mongoose");
const Item = mongoose.model("items");
const User = mongoose.model("users");
const Cart = mongoose.model("carts");

module.exports = app => {
  app.put("/api/cart/add", async (req, res) => {
    const item = await Item.findById(req.body.item);
    const user = req.user._id;
    const existingCart = await Cart.findOne({ user: user });
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
      res.status(201).send(existingCart.items);
    } else {
      const index = await find.findItemInCart(item._id, existingCart.items);
      if (index == null) {
        existingCart.items.push({
          id: item._id,
          name: item.name,
          imageURL: item.imageURL,
          quantity: 1,
          price: item.price
        });
        existingCart.save();
        res.status(201).send(existingCart.items);
      } else {
        Cart.find({});
        existingCart.items[index].quantity++;
        existingCart.save();
        res.status(200).send(existingCart.items);
      }
    }
  });

  app.delete("/api/cart/delete", async (req, res) => {
    const item = req.body.item;
    console.log(req.body.item);
    const user = req.user._id;
    const existingCart = await Cart.findOne({ user: user });
    if (!existingCart) {
      res.status(404).send("No cart was found");
    } else {
      const index = find.itemId(item, existingCart.items);
      if(index == null){
          console.log(null)
        res.status(404).send(`Item with id: ${item} was not found`);
      }
      else if (existingCart.items[index].quantity>1) {
          console.log("index", index)
        existingCart.items[index].quantity--;
      } else {
            if(index == 0){
                existingCart.items.shift();
            } else  {
                existingCart.items = existingCart.items.splice(index, 1);
            }
        }
        existingCart.save();
        res.status(200).send(existingCart.items);
    }
  });

  app.delete("/api/cart/deleteAll", async (req, res) => {
    console.log(req.body.item);
    const user = req.user._id;
    const existingCart = await Cart.findOne({ user: user });
    if (!existingCart) {
      res.status(404).send("No cart was found");
    } else {
        existingCart.items = [];
        existingCart.save();
        res.status(200).send(existingCart.items);
    }
  });

  app.get("/api/cart/get", async (req, res) => {
    const cart = await Cart.find({ user: req.user._id });
    if (cart == []) {
      res.status(200, "No items in cart");
    } else {
      res.send(cart[0].items);
    }
  });
};
