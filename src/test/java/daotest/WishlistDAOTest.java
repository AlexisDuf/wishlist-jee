/*package daotest;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ejb.EJB;
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
import wishlist.model.User;
import wishlist.model.Wishlist;
import wishlist.service.ContainerHarness;

public class WishlistDAOTest extends ContainerHarness{

	@Inject
	WishlistDAO daow;

	@Inject
	UserDAO daou;
	
	User testUser;

    @Before
    public void doBefore() throws NamingException, SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
    	daou = getEjb("UserDAO", UserDAO.class);
    	daow = getEjb("WishlistDAO", WishlistDAO.class);   	
    	
    	if(daou.findByMail("alexis.dufour@isen-lille.fr") == null){
    		testUser = daou.create("alexis.dufour@isen-lille.fr", "Alexis");
    	}
    	
    	
    }


	@Test
	public void daoItCanCreateWishlist() throws Exception {
		
		Wishlist wl = daow.create("test", testUser.getId());
		
		assertThat(daow).isNotNull();
		assertThat(wl.getDescription()).isEqualTo("test");
		assertThat(wl.getCreator().getName()).isEqualTo("Alexis");
		assertThat(daou.find(testUser.getId()).getWishlist()).hasSize(1);
		
		String tokenAdmin = wl.getTokenAdmin();
		String tokenGuest = wl.getTokenGuest();
		
		
		wl = daow.loadFromTokenAdmin(tokenAdmin);
		assertThat(daow).isNotNull();
		assertThat(wl.getDescription()).isEqualTo("test");
		assertThat(wl.getCreator().getName()).isEqualTo("Alexis");

		
		
		wl = daow.loadFromTokenGuest(tokenGuest);
		assertThat(daow).isNotNull();
		assertThat(wl.getDescription()).isEqualTo("test");
		assertThat(wl.getCreator().getName()).isEqualTo("Alexis");	
		
	}
	
	@Test
	public void daoItCanUpdateInfo() throws Exception {
		Wishlist wl = daow.create("test", testUser.getId());

		wl = daow.updateDescription(wl.getId(), "modification");
		assertThat(wl.getDescription()).isEqualTo("modification");	

	}
	
	@Test
	public void daoItCanUpdateGuest() throws Exception {
		Wishlist wl = daow.create("test", testUser.getId());
		
		wl = daow.updateGuest(wl.getId(), testUser);
		assertThat(daou.find(testUser.getId()).getParticipationToWishlist()).hasSize(1);
		assertThat(daow.find(wl.getId()).getGuest()).hasSize(1);

	}
	
	public void daoItCanDeleteWishlist() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Wishlist wl = daow.create("test", testUser.getId());
		User other = daou.create("test.dufour@isen-lille.fr", "Test");
		
		daow.updateGuest(wl.getId(), other);
		
		daow.delete(wl.getId(), testUser.getId());
		assertThat(other.getParticipationToWishlist()).hasSize(0);
		
		
	}
	


}
*/