package myapp.jpa.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import myapp.jpa.model.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Repository
@Transactional
public class JpaDao {


    private EntityManagerFactory factory = null;

    @PersistenceContext
    EntityManager em;


    /*
     * Ajouter une personne
     */
    public Person addPerson(Person p) {
        em.persist(p);
        return (p);
    }

    /*
     * Charger une personne
     */
    public Person findPerson(long id) {
        Person p = em.find(Person.class, id);
        return p;
    }


    public void updatePerson(Person new_p) {
        em.merge(new_p);
    }

    public void removePerson(long id) {
        Person findedPerson = findPerson(id);
        em.remove(em.contains(findedPerson) ? findedPerson : em.merge(findedPerson));
    }


    public List<Person> findAllPersons() {
        String query = "SELECT p FROM Person p";
        TypedQuery<Person> q = em.createQuery(query, Person.class);
        return q.getResultList();
    }

  /*  public List<Person> findPersonsByFirstName(String pattern) {
        Query query = em.createQuery("SELECT p from Person p where p.firstName like :patern" );
        query.setParameter(1 , pattern);
        return query.getResultList();
    }
*/

}