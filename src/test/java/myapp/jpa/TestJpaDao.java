package myapp.jpa;

import myapp.jpa.model.Car;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestJpaDao {

    @Autowired
    JpaDao dao;

    @PersistenceContext
    EntityManager em;


   /* @Test
    public void testUpdatePerson() {
        var p1 = new Person("damien", null);
        dao.addPerson(p1);
        p1.setFirstName("maxime");

        dao.updatePerson(p1);
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

    @Test
    public void testAddCarWithOwner() {
        Person p1 = new Person("Hugo", new Date("11/11/11"));
        dao.add(p1);
        Car car = new Car("A1A","alfa-romeo 4C");
        car.setOwner(p1);
        dao.add(car);
    }

*/
    @Test
    public void testAddCarFromOwner() {
        //new owner
        Car car = new Car("A1A","bmw m3");
        Person p1 = new Person("Charles", new Date("12/12/12"));
        p1.addCar(car);
        dao.add(p1);

        //update owner
        Car car2 = new Car("A1ABD","alfa-romeo 8C");
        Person p2 = dao.findPerson(p1.getId());
        assertTrue(p2.getFirstName().equals("Charles"));
        p2.addCar(car2);
        dao.update(p2);


        //print all persons
        List<Person> list_all = em.createQuery("SELECT DISTINCT p FROM Person p JOIN FETCH p.cars").getResultList();
        System.out.println(">>>>>>"+list_all.size());
        for (Person p: list_all) {
            System.out.println(">>>>>>"+p);
            Set<Car> list_Cars = p.getCars();
            for (Car c: list_Cars) {
                System.out.println("<<<<<"+c);
            }
        }

        assertTrue(dao.findPerson(p1.getId()).getCars().size()==2);
    }


    @Test
    public void testFindPersonsByCarModel() {
        //new owner
        Car car = new Car("ABCD","the bmw m3 bleu");
        Person p1 = new Person("Hamilton", new Date("12/12/12"));
        p1.addCar(car);
        dao.addPerson(p1);


        List<Person> l = em.createNamedQuery("findPersonsByCarModel")
                .setParameter("pattern", "%bmw%")
                .getResultList();

        //print all persons
        List<Person> list_all = em.createQuery("SELECT DISTINCT p FROM Person p JOIN FETCH p.cars").getResultList();
        System.out.println(">>>>>>"+list_all.size());
        for (Person p: list_all) {
            System.out.println(">>>>>>"+p);
            Set<Car> list_Cars = p.getCars();
            for (Car c: list_Cars) {
                System.out.println("<<<<<"+c);
            }
        }

        assertTrue(l.size()>=1);
    }



    @Test
    public void testOrderCars() {
        //new owner with 2 cars
        Car car = new Car("BBBBB","the bmw m3 bleu");
        Person p1 = new Person("Russel", new Date("12/12/12"));
        p1.addCar(car);
        dao.addPerson(p1);

        Car car2 = new Car("AAAAA","the bmw m3 bleu");
        p1.addCar(car2);
        dao.update(p1);


        List<Person> l = em.createNamedQuery("findPersonsByCarModel")
                .setParameter("pattern", "%bmw%")
                .getResultList();


        //print all persons
        List<Person> list_all = em.createQuery("SELECT DISTINCT p FROM Person p JOIN FETCH p.cars").getResultList();
        System.out.println(">>>>>>"+list_all.size());
        for (Person p: list_all) {
            System.out.println(">>>>>>"+p);
            Set<Car> list_Cars = p.getCars();
            for (Car c: list_Cars) {
                System.out.println("<<<<<"+c);
            }
        }

    }







}