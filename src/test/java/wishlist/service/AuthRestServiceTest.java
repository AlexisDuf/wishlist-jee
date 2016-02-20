package wishlist.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.junit.Test;

import wishlist.dao.UserDAO;
import wishlist.model.User;

public class AuthRestServiceTest extends ContainerHarness {
   /* @Test
    public void itCanCreateAUser() throws NamingException, SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

        final UserDAO dao = getEjb("UserDAO", UserDAO.class);
        final AuthServiceClientAPI client = JAXRSClientFactory.create(
                getServiceURI(), AuthServiceClientAPI.class);     
        
        User usr = new User();
        usr.setMail("ad@isen.fr");
        usr.setName("Ale");
        User user = client.create(usr);

        user = dao.find(user.getId());
        assertThat(user.getMail()).isEqualTo("ad@isen.fr");

        dao.delete(user.getId());

    }
    
    @Path("/api/users")
    @Produces({ "text/xml", "application/json" })
    public static interface AuthServiceClientAPI {

        @POST
        public User create(User usr);

    }

 */
}
