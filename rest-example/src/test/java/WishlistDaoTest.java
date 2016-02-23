
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.superbiz.rest.dao.UserDAO;
import org.superbiz.rest.dao.WishlistDAO;
import org.superbiz.rest.dao.WishlistItemDAO;
import org.superbiz.rest.model.User;
import org.superbiz.rest.model.Wishlist;
import org.superbiz.rest.model.WishlistItem;

import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;


public class WishlistDaoTest {
    private static EJBContainer container;
    
    //private static UserDAO udao;
    //private static WishlistDAO wdao;

    @BeforeClass
    public static void start() throws Exception {
        container = EJBContainer.createEJBContainer();
       // udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    //	wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    }

    @AfterClass
    public static void stop() {
        if (container != null) {
            container.close();
        }
    }
    
    
    
	public void daoItCanCreateWishlist() throws Exception {
    	final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
		User testUser = udao.create("test.dufour@isen-lille.fr", "Test");
		Wishlist wl = wdao.create("test", testUser.getId());
		
		wl = wdao.find(wl.getId());
		assertThat(wdao).isNotNull();
		assertThat(wl.getDescription()).isEqualTo("test");
		assertThat(wl.getCreator().getName()).isEqualTo("Test");
		assertThat(udao.find(testUser.getId()).getWishlist()).hasSize(1);
		
		String tokenAdmin = wl.getTokenAdmin();
		String tokenGuest = wl.getTokenGuest();
		
		
		wl = wdao.loadFromTokenAdmin(tokenAdmin);
		assertThat(wdao).isNotNull();
		assertThat(wl.getDescription()).isEqualTo("test");
		assertThat(wl.getCreator().getName()).isEqualTo("Test");
		
		
		wl = wdao.loadFromTokenGuest(tokenGuest);
		assertThat(wdao).isNotNull();
		assertThat(wl.getDescription()).isEqualTo("test");
		assertThat(wl.getCreator().getName()).isEqualTo("Test");	
		
	}
	
	
	public void daoItCanUpdateInfo() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	User testUser = udao.create("dufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("update test", testUser.getId());		
		
		wdao.updateDescription(wl.getId(), "modification");
		
		assertThat(wdao.find(wl.getId()).getDescription()).isEqualTo("modification");
	}
	
	
	public void daoItCanUpdateGuest() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	
    	User testUser = udao.create("test@isen-lille.fr", "Alex");
		User testGuest = udao.create("dufour@lille.fr", "Dufour");
		assertThat(udao.find(testGuest.getId())).isNotNull();
		
		Wishlist wl = wdao.create("update test", testUser.getId());	
		assertThat(wdao.find(wl.getId()).getDescription()).isEqualTo("update test");
		
		wl = wdao.updateGuest(wl.getId(), testGuest.getId());
		assertThat(wdao.find(wl.getId()).getGuest()).isNotNull();

		//assertThat(wdao.find(wl.getId()).getGuest().get(0).getName()).isEqualTo("Dufour");
	}
	
	@Test
	public void daoItCanUpdateItem() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	final WishlistItemDAO widao = (WishlistItemDAO) container.getContext().lookup("java:global/rest-example/WishlistItemDAO");
		
    	
    	User testUser = udao.create("dufor@isen-lille.fr", "Test");
		Wishlist wl = wdao.create("test", testUser.getId());	
		
		WishlistItem item = widao.create(50, "oLink", wl.getId());
		item = widao.find(item.getId());
		assertThat(wdao.find(wl.getId()).getItem()).isNotNull();
		
		//assertThat(wdao.find(wl.getId()).getGuest().get(0).getName()).isEqualTo("DUfour");
	}
	
	
	@Test
	public void daoItCanDeleteWishlist() throws Exception {
    	final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
		
    	User testUser = udao.create("aufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("update test", testUser.getId());	
		
		wdao.delete(wl.getId(), testUser.getId());
		assertThat(wdao.find(wl.getId())).isNull();		
		assertThat(udao.find(testUser.getId()).getWishlist()).hasSize(0);		
		
	}
}
