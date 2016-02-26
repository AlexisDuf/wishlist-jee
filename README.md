#__Title: Wishlist JEE REST API__
 
*DELMAIRE Matthieu - DUFOUR Alexis -  WASSON Paul*


### HOW RUN PROJECT

* Go to rest-example folder: mvn install 
* Then: mvn tomee:run 

### END POINT

* REST Application: http://localhost:8080/wishlist-jee/                                                                              
    * Service URI: http://localhost:8080/wishlist-jee/api/users -> Pojo org.wishlist.rest.service.AuthRestService     
        * POST http://localhost:8080/wishlist-jee/api/users/create -> Response create(String, String) 
    * Service URI: http://localhost:8080/wishlist-jee/api/wishlists -> Pojo org.wishlist.rest.service.WishListRestService
        * DELETE http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token} -> Response deleteWishList(String)                            
        * DELETE http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id} ->  Response deleteItem(String, long)                          
        * DELETE http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/comments/{comment_id}  ->  Response deleteComment(String, long, long, long)           
        * DELETE http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/links/{link_id}  -> Response createLink(String, long, long)                    
        * DELETE http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/propositions/{proposition_id} -> Response deleteProposition(String, long, long, long)       
        * GET http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token} -> Response showParticularWishList(String)                    
        * POST http://localhost:8080/wishlist-jee/api/wishlists/ -> Response createWishList(String, String, int)               
        * POST http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items  ->  Response createItem(String, float, String)                 
        * POST http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/comments  ->  Response createComment(String, long, String, long)         
        * POST http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/links  ->  Response createLink(String, long, String)                  
        * POST http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/propositions  ->  Response createProposition(String, long, float, long)      
        * PUT http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}  ->   Response updateWishList(String, String, String)            
        * PUT http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}   ->  Response updateItem(String, long, float, String)           
        * PUT http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/comments/{comment_id}  ->   Response createComment(String, long, long, String, long)   
        * PUT http://localhost:8080/wishlist-jee/api/wishlists/{wishlist_token}/items/{item_id}/propositions/{proposition_id}  ->  Response updateProposition(String, long, long, float, long)