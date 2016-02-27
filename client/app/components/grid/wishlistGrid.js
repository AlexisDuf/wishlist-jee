import React, {Component}                                             from 'react';
import Wishlist                                                       from '../wishlist/wishlist.js';
import {Responsive as ResponsiveReactGridLayout, WidthProvider}       from'react-grid-layout';

const ReactGridLayout = WidthProvider(ResponsiveReactGridLayout);


/**
 * Wishlist grid React class
 * @extends {Component}
 */
export default class WishlistGrid extends Component{
  
  /**
 * Class constructor
 */
  constructor(props){
    super(props);
    this.state =  {
      layouts: {"lg":this.generateLayout("lg")},
      currentBreakpoint: 'lg'
    }
  }

  /**
  * @override
  */
  static get defaultProps() {
    return {
      className: "layout",
      cols: {lg: 4, md: 2, sm: 2, xs: 1, xxs:1},
      rowHeight: 400,
      verticalCompact:true
    };
  }

  onBreakpointChange(breakpoint) {

    let newLayouts = Object.assign({}, this.state.layouts,this.state.layouts);
    newLayouts[breakpoint] = this.generateLayout(breakpoint);

    this.setState({
      layouts:newLayouts,
      currentBreakpoint: breakpoint
    });
  }

  /**
  * get the wishlist nodes.
  * @returns {array} containing the nodes
  */
  _getWishlistNodes(){
    return this.props.wishlists.map((wl,index)=>{
      return (
          <div key={index}>
            <Wishlist key={index} list={wl} owned={true}/>
          </div>
        )
    })
  }

  /**
  * generate the layout
  * @params {string} breakPoint - the break point ton render
  * @returns {object} the layout
  */
  generateLayout(breakPoint) {
    let layouts = this.props.wishlists.map((wl, index)=>{
      return {
        i: index.toString(),
        x: index%this.props.cols[breakPoint],
        y: Math.floor(index/this.props.cols[breakPoint]),
        w: 1,
        h: 1
      }
    })
    return layouts
  }

  /**
  * specify how to render the component.
  * @returns {html} representing the component
  */
  render() {
    let styles ={
      container:{
        width:"100%",
        height:"100%"
      }
    }
    return ( 
      <div style={styles.container}>
        <ReactGridLayout  className="layout" onBreakpointChange={this.onBreakpointChange.bind(this)} layouts={this.state.layouts} {...this.props}>
          {this._getWishlistNodes()}
        </ReactGridLayout>
      </div>
    );
  }


}
