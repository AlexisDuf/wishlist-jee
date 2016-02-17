package model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({
    @NamedQuery(name = "guestprop.list", query = "select g from GuestProposition g")
})
public class GuestProposition extends DateModel{
	
	private float price;
	
	@ManyToOne
	@JoinColumn(name = "guestId")
	@Valid
	@XmlTransient
	private User guestName;
	
	
	@ManyToOne
	@JoinColumn(name = "itemId")
	@Valid
	@XmlTransient
	private WishlistItem item;	

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
		return item;
	}
	
	/******* SETTER *******/
	public void setGuestName(User guestName) {
		guestName.addProposition(this);
		this.guestName = guestName;
	}
	
	public void setItem(WishlistItem item) {
		item.addProposition(this);
		this.item = item;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	

}
