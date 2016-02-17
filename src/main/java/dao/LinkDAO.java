package dao;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import model.Link;
import model.WishlistItem;

public class LinkDAO {
	@Inject
	DAO dao;
	
    public List<Link> list(int first, int max) {
        return dao.namedFind(Link.class, "link.list", first, max);
    }
	
	public List<Link> listByItem(long itemId) {
        WishlistItem item = dao.find(WishlistItem.class, itemId);
        if (item == null) {
            throw new IllegalArgumentException("WishlistItem with id " + itemId + " not found");
        }
        return Collections.unmodifiableList(item.getLinks());
    }
	
    
	public Link create(String url, long itemId){
		WishlistItem item = dao.find(WishlistItem.class, itemId);
		
		Link l = new Link();
		l.setUrl(url);
		l.setItem(item);
		
		try {
			dao.create(l);
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return l;
	}

	public void delete(long idLink, long idItem){
		dao.find(WishlistItem.class, idItem).getLinks().remove(dao.find(Link.class, idLink));
		dao.delete(Link.class, idLink);		
	}
	
	public Link find(long id) {
		return dao.find(Link.class, id);
	}

	public Link update(long id, String url) {
		Link l = dao.find(Link.class, id);
		
		if(l == null) throw new IllegalArgumentException("Link with id " + id + " not found");
		
		l.setUrl(url);
		
		return dao.update(l);
	}
}
