import React, {Component} 	from 'react';
import MainTheme 		  	from '../theme/mainTheme.js';
import ThemeManager  	  	from 'material-ui/lib/styles/theme-manager';
import FloatingActionButton from 'material-ui/lib/floating-action-button';
import ContentAdd 			from 'material-ui/lib/svg-icons/content/add';

/**
 * Add Wishlist Button React class
 * @extends {Component}
 */
export default class AddWishlistButton extends Component{
	
	/**
	* Class constructor.
	*/
	constructor(props){
		super(props);
		this.state={}
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
	* specify how to render the component.
	* @returns {html} representing the component
	*/
	render(){

		let styles = {
			container:{
				position:"fixed",
				bottom:10,
				right:10
			},
		}

		return (
			<div style={styles.container}>
				<FloatingActionButton onClick={this.props.onClick}>
			      <ContentAdd />
			    </FloatingActionButton>
		    </div>
		);
	}
};
