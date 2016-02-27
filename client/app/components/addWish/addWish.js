import React, {Component}				from 'react';
import {connect} 						from 'react-redux';
import {hashHistory} 					from 'react-router';
import {Config} 						from '../../app.config.js';
import ThemeManager  	  				from 'material-ui/lib/styles/theme-manager';
import MainTheme 		  				from '../theme/mainTheme.js';
import WishlistApi 						from '../../models/wishlistApi/wishlistApi.js';
import AppStore 						from '../../stores/app/appStore.js';
import WishlistActioner 				from '../../models/actions/wishlistActioner.js';



/**
 * add Wish  React class
 * @extends {Component}
 */
export class AddWish extends Component{

	/**
	* Class constructor.
	* @param {object} props - properties passed to component.
	*/
	constructor(props){
		super(props);
		this.state = {
			title:"Add a wish",
			list : this._getListFromId(this.props.params.id),
			url_photo:"",
			avg_price:"",
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

		if(lists.length <= 0){return};
		return lists[0];
	}

	/**
	* Handle the changes of the url photo input
	* @param {object} event - the triggered event
	*/
	_urlPhotoChanged(event){
		this.setState({url_photo:event.target.value})
	}
	
	/**
	* Handle the changes of the avg price input
	* @param {object} event - the triggered event
	*/
	_avgPriceChanged(event){
		this.setState({avg_price:event.target.value})
	}

	/**
	* Handle the button click
	* @param {object} event - the triggered event
	*/
	_submit(event){
		if(this.state.url_photo && this.state.avg_price && this.state.list){
			WishlistApi.addWish(this.state.url_photo, this.state.avg_price, this.state.list.admin_token).then((response, error)=>{
				if(response){
					AppStore.dispatch(WishlistActioner.addWish(this.state.list, response));
					this.setState({avg_price:null, url_photo:null});
					hashHistory.goBack();
				}
			})
		}
	}
	
	/**
	* override
	*/
	render() {
		let styles = {
			container:{
				width:"100%",
				height:"100%",
				fontFamily:MainTheme.fontFamily,
				fontSize:"60px",
				position:"absolute",
				top:0,
				bottom:0,
				display:"flex",
				backgroundColor:MainTheme.palette.backgroundColor,
				color:MainTheme.palette.textColor
			},
			form:{
				margin:"auto",
				textAlign:"center",
				width:250
			},
			title:{
				display:"block",
				marginBottom:10,
				lineHeight:1,
				borderBottom:`3px solid ${MainTheme.palette.alternateTextColor}`
			},
			input:{
				display:"block",
				boxSizing: "border-box",
				marginBottom:18,
				width:"100%",
				padding:8,
				fontSize:18,
				borderRadius:5,
				borderWidth:0,
				color:MainTheme.palette.textColor,
				boxShadow: '0px 0px 10px 0px rgba(42,42,42,.75)',
			},
			buttonContainer:{
				width:"100%",
				height:"100%",
				display:"flex",
			},
			button:{
				margin:"auto",
				cursor:"pointer",
				color:MainTheme.palette.textColor,
				backgroundColor:"white",
				borderRadius:5,
				fontSize:18,
				padding:8,
				paddingLeft:14,
				paddingRight:14,
				borderWidth:0,
				boxShadow: '0px 0px 10px 0px rgba(42,42,42,.75)',
			},
			inputContainer:{

			}
		};
		return (
			<div style={styles.container}>
				<div style={styles.form}>
					<div style={styles.title}>{this.state.title}</div>
					<div style={styles.inputContainer}>
						<input style={styles.input} type="text" value={this.state.url_photo} onChange={this._urlPhotoChanged.bind(this)} placeholder="url to photo" />
						<input style={styles.input} type="number" value={this.state.avg_price} onChange={this._avgPriceChanged.bind(this)} placeholder="average price" />
					</div>
					<div style={styles.buttonContainer}>
						<button onClick={this._submit.bind(this)} style={styles.button} type="button">Make a wish!</button>
					</div>
				</div>
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
export default connect(selectPropertyFromStore)(AddWish);