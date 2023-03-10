package myapp.jpa;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hsqldb.HsqlException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myapp.jpa.dao.JpaDao;
import myapp.jpa.model.Address;
import myapp.jpa.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestJpaDao {

    @Autowired
    JpaDao dao;

    @Test
    public void addAndFindPerson() {
        // Création
        var p1 = new Person("Jean", null);
        p1 = dao.addPerson(p1);
        assertTrue(p1.getId() > 0);
        // relecture
        var p2 = dao.findPerson(p1.getId());
        assertEquals("Jean", p2.getFirstName());
        assertEquals(p1.getId(), p2.getId());
    }


    @Test
    public void testNullName() {
        // Création
        var p1 = new Person(null, null);

        assertThrows(Exception.class,
                     ()->dao.addPerson(p1)
                    );
    }

    @Test
    public void testUpdatePerson() {
        var p1 = new Person("damien", null);
        dao.addPerson(p1);
        p1.setFirstName("maxime");

        dao.updatePerson(p1);

        Person p2 = dao.findPerson(p1.getId());
        assertEquals("maxime", p2.getFirstName());
    }

    @Test
    public void testRemovePerson() {
        var p1 = new Person("damien", null);
        dao.addPerson(p1);
        dao.removePerson(p1.getId());
    }


    @Test
    public void testUniqueTableConstraint() {
        var p1 = new Person("damien", new Date("10/10/23"));
        dao.addPerson(p1);
        var p2 = new Person("damien", new Date("10/10/23"));
        assertThrows(Exception.class,
                     ()->dao.addPerson(p2)
                    );
    }

    @Test
    public void testUniqueSecondNameConstraint() {
        var p1 = new Person("Heba", new Date("10/10/60"));
        p1.setSecondName("Heb");
        dao.addPerson(p1);
        var p2 = new Person("bibou", new Date("11/11/24"));
        p2.setSecondName("Heb");
        assertThrows(Exception.class,  ()->dao.addPerson(p2));
    }

    @Test
    public void testMultiThread() {
        var p1 = new Person("Heba", new Date("10/10/60"));

        Thread t1 = new Thread( ()->dao.addPerson(p1) );
        Thread t2 = new Thread( ()->dao.addPerson(p1) );

        t1.start();
        t2.start();

        //TODO exception non visible car sur autre thread, "technique du booléen"
    }

    @PersistenceContext
    EntityManager em;
    
    @Test
    public void testFindPersonsByFirstName() {
        var p1 = new Person("AAAB", new Date("10/10/60"));
        var p2 = new Person("AAAC", new Date("10/10/60"));

        dao.addPerson(p1);
        dao.addPerson(p2);
        
        List<Person> l = em.createNamedQuery("findPersonsByFirstName")
                .setParameter("patern", "AAA%")
                .getResultList();


        assertTrue(l.size()==2);
    }


    @Test
    public void testFindAllPerson() {
    	
        List<Person> l = em.createNamedQuery("listPrenom").getResultList();    
        
        assertTrue(l.size() >0 );
    }
    
    @Test
    public void testAdress() {
    	Person p1 = new Person("Maxi", new Date("11/11/11"));
    	
    	p1.setAddress(new Address("street", "city", "country"));
    	
    	dao.addPerson(p1);
    }




}