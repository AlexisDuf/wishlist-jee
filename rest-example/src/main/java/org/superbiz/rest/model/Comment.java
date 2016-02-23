package org.superbiz.rest.model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({
    @NamedQuery(name = "comment.list", query = "select c from Comment c")
})
@XmlRootElement(name = "comment")
public class Comment extends DatedModel {
	
	@NotNull
	@Size(min = 1)
	@Lob
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	@Valid
	@XmlTransient
	private User author;
	
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	@Valid
	@XmlTransient
	private WishlistItem itemComment;	
	
	
	/******** GETTER ********/
	public String getContent() {
		return content;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public WishlistItem getItem() {
		return itemComment;
	}
	
	/********* SETTER **********/
	
	public void setAuthor(User author){
		author.addComment(this);
		this.author = author;
	}
	
	public void setWishlistItem(WishlistItem item){
		item.addComment(this);
		this.itemComment = item;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
