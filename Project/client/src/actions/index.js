import axios from 'axios';
import {
    FETCH_USER, 
    FETCH_CART, 
    FETCH_ITEMS,
    ADD_TO_CART,
    DELETE_ITEM_FROM_CART,
    DELETE_ALL_ITEMS_FROM_CART
} from './types';

export const fetchUser = () => async dispatch => {
       const res = await axios.get('/api/current_user')
       dispatch({ type: FETCH_USER, payload: res.data});
};
export const fetchCart = () => async dispatch => {
    const res = await axios('/api/cart/get')
    dispatch({ type: FETCH_CART, payload: res.data});
};
export const addToCart = (item) => async dispatch => {
    const res = await axios.put('/api/cart/add',{
        item: item
    })
    dispatch({ type: ADD_TO_CART, payload: res.data});
};
export const removeItemFromCart = (item) => async dispatch => {
    const res = await axios.delete('/api/cart/delete',{data:{item: item}})
   dispatch({ type: DELETE_ITEM_FROM_CART, payload: res.data});
};
export const removeAllItemsFromCart = () => async dispatch => {
    const res = await axios.delete('/api/cart/deleteAll')
   dispatch({ type: DELETE_ALL_ITEMS_FROM_CART, payload: res.data});
};

export const fetchItems = () => async dispatch => {
    const res = await axios.get('/api/item/getAll')
    dispatch({ type: FETCH_ITEMS, payload: res.data});
};