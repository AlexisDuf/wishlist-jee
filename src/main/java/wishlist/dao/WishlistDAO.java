package wishlist.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.apache.commons.lang.RandomStringUtils;

import wishlist.model.User;
import wishlist.model.Wishlist;

@Singleton
@Lock(LockType.READ)
public class WishlistDAO {
	@Inject
	private DAO dao;
	
	@PersistenceContext(unitName = "wishlist")
    protected EntityManager em;
	
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
    
	public Wishlist create(String description, long userId){
		User usr = dao.find(User.class, userId);
		
        if (usr == null) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }
        
		Wishlist wishlist = new Wishlist();
		wishlist.setDescription(description);
		wishlist.setTokenAdmin(RandomStringUtils.randomAlphanumeric(10).toLowerCase());
		wishlist.setTokenGuest(RandomStringUtils.randomAlphanumeric(10).toLowerCase());
		wishlist.setCreator(usr);
		
		try {
			dao.create(wishlist);
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return wishlist;
	}
	
	public Wishlist loadFromTokenAdmin(String tokenAdmin){
		try {
			Wishlist wl = (Wishlist) em
					.createQuery("SELECT wl FROM Wishlist wl WHERE wl.tokenAdmin = :tokenAdmin")
					.setParameter("tokenAdmin", tokenAdmin).getSingleResult();
			return wl;
		} catch (NoResultException exception) {
			return null;
		}

	}
	
	public Wishlist loadFromTokenGuest(String tokenGuest){
		try {
			Wishlist wl = (Wishlist) em
					.createQuery("SELECT wl FROM Wishlist wl WHERE wl.tokenGuest = :tokenGuest")
					.setParameter("tokenGuest", tokenGuest).getSingleResult();
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

	public Wishlist updateDescription(long id, String description) {
		Wishlist wl = dao.find(Wishlist.class, id);
		
		if(wl == null) throw new IllegalArgumentException("Wishlist with id " + id + " not found");
		
		wl.setDescription(description);
		
		return dao.update(wl);
		
	}
	
	public Wishlist updateGuest(long id, User guest) {
		Wishlist wl = dao.find(Wishlist.class, id);
			
		if(wl == null) throw new IllegalArgumentException("Wishlist with id " + id + " not found");		
		
		if(wl.getGuest().contains(guest)) return wl;
		
		wl.addGuest(guest);		
		return dao.update(wl);
	}
}
