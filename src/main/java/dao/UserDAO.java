package dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import model.User;

public class UserDAO {

	@Inject
	private DAO dao;
	
	@Inject
	EntityManager em;
	
	public User create(String mail, String name) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		User usr = findByMail(mail);
		
		if(usr == null){
			usr = new User();
			usr.setMail(mail);
			usr.setName(name);		

			return dao.create(usr);
		} else {
			return usr;
		}
	}
	
    public List<User> list(int first, int max) {
        return dao.namedFind(User.class, "user.list", first, max);
    }
    
    public User findByMail(String mail){

    	try{
    		User usr = (User) em
    				.createQuery("SELECT u FROM User u WHERE u.mail = :mail")
    				.setParameter("mail", mail).getSingleResult();
    		return usr;

    	}catch (NoResultException exception){

    		return null;
    	}

    }
	
	public User find(long id) {
		return dao.find(User.class, id);
	}
	
	public void delete(long id) {
		dao.delete(User.class, id);
	}
	
	public User update(long id, String mail, String name) {
		User usr = dao.find(User.class, id);
		if (usr == null) throw new IllegalArgumentException("User id" + id + "not found");
		
		usr.setMail(mail);
		usr.setName(name);
		
		return dao.update(usr);
	}
	
	 
}
