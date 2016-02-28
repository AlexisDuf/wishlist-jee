import xhttp 					from 'xhttp/native';
import {Config} 				from '../../app.config.js';
import {hashHistory} 			from 'react-router';
import CookieManager 			from '../cookieManager/cookieManager.js';

export default class WishlistApi {
	
	/*
	* Send a log request with the given credentials
	* @param {string} name - the name
	* @param {string} email - the email
	* @returns {promise} the http promise
	*/
	static log(name, email){

		return xhttp({
		  url: `${Config.api.root}${Config.api.users}`,
		  method:'post',
		  data: {
		  	email:email,
		  	name:name
		  },
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		})

	}

	/*
	* fetch guest wishlist request with the given token
	* @param {string} token - the token
	* @returns {promise} the http promise
	*/
	static fetchGuestWishlist(token){

		return xhttp({
		  url: `${Config.api.root}${Config.api.wishlists}${token}`,
		  method:'get',
		  data: {},
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		})

	}

	/*
	* Send a add wishlist request
	* @param {string} name - the name
	* @param {string} description - the description
	* @returns {promise} the http promise
	*/
	static addWish(url_photo, avg_price, token){
		return xhttp({
		  url: `${Config.api.root}${Config.api.wishlists}${token}/items/`,
		  method:'post',
		  data: {
		  	url_photo:url_photo,
		  	avg_price:avg_price,
		  },
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		}).catch(WishlistApi.requestFailed);

	}

	/*
	* Post a comment on a wishlist request
	* @param {string} content - the comment content
	* @param {number} itemId - the item id
	* @param {string} token - the wishlist guest token 
	* @returns {promise} the http promise
	*/
	static addComment(content, itemId, token){
		return xhttp({
		  url: `${Config.api.root}${Config.api.wishlists}${token}/items/${itemId}/comments`,
		  method:'post',
		  data: {
		  	content:content,
		  	user_id:CookieManager.get(["userId"])["userId"]
		  },
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		}).catch(WishlistApi.requestFailed);

	}

	/*
	* Delete a comment on a wishlist request
	* @param {object} comment - the comment
	* @param {number} itemId - the item id
	* @param {string} token - the wishlist guest token 
	* @returns {promise} the http promise
	*/
	static removeComment(comment, itemId, token){
		return xhttp({
		  url: `${Config.api.root}${Config.api.wishlists}${token}/items/${itemId}/comments/${comment.id}`,
		  method:'delete',
		  data: {
		  	user_id:CookieManager.get(["userId"])["userId"]
		  },
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		}).catch(WishlistApi.requestFailed);

	}

	/*
	* Post a proposition on a wishlist request
	* @param {number} price - the price
	* @param {number} itemId - the item id
	* @param {string} token - the wishlist guest token 
	* @returns {promise} the http promise
	*/
	static addProposal(price, itemId, token){
		return xhttp({
		  url: `${Config.api.root}${Config.api.wishlists}${token}/items/${itemId}/propositions`,
		  method:'post',
		  data: {
		  	price:price,
		  	user_id:CookieManager.get(["userId"])["userId"]
		  },
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		}).catch(WishlistApi.requestFailed);

	}

	/*
	* Send a add wishlist request
	* @param {string} name - the name
	* @param {string} description - the description
	* @returns {promise} the http promise
	*/
	static addWishlist(name, description){
		return xhttp({
		  url: `${Config.api.root}${Config.api.wishlists}`,
		  method:'post',
		  data: {
		  	description:description,
		  	title:name,
		  	user_id:CookieManager.get(["userId"])["userId"]
		  },
		  headers:{
		  	'Content-Type':'application/x-www-form-urlencoded',
		  }

		}).catch(WishlistApi.requestFailed);

	}

	/*
	* Parse the response properly into an object
	* @param {object} response - the object wich needs to parse it fields
	* @returns {object} the parsed response
	*/
	static parse(response){
		let parsedResponse = {};
		for(let key in response){
			if(WishlistApi._isValidJSON(response[key])){
				parsedResponse[key] = JSON.parse(response[key])
			}else{
				parsedResponse[key] = response[key]
			}
		}
		return parsedResponse;
	}

	/*
	* Test if is a valid json
	* @param {object} element - the object wich needs to be tested
	* @returns {boolean} the reponse
	*/
	static _isValidJSON(element){
		try{
			let parsed = JSON.parse(element);
			return true;
		}catch(error){
			return false;
		}
		return false;
	}

	/*
	* If a request fails
	* @param {object} error - the error
	*/
	static requestFailed(error){
		CookieManager.remove("userId");
		CookieManager.remove("email");
		CookieManager.remove("name");
		hashHistory.push('/auth');
	}


}
