import {
    FETCH_CART,
    ADD_TO_CART, 
    DELETE_ITEM_FROM_CART, 
    DELETE_ALL_ITEMS_FROM_CART
} from '../actions/types'


export default function(state = null, action){
    //console.log(action);
    switch(action.type){
        case FETCH_CART:
            return action.payload || false;
        case ADD_TO_CART:
            return action.payload;
        case DELETE_ITEM_FROM_CART:
            return action.payload;
        case DELETE_ALL_ITEMS_FROM_CART:
            return action.payload;
        default:
            return state;
    }
}