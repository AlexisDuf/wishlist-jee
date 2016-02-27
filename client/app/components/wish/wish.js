import React, {Component}				from 'react'
import {connect} 						from 'react-redux'
import ThemeManager  	  				from 'material-ui/lib/styles/theme-manager'
import MainTheme 		  				from '../theme/mainTheme.js'
import WishlistApi 						from '../../models/wishlistApi/wishlistApi.js';
import AppStore 						from '../../stores/app/appStore.js';
import WishlistActioner 				from '../../models/actions/wishlistActioner.js';
import Tabs 							from 'material-ui/lib/tabs/tabs';
import Tab 								from 'material-ui/lib/tabs/tab';
import SwipeableViews 					from 'react-swipeable-views';

/**
 * Wish React class
 * @extends {Component}
 */
export default class Wish extends Component{

	/**
	* Class constructor.
	* @param {object} props - properties passed to component.
	*/
	constructor(props){
		super(props);
		this.state = {
			comment:"",
			proposal:null,
			slideIndex:0,
		}
	}

	_getProposalNodes(){
		let styles = {
			commentContainer:{
				margin:10
			},
			nameContainer:{
				textTransform:"uppercase",
				color:MainTheme.palette.textColor
			},
			contentContainer:{
				marginTop:5
			}
		}
		return this.props.wish.propositions.map((proposition, index)=>{
			return (
				<div key={index} style={styles.commentContainer}>
					<div style={styles.nameContainer}>
						{proposition.creator.name}
					</div>
					<div style={styles.contentContainer}>
						{`${proposition.price}€`}
					</div>
				</div>
			)
		});
	}

	_getCommentNodes(){
		let styles = {
			commentContainer:{
				margin:10
			},
			nameContainer:{
				textTransform:"uppercase",
				color:MainTheme.palette.textColor
			},
			contentContainer:{
				marginTop:5
			}
		}
		return this.props.wish.comments.map((comment, index)=>{
			return (
				<div key={index} style={styles.commentContainer}>
					<div style={styles.nameContainer}>
						{comment.creator.name}
					</div>
					<div style={styles.contentContainer}>
						{comment.content}
					</div>
				</div>
			)
		});
	}

	_commentChanged(event){
		this.setState({comment:event.target.value});
	}

	_submitComment(event){

		if(!(this.state.comment.length >= 0)){return};
		WishlistApi.addComment(this.state.comment, this.props.wish.id, this.props.wishlist.guest_token).then((response)=>{
			AppStore.dispatch(WishlistActioner.setWish(this.props.wishlist, response));
			this.setState({comment:""});
		})
	}

	_proposalChanged(event){
		this.setState({proposal:event.target.value});
	}

	_submitProposal(event){
		if(!this.state.proposal == null){return};
		WishlistApi.addProposal(this.state.proposal, this.props.wish.id, this.props.wishlist.guest_token).then((response)=>{
			AppStore.dispatch(WishlistActioner.setWish(this.props.wishlist, response));
			this.setState({proposal:null});
		})
	}

	_handleSlideChange(value){
		this.setState({slideIndex: value});
	}
	
	/**
	* override
	*/
	render() {
		let styles = {
			container:{
				width:"300px",
				marginTop:30
			},
			img:{
				height:"100%"
			},
			imgContainer:{
				height:"100%",
			},
			itemContainer:{
				height:100,
				display:"flex"
			},
			avgPrice:{
				margin:"auto",
				fontSize:20
			},
			comments:{

			},
			addComment:{
				marginTop:10,
			},
			section:{
				height: 600,
			},
			swipeableView:{
			},
			tabs:{
				marginTop:10
			},
			tabList:{
				height:400,
				overflowY:"scroll"
			},
			button:{
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
			input:{
				display:"block",
				margin:"auto",
				boxSizing: "border-box",
				marginBottom:18,
				width:"80%",
				padding:8,
				fontSize:18,
				borderRadius:5,
				borderWidth:0,
				color:MainTheme.palette.textColor,
				boxShadow: '0px 0px 10px 0px rgba(42,42,42,.75)',
			},
			addSection:{
				width:"100%",
				textAlign:"center",
				marginTop:50
			}
		};
		return (
			<div style={styles.container}>
				<div style={styles.itemContainer}>
					<div style={styles.imgContainer}>
						<img style={styles.img} src={this.props.wish.url_photo}/>
					</div>
					<div style={styles.avgPrice}>
						{`${this.props.wish.avg_price}€`}
					</div>
				</div>

				<div style={styles.tabs}>
					<Tabs style={styles.inlineTabs} onChange={this._handleSlideChange.bind(this)} value={this.state.slideIndex}>
			          <Tab style={{color:MainTheme.palette.alternateTextColor}} label="Comments" value={0} />
			          <Tab style={{color:MainTheme.palette.alternateTextColor}} label="Propsals" value={1} />
			        </Tabs>
	        		<SwipeableViews style={styles.swipeableView} index={this.state.slideIndex} onChangeIndex={this._handleSlideChange.bind(this)}>
						<div style={styles.section}>
							<div style={styles.tabList}>
								{this._getCommentNodes()}
							</div>
							<div style={styles.addSection}>
								<input style={styles.input} type="text" value={this.state.comment} onChange={this._commentChanged.bind(this)} placeholder="comment" />
								<button onClick={this._submitComment.bind(this)} style={styles.button} type="button">Make a comment</button>
							</div>
						</div>
						<div style={styles.section}>
							<div style={styles.tabList}>
								{this._getProposalNodes()}
							</div>
							<div style={styles.addSection}>
								<input style={styles.input} type="number" value={this.state.proposal} onChange={this._proposalChanged.bind(this)} placeholder="proposal" />
								<button onClick={this._submitProposal.bind(this)} style={styles.button} type="button">Make a proposal</button>
							</div>
						</div>
			        </SwipeableViews>
				</div>

				<div style={styles.proposals}>
				</div>
				<div style={styles.comments}>
				</div>
			</div>
		);
	}
};