package org.superbiz.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


import org.superbiz.rest.dao.UserDAO;
import org.superbiz.rest.model.User;

import com.google.gson.JsonObject;




@Path("/api/users")
@Produces({"text/xml"})
public class AuthRestService {
	
	@EJB
	private UserDAO udao;
	
	@Path("/create")
	@PUT
    public String create(@QueryParam("mail") String mail, @QueryParam("name") String name) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		User user = udao.findByMail(mail);
		if(user == null){
			user = udao.create(mail, name);
		}
		JsonObject usr = new JsonObject();
		usr.addProperty("id", user.getId());
		usr.addProperty("mail", user.getMail());
		usr.addProperty("name", user.getName());
		return usr.toString();
    }
	
    
    
    @Path("/list")
    @GET
    public List<User> list(@QueryParam("first") @DefaultValue("0") int first,
                           @QueryParam("max") @DefaultValue("20") int max) {
        return udao.list(first, max);
    }
    
    
	
/*	@POST
    public User create(User usr) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		User user = udao.findByMail(usr.getMail());

		if(user == null){
			user = udao.create(usr.getMail(), usr.getName());
		}
		return user;
    }*/
	

	
}
