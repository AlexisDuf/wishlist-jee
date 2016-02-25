package org.wishlist.rest.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.wishlist.rest.model.GuestProposition;
import org.wishlist.rest.model.User;
import org.wishlist.rest.model.WishlistItem;



@Singleton
@Lock(LockType.READ)
public class GuestPropostionDAO {

	@Inject
	DAO dao;
	
    public List<GuestProposition> list(int first, int max) {
        return dao.namedFind(GuestProposition.class, "guestprop.list", first, max);
    }
	
	public List<GuestProposition> listByItem(long itemId) {
        WishlistItem item = dao.find(WishlistItem.class, itemId);
        if (item == null) {
            throw new IllegalArgumentException("WishlistItem with id " + itemId + " not found");
        }
        return Collections.unmodifiableList(item.getPropositions());
    }
	
	public List<GuestProposition> listByUser(long userId) {
        User usr = dao.find(User.class, userId);
        if (usr == null) {
            throw new IllegalArgumentException("user with id " + userId + " not found");
        }
        return Collections.unmodifiableList(usr.getPropositions());
    }
    
	public GuestProposition create(float price, long itemId, long userId){
		User usr = dao.find(User.class, userId);
		WishlistItem item = dao.find(WishlistItem.class, itemId);
		
		GuestProposition gprop = new GuestProposition();
		gprop.setPrice(price);		
		gprop.setGuestName(usr);
		gprop.setItem(item);	

		return dao.create(gprop);
	}

	public GuestProposition find(long id) {
		return dao.find(GuestProposition.class, id);
	}
	
	public void delete(long idProp, long idUsr, long idItem){
		dao.find(User.class, idUsr).getPropositions().remove(dao.find(GuestProposition.class, idProp));
		dao.find(WishlistItem.class, idItem).getPropositions().remove(dao.find(GuestProposition.class, idItem));

		dao.delete(GuestProposition.class, idProp);		
	}

	public GuestProposition update(long id, float price) {
		GuestProposition gprop = dao.find(GuestProposition.class, id);
		
		if(gprop == null) throw new IllegalArgumentException("GuestProposition with id " + id + " not found");
		
		gprop.setPrice(price);
		
		return dao.update(gprop);

	}
}
