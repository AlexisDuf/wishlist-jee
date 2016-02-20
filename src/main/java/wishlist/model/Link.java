package wishlist.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="links")
@NamedQueries({
    @NamedQuery(name = "link.list", query = "select l from links l")
})
@XmlRootElement(name = "link")
public class Link extends DateModel{
	@NotNull
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "itemId")
	@Valid
	private WishlistItem item;
	
	public Link() {
		// TODO Auto-generated constructor stub
	}
	
	
	/********* GETTER ***********/
	
	public WishlistItem getItem() {
		return item;
	}
	
	public String getUrl() {
		return url;
	}
	
	/********* SETTER *********/
	
	public void setItem(WishlistItem item) {
		item.addLink(this);
		this.item = item;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
