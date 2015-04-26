package es.upm.dit.isst.voto2015.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.voto2015.model.User;

public class VotoDAOImpl implements VotoDAO {
	
	private static VotoDAOImpl instance;
	
	private VotoDAOImpl(){
	}
	
	public static VotoDAOImpl getInstance(){
		if(instance == null)
			instance = new VotoDAOImpl();
		return instance;
	}
	
	@Override
	public List<User> listUsers() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from User m");
		List<User> Users = q.getResultList();
		return Users;
	}

	@Override
	public void add(int dni, String nombre, String apellidos, int sector,
			int ponderacion) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager(); 
			User User = new User(dni, nombre, apellidos, sector, ponderacion, false); 
			em.persist(User);
			em.close();
		}
	}
	
	public void updateUser(User user)
	{
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager(); 
			em.persist(user);
			em.close();
		}
	}

	@Override
	public List<User> getUsers(int dni) {
		EntityManager em = EMFService.get().createEntityManager(); 
		Query q = em.createQuery("select u from User u where u.dni = :dni");
		q.setParameter("dni", dni); 
		List<User> Users = q.getResultList(); 
		em.close();
		return Users;
	}

	@Override
	public void remove(int dni) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			User User = em.find(User.class, dni);
			em.remove(User); } 
		finally {
			em.close();
			}
	}
	
}
