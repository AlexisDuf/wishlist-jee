package org.wishlist.rest.service;

import javax.ejb.EJB;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wishlist.rest.dao.UserDAO;
import org.wishlist.rest.model.User;

import com.google.gson.JsonObject;




@Path("/api/users")
public class AuthRestService {
	
	@EJB
	private UserDAO udao;
	
	@POST
    public Response create(@FormParam("email") String email, @FormParam("name") String name) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		User user = udao.findByMail(email);
		if(user == null){
			user = udao.create(email, name);
		}
		JsonObject json = ParserService.parseUser(user);
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON)
				.header("Access-Control-Allow-Origin", "*")
				.build();
    }
	
}
