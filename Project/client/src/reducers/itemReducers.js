import {FETCH_ITEMS} from '../actions/types'


export default function(state = null, action){
    console.log(action);
    switch(action.type){
        case FETCH_ITEMS:
            return action.payload
        default:
            return state;
    }
}