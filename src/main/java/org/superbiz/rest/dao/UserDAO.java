package org.superbiz.rest.dao;

import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.superbiz.rest.model.User;


@Singleton
@Lock(LockType.READ)
public class UserDAO {

	@Inject
    private DAO dao;	

    public User create(String mail, String name) {
    	   	
        User user = new User();
        user.setName(name);
        user.setMail(mail);
        return dao.create(user);
    }

    public List<User> list(int first, int max) {
        return dao.namedFind(User.class, "user.list", first, max);
    }

    public User find(long id) {
        return dao.find(User.class, id);
    }
    
    public User findByMail(String mail){
    	try{
    		User usr = dao.findBy(User.class, "SELECT u FROM User u WHERE u.mail = :mail", "mail", mail);
    		return usr;
    	}
    	catch(NoResultException exeption)
    	{
    		return null;
    	}
    	 
    }

    public void delete(long id) {
        dao.delete(User.class, id);
    }

    public User update(long id, String name, String mail) {
        User user = dao.find(User.class, id);
        if (user == null) {
            throw new IllegalArgumentException("setUser id " + id + " not found");
        }
        user.setName(name);
        user.setMail(mail);
        return dao.update(user);
    }
	
	 
}
