package org.superbiz.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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

import org.superbiz.rest.dao.CommentDAO;
import org.superbiz.rest.dao.GuestPropostionDAO;
import org.superbiz.rest.dao.LinkDAO;
import org.superbiz.rest.dao.WishlistDAO;
import org.superbiz.rest.dao.WishlistItemDAO;
import org.superbiz.rest.model.Comment;
import org.superbiz.rest.model.GuestProposition;
import org.superbiz.rest.model.Link;
import org.superbiz.rest.model.Wishlist;
import org.superbiz.rest.model.WishlistItem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


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

	private String parseAdminItems(Wishlist wishlist) {
		List<JsonObject> itemsObj = new ArrayList<>();
		JsonObject currentItemObj;
		Gson gson = new Gson();
		
		List<WishlistItem> items = wishlist.getItem();
		for (WishlistItem currentItem : items ) {
			currentItemObj = new JsonObject();
			currentItemObj.addProperty("links",  gson.toJson(currentItem.getLinks()));
			currentItemObj.addProperty("avg_price", currentItem.getAveragePrice());
			currentItemObj.addProperty("photo_link", currentItem.getPhotoLink());
			itemsObj.add(currentItemObj);
		}
		
		return itemsObj.toString();
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
		Wishlist wishList = wdao.create(title, description, user_id);		
		Gson gson = new Gson();
        return Response.ok(gson.toJson(wishList), MediaType.APPLICATION_JSON).build();
    }
	
	
	/*
	 * Get a particular wishlist by token
	 */
	@GET
    @Path("/{wishlist_token}")
    public Response showParticularWishList(@PathParam("wishlist_token") String token)  {
    	Gson gson = new Gson();
    	if(isAdmin(token)){
    		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    		return Response.ok(gson.toJson(wishlist), MediaType.APPLICATION_JSON).build();
    	}
    	else if(isGuest(token)){
    		Wishlist wishlist = wdao.loadFromTokenGuest(token);
    		return Response.ok(gson.toJson(wishlist), MediaType.APPLICATION_JSON).build();
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
    		Gson gson = new Gson();
    		wishlist = wdao.updateDescription(title, wishlist.getId(), description);
    		return Response.ok(gson.toJson(wishlist), MediaType.APPLICATION_JSON).build();
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
    		Gson gson = new Gson();
    		WishlistItem item = witemdao.create(avg_price, url_photo, wishlist.getId());
    		return Response.ok(gson.toJson(item), MediaType.APPLICATION_JSON).build();
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
    		Gson gson = new Gson();
    		WishlistItem item = witemdao.update(item_id, avg_price, url_photo);
        	return Response.status(Response.Status.OK).entity(gson.toJson(item)).build();
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
    		Gson gson = new Gson();
    		Comment comment = cmtdao.create(content, item_id, user_id);
    		return Response.ok(gson.toJson(comment), MediaType.APPLICATION_JSON).build();
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
    			Gson gson = new Gson();
        		comment = cmtdao.update(comment_id, content);
        		return Response.ok(gson.toJson(comment), MediaType.APPLICATION_JSON).build();
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
    		Gson gson = new Gson();
    		GuestProposition proposition = gpdao.create(price, item_id, user_id);
    		return Response.ok(gson.toJson(proposition), MediaType.APPLICATION_JSON).build();
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
    			Gson gson = new Gson();
    			proposition = gpdao.update(proposition_id, price);
    			return Response.ok(gson.toJson(proposition), MediaType.APPLICATION_JSON).build();
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
    		Gson gson = new Gson();
    		Link link = ldao.create(url, item_id);
    		return Response.ok(gson.toJson(link), MediaType.APPLICATION_JSON).build();
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
