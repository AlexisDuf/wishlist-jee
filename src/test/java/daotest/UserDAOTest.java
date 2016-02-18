package daotest;

import org.junit.Test;

import wishlist.dao.UserDAO;
import wishlist.model.User;
import wishlist.service.ContainerHarness;
import static org.assertj.core.api.Assertions.assertThat;

import javax.naming.NamingException;


public class UserDAOTest extends ContainerHarness{
	
	
    @Test
    public void itCanCreateAUser() throws Exception{    	
		UserDAO dao = getUserDAO();
    	User testUser = dao.create("alexis.dufour@isen-lille.fr", "Alexis");
    	assertThat(testUser).isNotNull();
    	assertThat(testUser.getMail()).isEqualTo("alexis.dufour@isen-lille.fr");
    	assertThat(testUser.getName()).isEqualTo("Alexis");	    			
    }
    
    private UserDAO getUserDAO() throws NamingException {
        final UserDAO udao = (UserDAO) container.getContext().lookup(
                "java:global/wishlist-jee/UserDAO");
        return udao;
    }
}
