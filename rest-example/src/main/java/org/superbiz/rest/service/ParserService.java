package org.superbiz.rest.service;

import java.util.Iterator;

import org.superbiz.rest.model.Comment;
import org.superbiz.rest.model.GuestProposition;
import org.superbiz.rest.model.Link;
import org.superbiz.rest.model.User;
import org.superbiz.rest.model.Wishlist;
import org.superbiz.rest.model.WishlistItem;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ParserService {
	public static JsonObject parseUser(User user){
		JsonObject json = new JsonObject();
		json.addProperty("id", user.getId());
		json.addProperty("email", user.getMail());
		json.addProperty("name", user.getName());
		JsonArray wishlists = new JsonArray();
		for (Iterator iterator = user.getWishlist().iterator(); iterator.hasNext();) {
			Wishlist currentWishlist = (Wishlist) iterator.next();
			wishlists.add(ParserService.parseWishlist(currentWishlist));
		}
		json.addProperty("wishlists", wishlists.toString());
		return json;
	}
	
	public static JsonObject parseWishlist(Wishlist wishlist){
		JsonObject json = new JsonObject();
		json.addProperty("id", wishlist.getId());
		json.addProperty("title", wishlist.getTitle());
		json.addProperty("description", wishlist.getDescription());
		json.addProperty("guest_token", wishlist.getTokenGuest());
		json.addProperty("admin_token", wishlist.getTokenAdmin());
		/*
		 * Creator
		 */
		JsonObject creatorJson = new JsonObject();
		User creator = wishlist.getCreator();
		creatorJson.addProperty("name", creator.getName());
		creatorJson.addProperty("email", creator.getMail());
		json.addProperty("creator", creatorJson.toString());
		/*
		 * Items
		 */
		JsonArray items = new JsonArray();
		for (Iterator iterator = wishlist.getItem().iterator(); iterator.hasNext();) {
			WishlistItem currentItem = (WishlistItem) iterator.next();
			items.add(ParserService.parseWishListItem(currentItem));
		}
		json.addProperty("items", items.toString());
		return json;
	}
	
	public static JsonObject parseWishListItem(WishlistItem item){
		JsonObject json = new JsonObject();
		json.addProperty("id", item.getId());
		json.addProperty("url_photo", item.getPhotoLink());
		json.addProperty("avg_price", item.getAveragePrice());
		/*
		 * Comments
		 */
		JsonArray comments = new JsonArray();
		for (Iterator iterator = item.getComments().iterator(); iterator.hasNext();) {
			Comment currentComment = (Comment) iterator.next();
			comments.add(ParserService.parseComment(currentComment));
		}
		json.addProperty("comments", comments.toString());
		/*
		 * Propositions
		 */
		JsonArray propositions = new JsonArray();
		for (Iterator iterator = item.getPropositions().iterator(); iterator.hasNext();) {
			GuestProposition currentProposition = (GuestProposition) iterator.next();
			propositions.add(ParserService.parseGuestProposition(currentProposition));
		}
		json.addProperty("propositions", propositions.toString());
		/*
		 * Links
		 */
		JsonArray links = new JsonArray();
		for (Iterator iterator = item.getLinks().iterator(); iterator.hasNext();) {
			Link currentLink = (Link) iterator.next();
			links.add(ParserService.parseLink(currentLink));
		}
		json.addProperty("links", links.toString());
		return json;
	}
	
	public static JsonObject parseComment(Comment comment){
		JsonObject json = new JsonObject();
		Gson gson = new Gson();
		json.addProperty("id", comment.getId());
		json.addProperty("content", comment.getContent());
		json.addProperty("date", gson.toJson(comment.getCreated()));
		/*
		 * Creator
		 */
		JsonObject creatorJson = new JsonObject();
		User creator = comment.getAuthor();
		creatorJson.addProperty("name", creator.getName());
		creatorJson.addProperty("email", creator.getMail());
		json.addProperty("creator", creatorJson.toString());
		return json;
	}
	
	public static JsonObject parseGuestProposition(GuestProposition gp){
		JsonObject json = new JsonObject();
		json.addProperty("id", gp.getId());
		json.addProperty("price", gp.getPrice());
		/*
		 * Creator
		 */
		JsonObject creatorJson = new JsonObject();
		User creator = gp.getGuestName();
		creatorJson.addProperty("name", creator.getName());
		creatorJson.addProperty("email", creator.getMail());
		json.addProperty("creator", creatorJson.toString());
		return json;
	}
	
	public static JsonObject parseLink(Link link){
		JsonObject json = new JsonObject();
		json.addProperty("id", link.getId());
		json.addProperty("url", link.getUrl());
		return json;
	}
}
