import javax.ejb.embeddable.EJBContainer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wishlist.rest.dao.CommentDAO;
import org.wishlist.rest.dao.GuestPropostionDAO;
import org.wishlist.rest.dao.LinkDAO;
import org.wishlist.rest.dao.UserDAO;
import org.wishlist.rest.dao.WishlistDAO;
import org.wishlist.rest.dao.WishlistItemDAO;
import org.wishlist.rest.model.Comment;
import org.wishlist.rest.model.GuestProposition;
import org.wishlist.rest.model.Link;
import org.wishlist.rest.model.User;
import org.wishlist.rest.model.Wishlist;
import org.wishlist.rest.model.WishlistItem;

import static org.assertj.core.api.Assertions.assertThat;


public class ItemDaoTest {
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
	public void daoItCanAddLink() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	final WishlistItemDAO widao = (WishlistItemDAO) container.getContext().lookup("java:global/rest-example/WishlistItemDAO");
    	final LinkDAO ldao = (LinkDAO) container.getContext().lookup("java:global/rest-example/LinkDAO");
    	User testUser = udao.create("dufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("Test", "update test", testUser.getId());		
		
		WishlistItem item = widao.create(50, "oLink", wl.getId());
		item = widao.find(item.getId());
		
		assertThat(item).isNotNull();
		assertThat(item.getAveragePrice()).isEqualTo(50);
		assertThat(item.getPhotoLink()).isEqualTo("oLink");
		assertThat(widao.find(item.getId()).getWishlist()).isNotNull();
		assertThat(wdao.find(wl.getId()).getItem()).hasSize(1);	
		
		Link link1 = ldao.create("url1", item.getId());
		
		link1 = ldao.find(link1.getId());
		
		assertThat(link1.getUrl()).isEqualTo("url1");
		
		assertThat(widao.find(item.getId()).getLinks()).hasSize(1);
		assertThat(ldao.find(link1.getId()).getItem()).isNotNull();
	}
    
    
    @Test
	public void daoItCanAddComment() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	final WishlistItemDAO widao = (WishlistItemDAO) container.getContext().lookup("java:global/rest-example/WishlistItemDAO");
    	final CommentDAO cdao = (CommentDAO) container.getContext().lookup("java:global/rest-example/CommentDAO");
    	User testUser = udao.create("dufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("Test", "update test", testUser.getId());		
		
		WishlistItem item = widao.create(50, "oLink", wl.getId());
		item = widao.find(item.getId());
		
		assertThat(item).isNotNull();
		assertThat(item.getAveragePrice()).isEqualTo(50);
		assertThat(item.getPhotoLink()).isEqualTo("oLink");
		assertThat(widao.find(item.getId()).getWishlist()).isNotNull();
		assertThat(wdao.find(wl.getId()).getItem()).hasSize(1);	
		
		Comment c1 = cdao.create("test comment", item.getId(), testUser.getId());
		
		c1 = cdao.find(c1.getId());
		
		assertThat(c1.getContent()).isEqualTo("test comment");
		
		assertThat(widao.find(item.getId()).getComments()).hasSize(1);
		assertThat(udao.find(testUser.getId()).getComments()).hasSize(1);
		assertThat(cdao.find(c1.getId()).getItem()).isNotNull();
		assertThat(cdao.find(c1.getId()).getAuthor()).isNotNull();

	}
    
    
    @Test
	public void daoItCanAddProposition() throws Exception {
		final UserDAO udao = (UserDAO) container.getContext().lookup("java:global/rest-example/UserDAO");
    	final WishlistDAO wdao = (WishlistDAO) container.getContext().lookup("java:global/rest-example/WishlistDAO");
    	final WishlistItemDAO widao = (WishlistItemDAO) container.getContext().lookup("java:global/rest-example/WishlistItemDAO");
    	final GuestPropostionDAO gdao = (GuestPropostionDAO) container.getContext().lookup("java:global/rest-example/GuestPropostionDAO");
    	User testUser = udao.create("dufour@isen-lille.fr", "Alexis");
		Wishlist wl = wdao.create("Test","update test", testUser.getId());		
		
		WishlistItem item = widao.create(50, "oLink", wl.getId());
		item = widao.find(item.getId());
		
		assertThat(item).isNotNull();
		assertThat(item.getAveragePrice()).isEqualTo(50);
		assertThat(item.getPhotoLink()).isEqualTo("oLink");
		assertThat(widao.find(item.getId()).getWishlist()).isNotNull();
		assertThat(wdao.find(wl.getId()).getItem()).hasSize(1);	
		
		
		GuestProposition g1 = gdao.create(10, item.getId(), testUser.getId());
		g1 = gdao.find(g1.getId());
		
		assertThat(g1.getPrice()).isEqualTo(10);
		
		assertThat(widao.find(item.getId()).getPropositions()).hasSize(1);
		assertThat(udao.find(testUser.getId()).getPropositions()).hasSize(1);
		assertThat(gdao.find(g1.getId()).getItem()).isNotNull();
		assertThat(gdao.find(g1.getId()).getGuestName()).isNotNull();

	}
    
    
}
