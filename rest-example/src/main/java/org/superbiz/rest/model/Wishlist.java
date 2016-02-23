package org.superbiz.rest.model;


import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@NamedQueries({
    @NamedQuery(name = "wishlist.list", query = "select w from Wishlist w")
})
@XmlRootElement(name = "wishlist")
public class Wishlist extends DatedModel {

	@NotNull
	@Size(min = 1)
	@Lob
	private String title;
	
	
	@NotNull
	@Size(min = 1)
	@Lob
	private String description;
	
	@NotNull
	private String tokenAdmin;
	@NotNull
	private String tokenGuest;
	
	@OneToMany(targetEntity=WishlistItem.class, mappedBy = "wishlist", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<WishlistItem> item = new LinkedList<>();
	

	/*@ManyToMany 
	@JoinTable(name="Wishlist_Guest", 
	      joinColumns=@JoinColumn(name="id"),
	      inverseJoinColumns=@JoinColumn(name="id")) */
	@ManyToMany(mappedBy="participationToWishlist", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<User> guest = new LinkedList<>();
	
	@ManyToOne
	@JoinColumn(name = "creator_id")
	@Valid
	@XmlTransient
	private User creator;
	
	public Wishlist() {
	}
	
	/********* GETTER ********/

	public String getTitle() {
		return title;
	}
	
	public User getCreator() {
		return creator;		
	}

	public List<User> getGuest() {
		return guest;
	}

	public List<WishlistItem> getItem() {
		return item;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getTokenAdmin() {
		return tokenAdmin;
	}
	
	public String getTokenGuest() {
		return tokenGuest;
	}

	/********* SETTER ********/
	
	public void setCreator(User creator) {
		this.creator = creator;
		creator.addWishlist(this);		
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTokenAdmin(String tokenAdmin) {
		this.tokenAdmin = tokenAdmin;
	}
	
	public void setTokenGuest(String tokenGuest) {
		this.tokenGuest = tokenGuest;
	}

	public void setGuest(List<User> guest) {
		this.guest = guest;
	}

	public void setItem(List<WishlistItem> item) {
		this.item = item;
	}

	/********** OTHER ***********/
	public void addItem(WishlistItem item) {
		if(!getItem().contains(item)){
			getItem().add(item);
		}		
	}
	
	public void removeItem(WishlistItem item){
		getItem().remove(item);	
	}
	
	public void addGuest(User guest){
		if (!getGuest().contains(guest)) {
			getGuest().add(guest);
		}
		if (!guest.getParticipationToWishlist().contains(this)) {
			guest.addParticpationToWishlist(this);
		}				
	}
	
	
	
}
