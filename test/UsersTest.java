import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.shirtstore.entity.Users;

public class UsersTest {

	public static void main(String[] args) {
		Users user1 = new Users();
		user1.setEmail("robotabc@gmail.com");
		user1.setFullName("The machine");
		user1.setPassword("123456");
		
		System.out.println("Error");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ShoeStoreWebsite");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
			
		entityManager.getTransaction().begin();
		entityManager.persist(user1);
			
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		
		System.out.println("A Users object was persisted");
	}

}
