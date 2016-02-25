package org.wishlist.rest.model;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({
    @NamedQuery(name = "item.list", query = "select i from WishlistItem i")
})
@XmlRootElement(name = "wishlistitem")
public class WishlistItem extends DatedModel{
	
	
	@NotNull
	private float averagePrice;
	
	private String photoLink;
	
	@OneToMany(targetEntity=Link.class, mappedBy = "item", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Link> links = new LinkedList<>();
	
    @OneToMany(targetEntity=Comment.class, mappedBy = "itemComment", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Comment> comments = new LinkedList<>();
    
    @OneToMany(targetEntity=GuestProposition.class, mappedBy = "itemProposition", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<GuestProposition> propositions = new LinkedList<>();
    
	@ManyToOne
	@JoinColumn(name = "wishlist_id")
	@Valid
	@XmlTransient
	private Wishlist wishlist;	
	
	public WishlistItem() {
	}
	

	/********** GETTER *********/
	public float getAveragePrice() {
		return averagePrice;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public List<GuestProposition> getPropositions() {
		return propositions;
	}
	
	public String getPhotoLink() {
		return photoLink;
	}
	
	public Wishlist getWishlist() {
		return wishlist;
	}
	
	/******** SETTER *********/
	
	public void setAveragePrice(float averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}
	
	public void setPropositions(List<GuestProposition> propositions) {
		this.propositions = propositions;
	}
	
	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
		wishlist.addItem(this);
	}
	
	
	/******** OTHER **********/
	
	public void addComment(Comment comment) {
		getComments().add(comment);
	}
	
	public void addLink(Link link){
		getLinks().add(link);
	}

	public void addProposition(GuestProposition guestProposition) {
		getPropositions().add(guestProposition);
	}

}
