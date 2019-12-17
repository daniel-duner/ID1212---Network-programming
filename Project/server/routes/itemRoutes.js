const itemDBH = require('../integration/itemDBH');
module.exports = (app) => {
    app.post('/api/item/add', async (req, res) => {
        const result = await itemDBH.addItem(req.body);
        res.status(result.status).send(result.data);
    });

    app.get('/api/item/getAll',async (req, res) => {
        const result = await itemDBH.getAllItems();
        res.status(result.status).send(result.data);
    });
}