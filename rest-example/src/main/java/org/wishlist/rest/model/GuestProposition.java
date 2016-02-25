package org.wishlist.rest.model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({
    @NamedQuery(name = "guestprop.list", query = "select g from GuestProposition g")
})
@XmlRootElement(name = "guestproposition")
public class GuestProposition extends DatedModel{
	
	private float price;
	
	@ManyToOne
	@JoinColumn(name = "guest_id")
	@Valid
	@XmlTransient
	private User guestName;
	
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	@Valid
	@XmlTransient
	private WishlistItem itemProposition;	

	public GuestProposition() {
	}

	/****** GETTER *******/
	public float getPrice() {
		return price;
	}
	
	public User getGuestName() {
		return guestName;
	}
	
	public WishlistItem getItem() {
		return itemProposition;
	}
	
	/******* SETTER *******/
	public void setGuestName(User guestName) {
		guestName.addProposition(this);
		this.guestName = guestName;
	}
	
	public void setItem(WishlistItem item) {
		item.addProposition(this);
		this.itemProposition = item;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	

}
