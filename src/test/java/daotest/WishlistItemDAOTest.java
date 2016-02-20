/*package daotest;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.junit.Before;
import org.junit.Test;

import wishlist.dao.UserDAO;
import wishlist.dao.WishlistDAO;
import wishlist.dao.WishlistItemDAO;
import wishlist.model.Wishlist;
import wishlist.model.WishlistItem;
import wishlist.service.ContainerHarness;


public class WishlistItemDAOTest extends ContainerHarness{
	
	@Inject
	WishlistDAO daow;

	@Inject
	UserDAO daou;
	
	@Inject
	WishlistItemDAO daoi;

    @Before
    public void doBefore() throws NamingException {
    	daou = getEjb("UserDAO", UserDAO.class);
    	daow = getEjb("WishlistDAO", WishlistDAO.class);
    	daoi = getEjb("WishlistItemDAO", WishlistItemDAO.class);
    }

	@Test
	public void daoIsInjected() throws Exception {
		assertThat(daoi).isNotNull();
	}
	
	@Test
	public void daoItCanCreateItem() throws Exception {

		Wishlist wl = getWishlist();
		WishlistItem item = getWishlistItem(wl);
		
		assertThat(item).isNotNull();
		assertThat(item.getAveragePrice()).isEqualTo(10);
		assertThat(item.getPhotoLink()).isEqualTo("test.com");
		assertThat(daoi.find(item.getId()).getWishlist()).isNotNull();
		assertThat(daow.find(wl.getId()).getItem()).hasSize(1);	
	}
	
	@Test
	public void daoItCanUpdate() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {


		Wishlist wl = getWishlist();
		WishlistItem item = getWishlistItem(wl);
		
		assertThat(item).isNotNull();
		item = daoi.update(item.getId(), 12, "photo.com");
		
		assertThat(item.getAveragePrice()).isEqualTo(12);
		assertThat(item.getPhotoLink()).isEqualTo("photo.com");
		
	}
	
	@Test
	public void daoItCanDelete() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {


		Wishlist wl = getWishlist();
		WishlistItem item = getWishlistItem(wl);
		
		assertThat(item).isNotNull();
		daoi.delete(item.getId(), wl.getId());
		assertThat(daoi.find(item.getId())).isNull();
		
		Wishlist w1 = daow.find(wl.getId());
		assertThat(w1.getItem()).hasSize(0);		
		
		
		
	}

	private Wishlist getWishlist() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
		return daow.create("test", daou.create("alexis.dufour@isen-lille.fr", "Alexis").getId());
	}
	
	private WishlistItem getWishlistItem(Wishlist wl){
		return daoi.create(10, "test.com", wl.getId());
	}
	
	
	

}
*/