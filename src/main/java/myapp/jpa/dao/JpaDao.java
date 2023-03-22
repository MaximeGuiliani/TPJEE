package myapp.jpa.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import myapp.jpa.model.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class JpaDao {


    @PersistenceContext
    EntityManager em;


    public <T> T find(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }


    public <T> Collection<T> findAll(String query, Class<T> clazz) {
        TypedQuery<T> q = em.createQuery(query, clazz);
        return q.getResultList();
    }

    @Transactional
    public <T> T add(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T update(T entity) {
        return em.merge(entity);
    }

    public <T> void remove(Class<T> clazz, Object pk) {
        T entity = em.find(clazz, pk);
        if (entity != null) {
            em.remove(entity);
        }
    }


    public Person addPerson(Person p) {
        em.persist(p);
        return (p);
    }


    public Person findPerson(long id) {
        Person p = em.find(Person.class, id);
        p.getCars().size();
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

    /*
    public List<Person> findPersonsByFirstName(String pattern) {
        Query query = em.createQuery("SELECT p from Person p where p.firstName like :patern" );
        query.setParameter(1 , pattern);
        return query.getResultList();
    }
*/


}