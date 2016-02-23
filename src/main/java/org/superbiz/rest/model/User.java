package org.superbiz.rest.model;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;




@Entity
@NamedQueries({
    @NamedQuery(name = "user.list", query = "select u from User u"),
    @NamedQuery(name = "user.findByMail", query = "SELECT u FROM User u WHERE u.mail = :mail")
})
@XmlRootElement(name = "user")
public class User extends DatedModel{
	
	@NotNull
	private String mail;
	
	@ManyToMany(targetEntity = Wishlist.class, cascade=CascadeType.ALL)
	List<Wishlist> participationToWishlist;
	
    @OneToMany(targetEntity=Wishlist.class, mappedBy="creator", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Wishlist> wishlists = new LinkedList<>();
    
    @OneToMany(targetEntity=GuestProposition.class, mappedBy = "guestName", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<GuestProposition> propositions = new LinkedList<>();
    
    @OneToMany(targetEntity=Comment.class, mappedBy = "author", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Comment> comments = new LinkedList<>();
	
	@NotNull
	@Size(min =1)
	private String name;

	
	/******** GETTER **********/
	public String getMail() {
		return mail;
	}

	public String getName() {
		return name;
	}
	

	public List<Wishlist> getWishlist() {
		return wishlists;
	}
	
	public List<GuestProposition> getPropositions() {
		return propositions;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public List<Wishlist> getParticipationToWishlist() {
		return participationToWishlist;
	}
	
	/********** SETTER *********/

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWishlist(List<Wishlist> wishlist) {
		this.wishlists = wishlist;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void setPropositions(List<GuestProposition> propositions) {
		this.propositions = propositions;
	}
	
	public void setParticipationToWishlist(List<Wishlist> participationToWishlist) {
		this.participationToWishlist = participationToWishlist;
	}

	
	/********* OTHER ********/
	public void addWishlist(Wishlist wishlist) {
		getWishlist().add(wishlist);
	}
	
	public void addParticpationToWishlist(Wishlist wl){
		getParticipationToWishlist().add(wl);
	}
	
	
	public void addComment(Comment comment) {
		getComments().add(comment);		
	}

	public void addProposition(GuestProposition guestProposition) {
		getPropositions().add(guestProposition);
	}
}
