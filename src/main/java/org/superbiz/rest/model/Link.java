package org.superbiz.rest.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
    @NamedQuery(name = "link.list", query = "select l from Link l")
})
@XmlRootElement(name = "link")
public class Link extends DatedModel{
	@NotNull
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
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
