package org.superbiz.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.Json;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.superbiz.rest.dao.UserDAO;
import org.superbiz.rest.model.User;

import com.google.gson.Gson;
import com.google.gson.JsonObject;




@Path("/api/users")
public class AuthRestService {
	
	@EJB
	private UserDAO udao;
	
	@POST
	@Path("/create")
    public Response create(@FormParam("email") String email, @FormParam("name") String name) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		User user = udao.findByMail(email);
		if(user == null){
			user = udao.create(email, name);
		}
		Gson gson = new Gson();
		return Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
    }
	
    
	@GET
    @Path("/list")
    public Response list(@FormParam("first") @DefaultValue("0") int first,
    						@FormParam("max") @DefaultValue("20") int max) {
        List<User> users = udao.list(first, max);
        Gson gson = new Gson();
        return Response.ok(gson.toJson(users), MediaType.APPLICATION_JSON).build();
    }
	
}
