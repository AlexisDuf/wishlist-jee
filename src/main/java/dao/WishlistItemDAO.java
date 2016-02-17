package dao;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import model.Wishlist;
import model.WishlistItem;

public class WishlistItemDAO {

	@Inject
	DAO dao;
	
    public List<WishlistItem> list(int first, int max) {
        return dao.namedFind(WishlistItem.class, "item.list", first, max);
    }
	
	public List<WishlistItem> list(long wlId) {
        Wishlist wl = dao.find(Wishlist.class, wlId);
        if (wl == null) {
            throw new IllegalArgumentException("Wishlist with id " + wlId + " not found");
        }
        return Collections.unmodifiableList(wl.getItem());
    }
    
	public WishlistItem create(float averagePrice, String photoLink, long wlId){
		Wishlist wl = dao.find(Wishlist.class, wlId);
		
        if (wl == null) {
            throw new IllegalArgumentException("Wishlist with id " + wlId + " not found");
        }
        
		WishlistItem item = new WishlistItem();
		item.setAveragePrice(averagePrice);
		item.setPhotoLink(photoLink);	
		item.setWishlist(wl);
		
		try {
			dao.create(item);
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return item;
	}

	public void delete(long idItem, Long idW){
		dao.find(Wishlist.class, idW).getItem().remove(dao.find(WishlistItem.class, idItem));
		dao.delete(WishlistItem.class, idItem);		
	}

	public WishlistItem find(long id) {
		return dao.find(WishlistItem.class, id);
	}
	
	public WishlistItem update(long id, float averagePrice, String photoLink) {
		WishlistItem item = dao.find(WishlistItem.class, id);
		
		if(item == null) throw new IllegalArgumentException("WishlistItem with id " + id + " not found");
		
		item.setAveragePrice(averagePrice);
		item.setPhotoLink(photoLink);
		
		return dao.update(item);
		

	}
}
