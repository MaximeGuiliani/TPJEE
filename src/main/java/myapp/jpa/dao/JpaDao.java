package myapp.jpa.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import myapp.jpa.model.Person;

@Service
public class JpaDao {

    private EntityManagerFactory factory = null;

    @PostConstruct
    public void init() {
        factory = Persistence.createEntityManagerFactory("myBase");
    }

    @PreDestroy
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }

    /*
     * Ajouter une personne
     */
    public Person addPerson(Person p) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } finally {
            closeEntityManager(em);
        }
    }

    /*
     * Charger une personne
     */
    public Person findPerson(long id) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            Person p = em.find(Person.class, id);
            em.getTransaction().commit();
            return p;
        } finally {
            closeEntityManager(em);
        }
    }

    /*
     * Fermeture d'un EM (avec rollback éventuellement)
     */
    private void closeEntityManager(EntityManager em) {
        if (em == null || !em.isOpen())
            return;

        var t = em.getTransaction();
        if (t.isActive()) {
            try {
                t.rollback();
            } catch (PersistenceException e) {
                e.printStackTrace(System.err);
            }
        }
        em.close();
    }


    public void updatePerson(Person new_p) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();

            em.merge(new_p);

            em.getTransaction().commit();
        } finally {
            closeEntityManager(em);
        }
    }

    public void removePerson(long id) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();

            Person findedPerson = findPerson(id);
            em.remove(em.contains(findedPerson) ? findedPerson : em.merge(findedPerson));

            em.getTransaction().commit();
        } finally {
            closeEntityManager(em);
        }
    }



}