package wishlist.model;
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

@Entity
@NamedQueries({
    @NamedQuery(name = "comment.list", query = "select c from Comment c")
})
@XmlRootElement(name = "comment")
public class Comment extends DateModel {
	
	@NotNull
	@Size(min = 1)
	@Lob
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "authorId")
	@Valid
	private User author;
	
	
	@ManyToOne
	@JoinColumn(name = "itemId")
	@Valid
	private WishlistItem item;	
	
	
	/******** GETTER ********/
	public String getContent() {
		return content;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public WishlistItem getItem() {
		return item;
	}
	
	/********* SETTER **********/
	
	public void setAuthor(User author){
		author.addComment(this);
		this.author = author;
	}
	
	public void setWishlistItem(WishlistItem item){
		item.addComment(this);
		this.item = item;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
