import AbstractActioner from './abstractActioner.js'

/**
 * All wishlist action types 
 * @type {Object}
 */
export let TYPES = {
	SET_OWNED_WISHLISTS:'SET_OWNED_WISHLIST',
	SET_GUEST_WISHLISTS:'SET_GUEST_WISHLISTS',
	ADD_OWNED_WISHLIST:'ADD_OWNED_WISHLIST',
	ADD_WISH:'ADD_WISH',
	SET_USER:'SET_USER',
	SET_WISH:'SET_WISH'
};


/**
* Wishlist actioner.
*/
export default class WishlistActioner extends AbstractActioner{

	/**
	* To get a set owned wishlists action
	* @params {array} wl - the array of lists to set
	* @returns {object} - the action 
	*/
	static setOwnedWishlists(wl){
		return {
			type:TYPES.SET_OWNED_WISHLISTS,
			lists:wl
		}
	}

	/**
	* To get a set a wish action
	* @params {object} wl - the wishlist of the wish
	* @params {object} wl - the wish to set
	* @returns {object} - the action 
	*/
	static setWish(wl, wish){
		return {
			type:TYPES.SET_WISH,
			list:wl,
			wish:wish
		}
	}

	/**
	* To get a set owned wishlists action
	* @params {array} wl - the array of lists to set
	* @returns {object} - the action 
	*/
	static addWish(wl, wish){
		return {
			type:TYPES.ADD_WISH,
			list:wl,
			wish:wish
		}
	}

	/**
	* To get a set user action
	* @params {array} user - the user to set
	* @returns {object} - the action 
	*/
	static setUser(user){
		return {
			type:TYPES.SET_USER,
			user:user
		}
	}

	/**
	* To get a set guest wishlists action
	* @params {array} wl - the array of lists to set
	* @returns {object} - the action 
	*/
	static setGuestWishlists(wl){
		return {
			type:TYPES.SET_GUEST_WISHLISTS,
			lists:wl
		}
	}

	/**
	* To get a add owned wishlist action
	* @params {object} wl - the lists to add
	* @returns {object} - the action 
	*/
	static addOwnedWishlist(wl){
		return {
			type:TYPES.ADD_OWNED_WISHLIST,
			list:wl
		}
	}
	
}