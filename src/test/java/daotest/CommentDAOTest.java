package daotest;

//import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.junit.Before;
import org.junit.Test;

import wishlist.dao.CommentDAO;
import wishlist.dao.UserDAO;
import wishlist.dao.WishlistDAO;
import wishlist.dao.WishlistItemDAO;
import wishlist.model.Comment;
import wishlist.model.Wishlist;
import wishlist.model.WishlistItem;
import wishlist.service.ContainerHarness;


public class CommentDAOTest extends ContainerHarness{
	
	/*@Inject
	CommentDAO daoc;
	
	@Inject
	WishlistDAO daow;

	@Inject
	UserDAO daou;
	
	@Inject
	WishlistItemDAO daoi;

    @Before
    public void doBefore() throws NamingException {
    	daou = getEjb("UserDAO", UserDAO.class);
    	daoc = getEjb("CommentDAO", CommentDAO.class);
    	daow = getEjb("WishlistDAO", WishlistDAO.class);
    	daoi = getEjb("WishlistItemDAO", WishlistItemDAO.class);
    }

	@Test
	public void daoIsInjected() throws Exception {
		assertThat(daoc).isNotNull();
	}
	
	@Test
	public void daoItCanCreateComment() throws Exception {
		Wishlist wl = getWishlist();
		WishlistItem item = getWishlistItem(wl);
		Comment c = getComment("Test d'ajout d'un commentaire", item.getId(), wl.getCreator().getId());
		
		assertThat(c.getContent()).isEqualTo("Test d'ajout d'un commentaire");
		
		assertThat(daoi.find(item.getId()).getComments()).hasSize(1);			
		assertThat(daou.find(wl.getCreator().getId()).getComments()).hasSize(1);	
	}
	
	@Test
	public void daoItCanUpdate() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Wishlist wl = getWishlist();
		WishlistItem item = getWishlistItem(wl);
		Comment c = getComment("Test d'ajout d'un commentaire", item.getId(), wl.getCreator().getId());
		
		assertThat(c.getContent()).isEqualTo("Test d'ajout d'un commentaire");
		
		Comment c1 = daoc.update(c.getId(), "Test de modification d'un commentaire");
		assertThat(c1.getContent()).isEqualTo("Test de modification d'un commentaire");
	}
	
	@Test
	public void daoItCanDelete() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Wishlist wl = getWishlist();
		WishlistItem item = getWishlistItem(wl);
		Comment c = getComment("Test d'ajout d'un commentaire", item.getId(), wl.getCreator().getId());
		
		assertThat(c.getContent()).isEqualTo("Test d'ajout d'un commentaire");
		
		assertThat(daoi.find(item.getId()).getComments()).hasSize(1);			
		//assertThat(daou.find(wl.getCreator().getId()).getComments()).hasSize(1);	
		
		daoc.delete(c.getId(), wl.getCreator().getId(), item.getId());
		
		assertThat(daoi.find(item.getId()).getComments()).hasSize(0);			
		//assertThat(daou.find(wl.getCreator().getId()).getComments()).hasSize(0);	
		
	}

	private Wishlist getWishlist() throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
		return daow.create("test", daou.create("alexis.dufour@isen-lille.fr", "Alexis").getId());
	}
	
	private WishlistItem getWishlistItem(Wishlist wl){
		return daoi.create(10, "test.com", wl.getId());
	}
	
	private Comment getComment(String content, long itemId, long userId) {
		return daoc.create(content, itemId, userId);
	}
	
	

*/}
