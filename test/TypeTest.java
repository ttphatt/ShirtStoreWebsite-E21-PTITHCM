import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.shirtstore.entity.Type;

public class TypeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Type newType = new Type();
		newType.setTypeName("Slip-on");
		
		System.out.println("Error");
		
		try {
			System.out.println("Error 1");
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ShoeStoreWebsite");
			System.out.println("Error 2");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			
			entityManager.getTransaction().begin();
			entityManager.persist(newType);
			
			entityManager.getTransaction().commit();
			entityManager.close();
			entityManagerFactory.close();
		} catch(Exception exception){
		     System.out.println("Problem creating session factory");
		     exception.printStackTrace(); 
		}
		
		System.out.println("A Users object was persisted");
	}

}
