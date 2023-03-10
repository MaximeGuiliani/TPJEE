package myapp.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSpringNameDao {
    
    @Autowired
    SpringNameDao dao;

    @Test
    public void testNames() throws SQLException {
        dao.deleteName(100);
        dao.deleteName(200);
        dao.addName(100, "Hello");
        dao.addName(200, "Salut");
        assertEquals("Hello", dao.findName(100));
        assertEquals("Salut", dao.findName(200));
        dao.findNames().forEach(System.out::println);
    }

    @Test
    public void testErrors() throws SQLException {
        dao.deleteName(300);
        assertThrows(SQLException.class, () -> {
            dao.addName(300, "Bye");
            dao.addName(300, "Au revoir");
        });
        assertEquals("Bye", dao.findName(300));
    }
    
    @Test
    public void testUpdates() throws SQLException {
        dao.deleteName(100);
        dao.addName(100, "Hello");
        assertEquals("Hello", dao.findName(100));
        dao.updateName(100, "NameWithID100");
        assertEquals("NameWithID100", dao.findName(100));
    }
    
    // TODO : test injection 
	@Test

    public void testInjections() throws SQLException {
    	            // Test with valid input
        	dao.addName(99, "MAXXXXXXXXXX");
            int id = 1;
            String maliciousName = "'; DROP TABLE NAME; --";
            dao.updateName(id, maliciousName);
            assertEquals("MAXXXXXXXXXX", dao.findName(99));
	}
	
	
	// TODO Faites varier les deux derniers paramètres de la DataSource et étudiez l'impact sur le temps de réponse.

	
	@Test
	public void testWorks() throws Exception {
	    long debut = System.currentTimeMillis();

	    // exécution des threads
	    ExecutorService executor = Executors.newFixedThreadPool(10);
	    for (int i = 1; (i < 5); i++) {
	        executor.execute(dao::longWork);
	    }

	    // attente de la fin des threads
	    executor.shutdown();
	    executor.awaitTermination(10, TimeUnit.HOURS);

	    // calcul du temps de réponse
	    long fin = System.currentTimeMillis();
	    System.out.println("duree = " + (fin - debut) + "ms");
	}	
    

}