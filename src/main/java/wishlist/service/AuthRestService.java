package wishlist.service;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import wishlist.dao.UserDAO;
import wishlist.model.User;

@Path("users")
@Produces({"application/json","text/xml"})
public class AuthRestService {
	
	@Inject
	private UserDAO udao;
	
	@POST
    public User create(@FormParam("mail") String mail, @FormParam("name") String name) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		User user = udao.findByMail(mail);

		if(user == null){
			user = udao.create(mail, name);
		}
		return user;
    }
	

	
}
