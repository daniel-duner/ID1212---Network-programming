import {combineReducers} from 'redux';
import authReducer from './authReducers';
import itemReducer from './itemReducers';
import cartReducer from './cartReducers';

export default combineReducers({
    auth: authReducer,
    items: itemReducer,
    cart: cartReducer
});