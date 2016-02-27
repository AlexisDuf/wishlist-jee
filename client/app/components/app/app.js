import React, {Component} 	from 'react';
import {Provider}		  	from 'react-redux';
import AppBar 				from 'material-ui/lib/app-bar';
import AppStore 			from '../../stores/app/appStore.js';
import MainTheme 		  	from '../theme/mainTheme.js';
import ThemeManager  	  	from 'material-ui/lib/styles/theme-manager';
import InjectTapEventPlugin from 'react-tap-event-plugin';

/**
 * App React class
 * @extends {Component}
 */
export default class App extends Component{
	
	/**
	* Class constructor.
	*/
	constructor(){
		super();
		InjectTapEventPlugin();//to convert click to tap
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
			
		}
		return (
		  <div>
		    <Provider store={AppStore}>
				{this.props.children}
			</Provider>
		  </div>
		);
	}
};