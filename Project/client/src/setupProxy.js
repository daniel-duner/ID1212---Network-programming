const proxy = require('http-proxy-middleware');

module.exports = function(app){
    app.use(proxy(['/api', '/auth/google', '/api/item/getAll', '/api/cart/add', '/api/cart/delete'], {target: 'http://localhost:5000'}));
}