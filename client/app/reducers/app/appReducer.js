import { combineReducers } 		from 'redux';
import WishlistsReducer 			from '../wishlist/wishlistReducer.js';

export default combineReducers({
  WishlistsReducer:WishlistsReducer,
})