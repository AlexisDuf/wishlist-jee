import React, {Component}				from 'react';
import {Config} 						from '../../app.config.js';
import {connect} 						from 'react-redux';
import {hashHistory} 					from 'react-router';
import ThemeManager  	  				from 'material-ui/lib/styles/theme-manager';
import MainTheme 		  				from '../theme/mainTheme.js';
import Paper 							from 'material-ui/lib/paper';
import AddWishlistButton 				from '../buttons/addWishlistButton.js';
import WishlistApi 						from '../../models/wishlistApi/wishlistApi.js';
import AppStore 						from '../../stores/app/appStore.js';
import WishlistActioner 				from '../../models/actions/wishlistActioner.js';
import Wish 							from '../wish/wish.js';

/**
 * Wishlist Detailed React class
 * @extends {Component}
 */
export class WishlistDetailed extends Component{

	/**
	* Class constructor.
	* @param {object} props - properties passed to component.
	*/
	constructor(props){
		super(props);

		this.state = {
			list : null,
			isGuest : true,
		}
	}

	/**
	* Get the list of the provided id from the owned and guest lists
	* @param {string} id - the id
	* @returns {object} the list
	*/
	_getListFromId(id){
		let lists = this.props.ownedWishlits.concat(this.props.guestWishlists).filter((list)=>{
			return list.id == id;
		});

		if(lists.length <= 0){hashHistory.push("/home")};
		return lists[0];
	}

	_getList(){
		if(this.props.params.id){
			return this._getListFromId(this.props.params.id);
		}else if(this.props.params.token){
			this._fetchList(this.props.params.token);
		}
	}

	_fetchList(token){
		WishlistApi.fetchGuestWishlist(token).then((response)=>{
			if(response){
				this.setState({list:response}, ()=>{
					this.setState({isGuest: this._isGuest()});
					AppStore.dispatch(WishlistActioner.setGuestWishlists([response]));

				});
			}else{
				hashHistory.push("/home")
			}
		}).catch((error)=>{
				hashHistory.push("/home")
		})
	}

	/**
	* Go to add wish 
	*/
	_gotToAddWish(){
		hashHistory.push(`/add_wish/${this.props.params.id}`);
	}

	_getWishlistNode(){
		
		let styles = {
			container:{
				width:"100%",
				height:"100%",
			},
			name:{
				cursor:"pointer",				
				margin:"auto",
				margin:10,
				fontSize:50,
				color:MainTheme.palette.textColor
			},
			descriptionContainer:{
			
			},
			description:{
				margin:10,
				fontSize:30
			},
			descriptionTitle:{
				margin:10
			},
			wishesContainer:{
				width:"100%",
				display:"flex"
			},
			wishes:{
				display:"table-caption",
				margin:"auto"
			}
		};
		
		if(this.state.list){
			return(
					<Paper style={styles.container} zDepth={0}>
						<div title={this.state.list.title} style={styles.name}>
							{this.state.list.title}
						</div>
						<div style={styles.descriptionTitle}>
							Description				
						</div>
						<div style={styles.description}>
							{this.state.list.description}
						</div>
						<div style={styles.descriptionTitle}>
							Link				
						</div>
						<div style={styles.description}>
							{`${Config.adress}guest_wishlist/${this.state.list.guest_token}`}
						</div>
						<div style={styles.wishesContainer}>
							<div style={styles.wishes}>
								{this._getWishesNodes()}
							</div>
						</div>
					</Paper>
				)
		}else{
			return(
					<div style={styles.wishlist}>
						wait
					</div>
				)
		}

	}

	_getWishesNodes(){
		return this.state.list.items.map((wish, index)=>{
			let style = {
				margin:10
			}
			return (
				<div key={index} style={style}>
					<Wish wishlist={this.state.list} wish={wish} />
				</div>
			)
		})
	}

	_getactions(){
		if(!this.state.isGuest){
			return (
					<AddWishlistButton onClick={this._gotToAddWish.bind(this)} />
				)
		}
		return null

	}

	_isGuest(){
		if(!this.state.list){return true};
		if(this.props.params.id){return false};
		if(this.props.params.token){
			return this.state.list.guest_token == this.props.params.token 
		}
	}

	componentDidMount(){
		this.setState({list:this._getList()},()=>{
			this.setState({isGuest:this._isGuest()});
		});
	}
	
	/**
	* override
	*/
	render() {
		let styles = {
			container:{
				width:"100%",
				height:"100%",
				display:"flex"				
			}
		};
		console.log(this.props)
		return (
			<div style={styles.container}>
				{this._getWishlistNode()}
				{this._getactions()}
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
* An extended verison of {WishlistDetailed} witch is connected to the store
*/
export default connect(selectPropertyFromStore)(WishlistDetailed);