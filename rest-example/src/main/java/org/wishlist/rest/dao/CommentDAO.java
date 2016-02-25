package org.wishlist.rest.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.wishlist.rest.model.Comment;
import org.wishlist.rest.model.User;
import org.wishlist.rest.model.WishlistItem;



@Singleton
@Lock(LockType.READ)
public class CommentDAO {

	@Inject
	DAO dao;
	
    public List<Comment> list(int first, int max) {
        return dao.namedFind(Comment.class, "comment.list", first, max);
    }
	
	public List<Comment> listByItem(long itemId) {
        WishlistItem item = dao.find(WishlistItem.class, itemId);
        if (item == null) {
            throw new IllegalArgumentException("WishlistItem with id " + itemId + " not found");
        }
        return Collections.unmodifiableList(item.getComments());
    }
	
	public List<Comment> listByUser(long userId) {
        User usr = dao.find(User.class, userId);
        if (usr == null) {
            throw new IllegalArgumentException("user with id " + userId + " not found");
        }
        return Collections.unmodifiableList(usr.getComments());
    }
    
	public Comment create(String content, long itemId, long userId){
		User usr = dao.find(User.class, userId);
		WishlistItem item = dao.find(WishlistItem.class, itemId);
		
		Comment c = new Comment();
		c.setContent(content);		
		c.setAuthor(usr);
		c.setWishlistItem(item);		

		return dao.create(c);
	}
	
	public Comment find(long id) {
		return dao.find(Comment.class, id);
	}

	public void delete(long idCom, long idAuthor, long idItem){
		dao.find(User.class, idAuthor).getComments().remove(dao.find(Comment.class, idCom));
		dao.find(WishlistItem.class, idItem).getComments().remove(dao.find(Comment.class, idCom));
		
		dao.delete(Comment.class, idCom);		
	}

	public Comment update(long id, String content) {
		Comment c = dao.find(Comment.class, id);
		
		if(c == null) throw new IllegalArgumentException("Comment with id " + id + " not found");
		
		c.setContent(content);
		
		return dao.update(c);
	}
}
