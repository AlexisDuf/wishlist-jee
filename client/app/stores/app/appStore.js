import { createStore } 		from 'redux'
import AppReducer 			from '../../reducers/app/appReducer.js'

/**
 * the AppStore.
 * @type {class}
 */
let AppStore = createStore(AppReducer);
export default AppStore;