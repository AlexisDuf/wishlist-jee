import Cookie from'cookie';

/**
 * CookieManger class
 */
export default class CookieManager{
	
	/**
	 * Class constructor
	 */
	constructor(){
	
	}

	/**
	 * To save an object into the cookie
	 * @param {object} object - the object you want to save in the cookie
	 */
	static saveObject(object){
		let serializedCookie = "";
		Object.keys(object).map((key)=>{
			let serializedCookie = Cookie.serialize(key, object[key]);
			document.cookie= serializedCookie;
		});
	}

	/**
	 * To save a cookie
	 * @param {string} key - the key you want to save in the cookie
	 * @param {string} value - the value you want to save in the cookie
	 * @param {string} expires - the expiration date of the cookie second
	 */
	static save(key, value, expires){
		let options = {};
		if(expires != null){
			let now = new Date();	
			now.setSeconds(now.getSeconds() + parseInt(expires))
			options = {
				expires:(value != null)?new Date(0):now
			}
		}
		let serializedCookie = Cookie.serialize(key, value,options);
		document.cookie= serializedCookie;
	}

	/**
	 * To remove a cookie
	 * @param {string} key - the key of the cookie you want to delete
	 */
	static remove(key){
		CookieManager.save(key, null, 0);
	}

	/**
	 * To remove a cookie
	 * @param {Array} keys - the keys of the cookies you want to get
	 * @returns {object} the key value association of the requested cookies 
	 */
	static get(keys){
		let parsedCookie = Cookie.parse(document.cookie);
		let subset = {};
		keys.map((key)=>{
			subset[key] = parsedCookie[key];
		});
		return subset;
	}

}