import React, {Component}				from 'react';
import {Config} 						from '../../app.config.js';
import {connect} 						from 'react-redux';
import {hashHistory} 					from 'react-router';
import CookieManager 					from '../../models/cookieManager/cookieManager.js';
import ThemeManager  	  				from 'material-ui/lib/styles/theme-manager';
import MainTheme 		  				from '../theme/mainTheme.js';
import WishlistApi 						from '../../models/wishlistApi/wishlistApi.js';
import AppStore 						from '../../stores/app/appStore.js';
import WishlistActioner 				from '../../models/actions/wishlistActioner.js';
import LovelyFooter 					from '../footer/lovelyFooter.js';

/**
 * Authentication React class
 * @extends {Component}
 */
export class Authentication extends Component{

	/**
	* Class constructor.
	* @param {object} props - properties passed to component.
	*/
	constructor(props){
		super(props);
		this.state = {
			title:Config.application_name,
			email:"",
			name:""
		}
	}

	/**
	* Handle the changes of the email input
	* @param {object} event - the triggered event
	*/
	_emailChanged(event){
		this.setState({email:event.target.value});
	}

	/**
	* Handle the changes of the name input
	* @param {object} event - the triggered event
	*/
	_nameChanged(event){
		this.setState({name:event.target.value});
	}

	/**
	* Handle the button click
	* @param {object} event - the triggered event
	*/
	_submit(event){
		if(this.state.name && this.state.email){
			WishlistApi.log(this.state.name, this.state.email).then((response, error)=>{
				if(response){
					AppStore.dispatch(WishlistActioner.setUser(response));
					CookieManager.saveObject({userId:response.id, name: response.name, email:response.email});
					hashHistory.push('/home');
				}
			})
		}
	}

	/**
	* @override
	*/
	componentDidMount(){
		let credentials = CookieManager.get(["userId", "name", "email"]);
		if(credentials.name != null && credentials.email != null && credentials.userId != null){
			this.setState({email:credentials.email, name:credentials.name},this._submit.bind(this));
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
						<input style={styles.input} type="text" value={this.state.name} onChange={this._nameChanged.bind(this)} placeholder="NAME" />
						<input style={styles.input} type="email" value={this.state.email} onChange={this._emailChanged.bind(this)} placeholder="EMAIL" />
					</div>
					<div style={styles.buttonContainer}>
						<button onClick={this._submit.bind(this)} style={styles.button} type="button">Make a wish happen !</button>
					</div>
				</div>
				<LovelyFooter/>
			</div>
		);
	}
}; 

/*
* Select the properties needed from the store.
* @param {object} state - containing the all store properties.
* @returns {object} the needed subset.
*/
function selectPropertyFromStore(state) {
	return {
		ownedWishlits:state.WishlistsReducer.ownedWishlists,
		guestWishlists:state.WishlistsReducer.guestWishlists,
	}
}

/**
* An extended verison of {SignInform} witch is connected to the store
*/
export default connect(selectPropertyFromStore)(Authentication);