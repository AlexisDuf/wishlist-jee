package jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import dao.CommentDAO;
import dao.GuestPropostionDAO;
import dao.LinkDAO;
import dao.WishlistDAO;
import dao.WishlistItemDAO;
import model.Comment;
import model.GuestProposition;
import model.Link;
import model.Wishlist;
import model.WishlistItem;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Path("wishlists")
@Produces({"application/json","text/xml"})
public class WishListRestService{
	
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

	private List<JSONObject> parseAdminItems(Wishlist wishlist) throws JSONException{
		List<JSONObject> itemsObj = new ArrayList<>();
		JSONObject currentItemObj;
		Gson gson = new Gson();
		
		List<WishlistItem> items = wishlist.getItem();
		for (WishlistItem currentItem : items ) {
			currentItemObj = new JSONObject();
			currentItemObj.put("links",  gson.toJson(currentItem.getLinks()));
			currentItemObj.put("avg_price", currentItem.getAveragePrice());
			currentItemObj.put("photo_link", currentItem.getPhotoLink());
			itemsObj.add(currentItemObj);
		}
		
		return itemsObj;
	}
	
	/*
	 * Injections
	 */
	
	@Inject
	private WishlistDAO wdao;
	@Inject 
	private WishlistItemDAO witemdao;
	@Inject
	private GuestPropostionDAO gpdao;
	@Inject
	private CommentDAO cmtdao;
	@Inject
	private LinkDAO ldao;
	
