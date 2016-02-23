package org.superbiz.rest.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.superbiz.rest.model.Link;
import org.superbiz.rest.model.WishlistItem;



@Singleton
@Lock(LockType.READ)
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
		
		return dao.create(l);
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
