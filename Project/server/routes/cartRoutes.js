const cartDBH = require('../integration/cartDBH');

module.exports = app => {
  app.put("/api/cart/add", async (req, res) => {
      const result = await cartDBH.addItem(req.body.item, req.user._id)
      res.status(result.status).send(result.data);
  });

  app.delete("/api/cart/delete", async (req, res) => {
    const result = await cartDBH.deleteItem(req.body.item, req.user._id);
    res.status(result.status).send(result.data);
  });

  app.delete("/api/cart/deleteAll", async (req, res) => {
    const result = await cartDBH.deleteAll(req.user._id);
    res.status(result.status).send(result.data);
  });

  app.get("/api/cart/get", async (req, res) => {
    const result = await cartDBH.getCart(req.user._id);
    res.status(result.status).send(result.data);
  });
};
