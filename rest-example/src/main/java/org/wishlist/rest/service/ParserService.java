package org.wishlist.rest.service;

import java.util.Iterator;

import org.wishlist.rest.model.Comment;
import org.wishlist.rest.model.GuestProposition;
import org.wishlist.rest.model.Link;
import org.wishlist.rest.model.User;
import org.wishlist.rest.model.Wishlist;
import org.wishlist.rest.model.WishlistItem;

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
		json.add("wishlists", wishlists);
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
		json.add("creator", creatorJson);
		/*
		 * Items
		 */
		JsonArray items = new JsonArray();
		for (Iterator iterator = wishlist.getItem().iterator(); iterator.hasNext();) {
			WishlistItem currentItem = (WishlistItem) iterator.next();
			items.add(ParserService.parseWishListItem(currentItem));
		}
		json.add("items", items);
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
		json.add("comments", comments);
		/*
		 * Propositions
		 */
		JsonArray propositions = new JsonArray();
		for (Iterator iterator = item.getPropositions().iterator(); iterator.hasNext();) {
			GuestProposition currentProposition = (GuestProposition) iterator.next();
			propositions.add(ParserService.parseGuestProposition(currentProposition));
		}
		json.add("propositions", propositions);
		/*
		 * Links
		 */
		JsonArray links = new JsonArray();
		for (Iterator iterator = item.getLinks().iterator(); iterator.hasNext();) {
			Link currentLink = (Link) iterator.next();
			links.add(ParserService.parseLink(currentLink));
		}
		json.add("links", links);
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
		json.add("creator", creatorJson);
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
		json.add("creator", creatorJson);
		return json;
	}
	
	public static JsonObject parseLink(Link link){
		JsonObject json = new JsonObject();
		json.addProperty("id", link.getId());
		json.addProperty("url", link.getUrl());
		return json;
	}
}
