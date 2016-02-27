import React 								   											from 'react';
import { render } 							   											from 'react-dom';
import App							   													from './components/app/app.js';
import Home 																			from './components/home/home.js';
import Wishlist 																		from './components/wishlist/wishlist.js';
import WishlistDetailed 																from './components/wishlist/wishlistDetailed.js';
import Authentication 										 							from './components/authentication/authentication.js';
import AddWish 										 									from './components/addWish/addWish.js';
import AddWishlist 										 									from './components/addWishlist/addWishlist.js';
import { Router as ReactRouter , Route, Link, IndexRedirect, Redirect, hashHistory} 	from 'react-router';

/**
 * The application Router
 */
export class Router{

	/**
	* Class constructor.
	* create the router and listen to route changes
	*/
	constructor(){

		render((
			<ReactRouter history={hashHistory}>
				<Route path="/" component={App}>
					<Route path="auth" component={Authentication} />
					<Route path="home" component={Home} />
					<Route path="wishlist">
						<Route path=":id" component={WishlistDetailed} />
					</Route>
					<Route path="guest_wishlist">
						<Route path=":token" component={WishlistDetailed} />
					</Route>
					<Route path="add_wish">
						<Route path=":id" component={AddWish} />
					</Route>
					<Route path="add_wishlist" component={AddWishlist}/>					
					<IndexRedirect to="/auth" />
				</Route>
			</ReactRouter>
		), document.getElementById("app")); 

	}

}


/**
 * the application's router.
 * @type {class}
 */
export let router = new Router();



