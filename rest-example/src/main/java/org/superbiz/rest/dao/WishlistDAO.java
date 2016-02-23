package org.superbiz.rest.dao;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.superbiz.rest.model.User;
import org.superbiz.rest.model.Wishlist;

@Singleton
@Lock(LockType.READ)
public class WishlistDAO {
	@Inject
	private DAO dao;
	
	
	private SecureRandom random = new SecureRandom();
	
    public List<Wishlist> list(int first, int max) {
        return dao.namedFind(Wishlist.class, "wishlist.list", first, max);
    }
	
    public List<Wishlist> listByUser(long userId) {
        User usr = dao.find(User.class, userId);
        if (usr == null) {
            throw new IllegalArgumentException("user with id " + userId + " not found");
        }
        return Collections.unmodifiableList(usr.getWishlist());
    }
    
	public Wishlist create(String title, String description, long userId){
		User usr = dao.find(User.class, userId);
		
        if (usr == null) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }
        
		Wishlist wishlist = new Wishlist();
		wishlist.setTitle(title);
		wishlist.setDescription(description);
		wishlist.setTokenAdmin(new BigInteger(130, random).toString(32));
		wishlist.setTokenGuest(new BigInteger(130, random).toString(32));
		wishlist.setCreator(usr);
			
		return dao.create(wishlist);
	}
	
	public Wishlist loadFromTokenAdmin(String tokenAdmin){
		try {
			Wishlist wl = (Wishlist) dao.findBy(Wishlist.class, "SELECT wl FROM Wishlist wl WHERE wl.tokenAdmin = :tokenAdmin", "tokenAdmin", tokenAdmin);
			return wl;
		} catch (NoResultException exception) {
			return null;
		}

	}
	
	public Wishlist loadFromTokenGuest(String tokenGuest){
		try {
			Wishlist wl = (Wishlist) dao.findBy(Wishlist.class, "SELECT wl FROM Wishlist wl WHERE wl.tokenGuest = :tokenGuest", "tokenGuest", tokenGuest);
			return wl;
		} catch( NoResultException exception){
			return null;
		}

	}

	public Wishlist find(long id) {
		return dao.find(Wishlist.class, id);
	}
	
	public void delete(long idWl, long idCreat){
		dao.find(User.class, idCreat).getWishlist().remove(dao.find(Wishlist.class, idWl));
		dao.delete(Wishlist.class, idWl);		
	}

	public Wishlist updateDescription(String title, long id, String description) {
		Wishlist wl = dao.find(Wishlist.class, id);
		
		if(wl == null) throw new IllegalArgumentException("Wishlist with id " + id + " not found");
		
		wl.setDescription(description);
		wl.setTitle(title);
		
		return dao.update(wl);
		
	}

}
