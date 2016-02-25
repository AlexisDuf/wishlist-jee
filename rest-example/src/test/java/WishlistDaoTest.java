
import javax.ejb.embeddable.EJBContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wishlist.rest.dao.UserDAO;
import org.wishlist.rest.dao.WishlistDAO;
import org.wishlist.rest.dao.WishlistItemDAO;
import org.wishlist.rest.model.User;
import org.wishlist.rest.model.Wishlist;
import org.wishlist.rest.model.WishlistItem;

import static org.assertj.core.api.Assertions.assertThat;


public class WishlistDaoTest {
    private static EJBContainer container;
    

    @BeforeClass
    public static void start() throws Exception {
        container = EJBContainer.createEJBContainer();
    }

    @AfterClass
    public static void stop() {
        if (container != null) {
            container.close();
        }
    }
    
    
    @Test
	public void daoItCanCreateWishlist() throws Exception {
    	final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
		User testUser = udao.create("test.dufour@isen-lille.fr", "Test");
		Wishlist wl = wdao.create("Test","test", testUser.getId());
		
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
	
	@Test
	public void daoItCanUpdateInfo() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	User testUser = udao.create("dufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("Test","update test", testUser.getId());		
		
		wdao.updateDescription("Test", wl.getId(), "modification");
		
		assertThat(wdao.find(wl.getId()).getDescription()).isEqualTo("modification");
	}
	
	
	@Test
	public void daoItCanAddItem() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	final WishlistItemDAO widao = (WishlistItemDAO) container.getContext().lookup("java:global/rest-example/WishlistItemDAO");
		
    	
    	User testUser = udao.create("dufor@isen-lille.fr", "Test");
		Wishlist wl = wdao.create("Test", "test", testUser.getId());	
		
		WishlistItem item = widao.create(50, "oLink", wl.getId());
		item = widao.find(item.getId());
		assertThat(widao.find(item.getId()).getWishlist()).isNotNull();
		assertThat(wdao.find(wl.getId()).getItem()).hasSize(1);
		
		WishlistItem item2 = widao.create(50, "oLink", wl.getId());
		item2 = widao.find(item2.getId());
		assertThat(widao.find(item2.getId()).getWishlist()).isNotNull();
		assertThat(wdao.find(wl.getId()).getItem()).hasSize(2);
	}
	
	
	@Test
	public void daoItCanDeleteWishlist() throws Exception {
    	final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
		
    	User testUser = udao.create("aufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("Test", "update test", testUser.getId());	
		
		wdao.delete(wl.getId(), testUser.getId());
		assertThat(wdao.find(wl.getId())).isNull();		
		assertThat(udao.find(testUser.getId()).getWishlist()).hasSize(0);		
		
	}
}
