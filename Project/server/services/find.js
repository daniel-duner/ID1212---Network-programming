function addItemToCart(id, items){
    if(findItemInCart(id, items)){
        items[i].quantity++;
        return true;
    } else {
        return false
    }
}

function itemId(id, items){
    for (let i in items) {
        if (items[i]._id == id) {
           return i;
        }
      }
      return null;
}

function findItemInCart(id, items){
    for (let i in items) {
      if (items[i].id == id) {
         return i;
      }
    }
    return null;
}

exports.findItemInCart = findItemInCart;
exports.addItemToCart = addItemToCart;
exports.itemId = itemId;