		/*
		 * Endpoints
		 */
	
	
	/*
	 * Create a new wishlist
	 */
	@POST
    public Wishlist createWishList(@QueryParam("description") String description,
            @QueryParam("user_id") int user_id) {
        return wdao.create(description, user_id);
    }
	
	
	/*
	 * Get a particular wishlist by token
	 */
    @Path("{wishlist_token}")
    @GET
    public Response showParticularWishList(@PathParam("wishlist_token") String token) throws JSONException {
    	JSONObject responseObj = new JSONObject();
    	Gson gson = new Gson();
    	if(isAdmin(token)){
    		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    		List<JSONObject> itemsObj = this.parseAdminItems(wishlist);
			responseObj.put("items", itemsObj);
			responseObj.put("guests", gson.toJson(wishlist.getGuest()));
			responseObj.put("admin", gson.toJson(wishlist.getCreator()));
			responseObj.put("created_date", gson.toJson(wishlist.getCreated()));
			responseObj.put("description", gson.toJson(wishlist.getDescription()));
			return Response.status(Response.Status.OK).build();
    	}
    	else if(isGuest(token)){
    		Wishlist wishlist = wdao.loadFromTokenGuest(token);
			responseObj.put("items", gson.toJson(wishlist.getItem()));
			responseObj.put("guests", gson.toJson(wishlist.getGuest()));
			responseObj.put("admin", gson.toJson(wishlist.getCreator()));
			responseObj.put("created_date", gson.toJson(wishlist.getCreated()));
			responseObj.put("description", gson.toJson(wishlist.getDescription()));
			return Response.status(Response.Status.OK).build();
    	}
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Modify a particular wishlist, only admin can do that
     */
    @Path("{wishlist_token}")
    @PUT
    public Response updateWishList(@PathParam("wishlist_token") String token, @QueryParam("description") String description){
    	// Admin can modify wishlist configurations
    	if(this.isAdmin(token)){
    		Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    		Gson gson = new Gson();
    		wishlist = wdao.updateDescription(wishlist.getId(), description);
    		return Response.status(Response.Status.OK).entity(gson.toJson(wishlist)).build();
    	}
    	// Guets can't modify wishlist configurations
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Delete a particular wishlist, only admin can do that
     */
    @Path("{wishlist_token}")
    @DELETE
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
    @Path("{wishlist_token}/items")
    @POST
    public Response createItem(@PathParam("wishlist_token") String token, @QueryParam("avg_price") float avg_price,
            @QueryParam("url_photo") String url_photo){
    	Wishlist wishlist = wdao.loadFromTokenAdmin(token);
    	// Guests can't add item 
    	if(wishlist == null){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can add item
    	else{
    		Gson gson = new Gson();
    		WishlistItem item = witemdao.create(avg_price, url_photo, wishlist.getId());
        	return Response.status(Response.Status.OK).entity(gson.toJson(item)).build();
    	}
    }
    
    
    /*
     * Modify a specific item
     */
    @Path("{wishlist_token}/items/{item_id}")
    @PUT
    public Response updateItem(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @QueryParam("avg_price") float avg_price,
            @QueryParam("url_photo") String url_photo){
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
    @Path("{wishlist_token}/items/{item_id}")
    @DELETE
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
    @Path("{wishlist_token}/items/{item_id}/comments")
    @POST
    public Response createComment(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id,@QueryParam("content") String content,
            @QueryParam("user_id") long user_id){
    	// Guest can add comment
    	if(this.isGuest(token)){
    		Gson gson = new Gson();
    		Comment comment = cmtdao.create(content, item_id, user_id);
    		return Response.status(Response.Status.OK).entity(gson.toJson(comment)).build();
    	}
    	// Admin can't add comment
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    /*
     * Modify a particular comment
     */
    @Path("{wishlist_token}/items/{item_id}/comments/{comment_id}")
    @PUT
    public Response createComment(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @PathParam("comment_id") long comment_id, @QueryParam("content") String content,
            @QueryParam("user_id") long user_id){
    	// Guest can modify comment only if is owner
    	if(this.isGuest(token)){
    		Comment comment = cmtdao.find(comment_id);
    		if(user_id == comment.getAuthor().getId()){
    			Gson gson = new Gson();
        		comment = cmtdao.update(comment_id, content);
        		return Response.status(Response.Status.OK).entity(gson.toJson(comment)).build();
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
    @Path("{wishlist_token}/items/{item_id}/comments/{comment_id}")
    @DELETE
    public Response deleteComment(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @PathParam("comment_id") long comment_id, @QueryParam("user_id") long user_id){
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
    @Path("{wishlist_token}/items/{item_id}/propositions")
    @POST
    public Response createProposition(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id, @QueryParam("price") float price,
            @QueryParam("user_id") long user_id){
    	// Guest can add proposition
    	if(this.isGuest(token)){
    		Gson gson = new Gson();
    		GuestProposition proposition = gpdao.create(price, item_id, user_id);
    		return Response.status(Response.Status.OK).entity(gson.toJson(proposition)).build();
    	}
    	// Admin can't add proposition
    	else{
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    }
    
    
    /*
     * Modify proposition
     */
    @Path("{wishlist_token}/items/{item_id}/propositions/{proposition_id}")
    @PUT
    public Response updateProposition(@PathParam("wishlist_token") String token,@PathParam("item_id") long item_id, @PathParam("proposition_id") long proposition_id, @QueryParam("price") float price,
            @QueryParam("user_id") long user_id){
    	// Guest can update proposition only if is owner
    	if(this.isGuest(token)){
    		GuestProposition proposition = gpdao.find(proposition_id);
    		if(user_id == proposition.getGuestName().getId()){
    			Gson gson = new Gson();
    			proposition = gpdao.update(proposition_id, price);
    			return Response.status(Response.Status.OK).entity(gson.toJson(proposition)).build();
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
            @QueryParam("user_id") long user_id){
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
    @Path("{wishlist_token}/items/{item_id}/links")
    @POST
    public Response createLink(@PathParam("wishlist_token") String token, @PathParam("item_id") long item_id,@QueryParam("url") String url){
    	// Guest can't add link
    	if(this.isGuest(token)){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can add link
    	else{
    		Gson gson = new Gson();
    		Link link = ldao.create(url, item_id);
    		return Response.status(Response.Status.OK).entity(gson.toJson(link)).build();
    	}
    }
    
    
    /*
     * Remove item's link
     */
    @Path("{wishlist_token}/items/{item_id}/links/{link_id}")
    @DELETE
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

    
    /*
     * Add guest 
     */
    @Path("{wishlist_token}/guests/")
    @POST
    public Response addGuest(@PathParam("wishlist_token") String token, @QueryParam("email") String email){
    	// Guest can't add guest
    	if(this.isGuest(token)){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can add guest
    	else{
    		/*
    		 * TODO model
    		 */
    		return Response.status(Response.Status.OK).build();
    	}
    }
    
    
    /*
     * Remove guest
     */
    @Path("{wishlist_token}/guests/{user_id}")
    @POST
    public Response removeGuest(@PathParam("wishlist_token") String token, @PathParam("user_id") long user_id, @QueryParam("email") String email){
    	// Guest can't add guest
    	if(this.isGuest(token)){
    		return Response.status(Response.Status.FORBIDDEN).build();
    	}
    	// Admin can add guest
    	else{
    		/*
    		 * TODO model
    		 */
    		return Response.status(Response.Status.OK).build();
    	}
    }

}
