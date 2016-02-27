import { TYPES as wishlistActionType } from '../../models/actions/wishlistActioner.js';

const initialState = {
	userId:null,
	email:null,
	name:null,
	ownedWishlists:[],
	guestWishlists:[],
}


/**
* Call on action recieved.
* @param {object} state - the current state, default value is the inital state.
* @param {object} action - the performed action.
*/
export default function WishlistsReducer(state = initialState, action){

		switch (action.type) {

		case wishlistActionType.SET_OWNED_WISHLIST:

			var newState = Object.assign({}, state,state);
			newState.ownedWishlists = action.lists;
			return Object.assign({}, state,newState);

		case wishlistActionType.ADD_WISH:

			var newState = Object.assign({}, state,state);
			newState.ownedWishlists = newState.ownedWishlists.map((wl)=>{
				if(action.list.id == wl.id){
					wl.items.push(action.wish);
				}
				return wl;
			})
			return Object.assign({}, state,newState);

		case wishlistActionType.SET_WISH:

			var newState = Object.assign({}, state,state);
			newState.ownedWishlists = newState.ownedWishlists.map((wl)=>{
				if(action.list.id == wl.id){
					wl.items = wl.items.map((wish)=>{
						if(wish.id == action.wish.id){
							return action.wish
						}
						return wish;
					})
				}
				return wl;
			})
			newState.guestWishlists = newState.guestWishlists.map((wl)=>{
				if(action.list.id == wl.id){
					wl.items = wl.items.map((wish)=>{
						if(wish.id == action.wish.id){
							return action.wish
						}
						return wish;
					})
				}
				return wl;
			})
			return Object.assign({}, state,newState);

		case wishlistActionType.SET_GUEST_WISHLISTS:

			var newState = Object.assign({}, state,state);
			newState.guestWishlists = action.lists;
			return Object.assign({}, state,newState);

		case wishlistActionType.ADD_OWNED_WISHLIST:

			var newState = Object.assign({}, state,state);
			newState.ownedWishlists.push(action.list);
			return Object.assign({}, state,newState);

		case wishlistActionType.SET_USER:

			var newState = Object.assign({}, state,state);
			newState.ownedWishlists = action.user.wishlists;
			newState.userId = action.user.id;
			newState.email = action.user.email;
			newState.name = action.user.name;
			return Object.assign({}, state,newState);

		default:
			return state;
		};
}
