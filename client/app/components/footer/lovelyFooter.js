import React, {Component} 	from 'react';
import {Config} 			from '../../app.config.js';
import MainTheme 		  	from '../theme/mainTheme.js';
import FontIcon 			from 'material-ui/lib/font-icon';
import ThemeManager  	  	from 'material-ui/lib/styles/theme-manager';


/**
 * A lovely footer React class
 * @extends {Component}
 */
export default class LovelyFooter extends Component{
	
	/**
	* Class constructor.
	*/
	constructor(){
		super();
		this.state = {
			
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
	* specify how to render the component.
	* @returns {html} representing the component
	*/
	render(){
		let styles={
			container:{
				position:"fixed",
				width:"100%",
				bottom:3,
				textAlign:"center"
			},
			icon:{
				fontSize:15
			}
		}
		return (
		  <div style={styles.container}>
		  	Made with <FontIcon style={styles.icon} className="material-icons">favorite</FontIcon> by a bunch of nerds
		  </div>
		);
	}
};