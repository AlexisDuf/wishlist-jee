import React, {Component} 	from 'react';
import {connect} 			from 'react-redux';
import {hashHistory} 		from 'react-router';
import {Config} 			from '../../app.config.js';
import AppBar 				from 'material-ui/lib/app-bar';
import MainTheme 		  	from '../theme/mainTheme.js';
import ThemeManager  	  	from 'material-ui/lib/styles/theme-manager';
import WishlistGrid 		from '../grid/wishlistGrid.js';
import AddWishlistButton 	from '../buttons/addWishlistButton.js';

/**
 * Home React class
 * @extends {Component}
 */
export default class Home extends Component{
	
	/**
	* Class constructor.
	*/
	constructor(props){
		super(props);
		this.state = {
			title:Config.application_name,
		}
		
	}

	/**
     * @type {object}
	* defining the child context types
     */
	static get childContextTypes(){
        return {
			muiTheme: React.PropTypes.object,
		};
    }

    /**
	* get the child context.
	* @returns {object} wich specify the child context
	*/
	getChildContext() {
		return {
			muiTheme: ThemeManager.getMuiTheme(MainTheme),
		};
	}

	/**
	* Go to add wishlist
	*/
	_goToAddWishlist(){
		hashHistory.push('/add_wishlist');
	}

	/**
	* @override
	*/
	componentDidMount(){
		if(!this.props.userId){hashHistory.push('/auth')};
	}

	/**
	* specify how to render the component.
	* @returns {html} representing the component
	*/
	render(){
		let styles={
			container:{
				position:"absolute",
				width:"100%",
				height:"100%",
			},
			placehodlerContainer:{
				display:(this.props.ownedWishlits.length > 0)?'none':'flex',
				position:"absolute",
				top:0,
				left:0,
				width:"100%",
				height:"100%"
			},
			placehodler:{	
				margin:"auto",
				fontSize:30,
			}
		}

		return (
		  <div style={styles.container}>
		  	<WishlistGrid wishlists={this.props.ownedWishlits}/>
		  	<div style={styles.placehodlerContainer}>
		  		<div style={styles.placehodler}> No wishlist yet!</div>
	  		</div>
		  	<AddWishlistButton onClick={this._goToAddWishlist.bind(this)} />
		  </div>
		);
	}
};


/**
* Select the properties needed from the store.
* @param {object} state - containing the all store properties.
* @returns {object} the needed subset.
*/
function selectPropertyFromStore(state) {
	return {
		ownedWishlits:state.WishlistsReducer.ownedWishlists,
		guestWishlists:state.WishlistsReducer.guestWishlists,
		userId:state.WishlistsReducer.userId,
		name:state.WishlistsReducer.name,
		email:state.WishlistsReducer.email,
	}
}

/**
* An extended verison of {SignInform} witch is connected to the store
*/
export default connect(selectPropertyFromStore)(Home);
