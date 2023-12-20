package parser;



import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import com.spring.springbootproject.entity.Emission;

import jakarta.persistence.EntityManager;

public class EmissionDAO {
	
	protected static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("PU");
	
	public EmissionDAO() {}
	
	public void persist(Emission e) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Emission e) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(e));
		em.getTransaction().commit();
		em.close();
	}
	
	public Emission merge(Emission e) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Emission updatedEmission = em.merge(e);
		em.getTransaction().commit();
		em.close();
		
		return updatedEmission;
	}
}
