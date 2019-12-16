
function itemInCartId(id, items){
    for (let i in items) {
        if (items[i]._id == id) {
           return i;
        }
      }
      return null;
}

function itemId(id, items){
    for (let i in items) {
      if (items[i].id == id) {
         return i;
      }
    }
    return null;
}

exports.itemInCartId= itemInCartId;
exports.itemId = itemId;