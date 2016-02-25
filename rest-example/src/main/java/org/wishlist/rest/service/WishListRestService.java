package org.wishlist.rest.service;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wishlist.rest.dao.CommentDAO;
import org.wishlist.rest.dao.GuestPropostionDAO;
import org.wishlist.rest.dao.LinkDAO;
import org.wishlist.rest.dao.WishlistDAO;
import org.wishlist.rest.dao.WishlistItemDAO;
import org.wishlist.rest.model.Comment;
import org.wishlist.rest.model.GuestProposition;
import org.wishlist.rest.model.Link;
import org.wishlist.rest.model.Wishlist;
import org.wishlist.rest.model.WishlistItem;

import com.google.gson.JsonObject;


@Path("/api/wishlists")
public class WishListRestService{
	
	/*
	 * Injections
	 */
	
	@EJB
	private WishlistDAO wdao;
	@EJB 
	private WishlistItemDAO witemdao;
	@EJB
	private GuestPropostionDAO gpdao;
	@EJB
	private CommentDAO cmtdao;
	@EJB
	private LinkDAO ldao;
	
	private boolean isGuest(String token){
		Wishlist wishlist = wdao.loadFromTokenGuest(token);
		if (wishlist == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	private boolean isAdmin(String token){
		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
		if (wishlist == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	

	
		/*
		 * Endpoints
		 */
	
	
	/*
	 * Create a new wishlist
	 */
	@POST
    public Response createWishList(@FormParam("description") String description, @FormParam("title") String title,
            @HeaderParam("user_id") int user_id) {
		Wishlist wishlist = wdao.create(title, description, user_id);		
		JsonObject json = ParserService.parseWishlist(wishlist);
        return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    }
	
	
	/*
	 * Get a particular wishlist by token
	 */
	@GET
    @Path("/{wishlist_token}")
    public Response showParticularWishList(@PathParam("wishlist_token") String token)  {
    	if(isAdmin(token)){
    		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    		JsonObject json = ParserService.parseWishlist(wishlist);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    	else if(isGuest(token)){
    		Wishlist wishlist = wdao.loadFromTokenGuest(token);
    		JsonObject json = ParserService.parseWishlist(wishlist);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Modify a particular wishlist, only admin can do that
     */
	@PUT
    @Path("/{wishlist_token}")
    public Response updateWishList(@PathParam("wishlist_token") String token, @FormParam("description") String description, @FormParam("title") String title){
    	// Admin can modify wishlist configurations
    	if(this.isAdmin(token)){
    		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    		wishlist = wdao.updateDescription(title, wishlist.getId(), description);
    		JsonObject json = ParserService.parseWishlist(wishlist);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    	// Guets can't modify wishlist configurations
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Delete a particular wishlist, only admin can do that
     */
	@DELETE
    @Path("/{wishlist_token}")
    public Response deleteWishList(@PathParam("wishlist_token") String token){
    	// Admin can delete wishlist
    	if(this.isAdmin(token)){
    		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    		wdao.delete(wishlist.getId(), wishlist.getCreator().getId());
    		return Response.status(Response.Status.NO_CONTENT).build();
    	}
    	// Guets can't delete wishlist
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Add item
     */
	@POST
    @Path("{wishlist_token}/items")
    public Response createItem(@PathParam("wishlist_token") String token, @FormParam("avg_price") float avg_price,
            @FormParam("url_photo") String url_photo){
    	Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    	// Guests can't add item 
    	if(wishlist == null){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can add item
    	else{
    		WishlistItem item = witemdao.create(avg_price, url_photo, wishlist.getId());
    		JsonObject json = ParserService.parseWishListItem(item);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    }
    
    
    /*
     * Modify a specific item
     */
	@PUT
    @Path("{wishlist_token}/items/{item_id}")
    public Response updateItem(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @FormParam("avg_price") float avg_price,
            @FormParam("url_photo") String url_photo){
    	Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    	// Guests can't update item 
    	if(wishlist == null){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can update item
    	else{
    		WishlistItem item = witemdao.update(item_id, avg_price, url_photo);
    		JsonObject json = ParserService.parseWishListItem(item);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    }
   
    
    /*
     * Delete a specific item 
     */
	@DELETE
    @Path("{wishlist_token}/items/{item_id}")
    public Response deleteItem(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id){
    	Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    	// Guests can't delete item 
    	if(wishlist == null){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can delete item
    	else{
    		witemdao.delete(item_id, wishlist.getId());
        	return Response.status(Response.Status.NO_CONTENT).build();
    	}
    }
    
    
    /*
     * Add comment
     */
    @POST
    @Path("{wishlist_token}/items/{item_id}/comments")
    public Response createComment(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id,@FormParam("content") String content,
            @HeaderParam("user_id") long user_id){
    	// Guest can add comment
    	if(this.isGuest(token)){
    		Comment comment = cmtdao.create(content, item_id, user_id);
    		JsonObject json = ParserService.parseComment(comment);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    	// Admin can't add comment
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    /*
     * Modify a particular comment
     */
    @PUT
    @Path("{wishlist_token}/items/{item_id}/comments/{comment_id}")
    public Response createComment(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @PathParam("comment_id") long comment_id, @FormParam("content") String content,
            @HeaderParam("user_id") long user_id){
    	// Guest can modify comment only if is owner
    	if(this.isGuest(token)){
    		Comment comment = cmtdao.find(comment_id);
    		if(user_id == comment.getAuthor().getId()){
        		comment = cmtdao.update(comment_id, content);
        		JsonObject json = ParserService.parseComment(comment);
        		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    		}
    		else{
    			return Response.status(Response.Status.FORBIDDEN).build();
    		}
    	}
    	// Admin can't modify comment
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Delete a particular comment
     */
    @DELETE
    @Path("{wishlist_token}/items/{item_id}/comments/{comment_id}")
    public Response deleteComment(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @PathParam("comment_id") long comment_id, @HeaderParam("user_id") long user_id){
    	// Guest can delete comment only if is owner
    	if(this.isGuest(token)){
    		Comment comment = cmtdao.find(comment_id);
    		if(user_id == comment.getAuthor().getId()){
        		cmtdao.delete(comment_id, user_id, item_id);
        		return Response.status(Response.Status.NO_CONTENT).build();
    		}
    		else{
    			return Response.status(Response.Status.FORBIDDEN).build();
    		}
    	}
    	// Admin can't delete comment
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }

    
    /*
     * Add proposition
     */
    @POST
    @Path("{wishlist_token}/items/{item_id}/propositions")
    public Response createProposition(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @FormParam("price") float price,
            @HeaderParam("user_id") long user_id){
    	// Guest can add proposition
    	if(this.isGuest(token)){
    		GuestProposition proposition = gpdao.create(price, item_id, user_id);
    		JsonObject json = ParserService.parseGuestProposition(proposition);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    	// Admin can't add proposition
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Modify proposition
     */
    @PUT
    @Path("{wishlist_token}/items/{item_id}/propositions/{proposition_id}")
    public Response updateProposition(@PathParam("wishlist_token") String token,@PathParam("item_id") long item_id, @PathParam("proposition_id") long proposition_id, @FormParam("price") float price,
            @HeaderParam("user_id") long user_id){
    	// Guest can update proposition only if is owner
    	if(this.isGuest(token)){
    		GuestProposition proposition = gpdao.find(proposition_id);
    		if(user_id == proposition.getGuestName().getId()){
    			proposition = gpdao.update(proposition_id, price);
    			JsonObject json = ParserService.parseGuestProposition(proposition);
        		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    		}
    		else{
    			return Response.status(Response.Status.FORBIDDEN).build();
    		}
    	}
    	// Admin can't update proposition
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Delete proposition
     */
    @Path("{wishlist_token}/items/{item_id}/propositions/{proposition_id}")
    @DELETE
    public Response deleteProposition(@PathParam("wishlist_token") String token,@PathParam("item_id") long item_id, @PathParam("proposition_id") long proposition_id,
            @HeaderParam("user_id") long user_id){
    	// Guest can delete proposition only if is owner
    	if(this.isGuest(token)){
    		GuestProposition proposition = gpdao.find(proposition_id);
    		if(user_id == proposition.getGuestName().getId()){
    			gpdao.delete(proposition_id, user_id, item_id);
    			return Response.status(Response.Status.NO_CONTENT).build();
    		}
    		else{
    			return Response.status(Response.Status.FORBIDDEN).build();
    		}
    	}
    	// Admin can't delete proposition
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }


    
    /*
     * Add item's link
     */
    @POST
    @Path("{wishlist_token}/items/{item_id}/links")
    public Response createLink(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id,@FormParam("url") String url){
    	// Guest can't add link
    	if(this.isGuest(token)){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can add link
    	else{
    		Link link = ldao.create(url, item_id);
    		JsonObject json = ParserService.parseLink(link);
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    	}
    }
    
    
    /*
     * Remove item's link
     */
    @DELETE
    @Path("{wishlist_token}/items/{item_id}/links/{link_id}")
    public Response createLink(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @PathParam("link_id") long link_id){
    	// Guest can't remove link
    	if(this.isGuest(token)){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can remove link
    	else{
    		ldao.delete(link_id, item_id);
    		return Response.status(Response.Status.NO_CONTENT).build();
    	}
    }
}
