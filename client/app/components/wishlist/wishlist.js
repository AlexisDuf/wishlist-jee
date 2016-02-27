import React, {Component}				from 'react';
import {hashHistory}				 	from 'react-router';
import {connect} 						from 'react-redux';
import ThemeManager  	  				from 'material-ui/lib/styles/theme-manager';
import MainTheme 		  				from '../theme/mainTheme.js';
import Paper 							from 'material-ui/lib/paper';
import Badge 							from 'material-ui/lib/badge';
import CakeIcon 		 				from 'material-ui/lib/svg-icons/social/cake';
import CashIcon 		 				from 'material-ui/lib/svg-icons/editor/attach-money';

/**
 * Wishlist React class
 * @extends {Component}
 */
export default class Wishlist extends Component{

	/**
	* Class constructor.
	* @param {object} props - properties passed to component.
	*/
	constructor(props){
		super(props);
	}

	_goToDetailedView(){
		hashHistory.push(`/wishlist/${this.props.list.id}`);
	}

	_getTitle(){
		const maxChar = 12;

		if(this.props.list.title.length>maxChar){
			let splitedTitleArray = this.props.list.title.split("");
			splitedTitleArray.splice(maxChar);
			return `${splitedTitleArray.join("")}...`;
		}

		return this.props.list.title;

	}

	_getNbProposals(){
		let nbProposals = 0
		this.props.list.items.map((item)=>{
			nbProposals += item.propositions.length;
		})
		return nbProposals;
	}

	
	
	/**
	* override
	*/
	render() {
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

			badge:{
				position:"absolute",
				bottom:0,
				width:"100%",
				display:"flex"
			},

			description:{
				margin:10,
				fontSize:30
			},

			descriptionTitle:{
				margin:10
			}
		};
		return (
			<Paper style={styles.container}>
				<div title={this.props.list.title} style={styles.name} onClick={this._goToDetailedView.bind(this)}>
					{this._getTitle()}
				</div>
				<div style={styles.descriptionTitle}>
					Description				
				</div>
				<div style={styles.description}>
					{this.props.list.description}
				</div>
				<div style={styles.badge}>
					<Badge badgeContent={this.props.list.items.length} primary={true}>
				      <CakeIcon />
				    </Badge>
				    <Badge badgeContent={this._getNbProposals()} primary={true}>
				      <CashIcon />
				    </Badge>
				</div>
			</Paper>
			
		);
	}
};