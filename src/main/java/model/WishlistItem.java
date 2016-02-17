package model;
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

@Entity
@NamedQueries({
    @NamedQuery(name = "item.list", query = "select i from WishlistItem i")
})
public class WishlistItem extends DateModel{
	
	
	@NotNull
	private float averagePrice;
	
	private String photoLink;
	
	@OneToMany(targetEntity=Link.class, mappedBy = "item", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Link> links;
	
    @OneToMany(targetEntity=Comment.class, mappedBy = "item", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Comment> comments;
    
    @OneToMany(targetEntity=GuestProposition.class, mappedBy = "item", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<GuestProposition> propositions;
    
	@ManyToOne()
	@JoinColumn(name = "wishlistId")
	@Valid
	private Wishlist wishlist;	
	
	public WishlistItem() {
		links = new LinkedList<>();
		comments = new LinkedList<>();
		propositions = new LinkedList<>();
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
		wishlist.addItem(this);
		this.wishlist = wishlist;
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
