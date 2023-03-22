package myapp.jpa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "Person")
@Data
@NoArgsConstructor

@Table(name = "TPerson",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "first_name", "birth_day"
                })
        })
@NamedQuery(
        name="findPersonsByFirstName",
        query="SELECT p from Person p where p.firstName like :patern"
)

@NamedQuery(
        name="listPrenom",
        query="SELECT new myapp.jpa.model.FirstName(p.id,p.firstName) FROM Person p"
)

public class Person {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic(optional = false, fetch = FetchType.EAGER)
    @Column(name = "first_name", length = 200)
    private String firstName;


    @Basic()
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_day")
    private Date birthDay;

    @Version()
    private long version = 0;
    
    @Embedded
    private Address address;

    @Transient
    public static long updateCounter = 0;

    public Person(String firstName, Date birthDay) {
        super();
        this.firstName = firstName;
        this.birthDay = birthDay;
    }

    @Basic(optional = true, fetch = FetchType.EAGER)
    @Column(name = "second_name", length = 100, nullable = true, unique = true)
    private String secondName;




    @OneToMany(
            fetch = FetchType.LAZY,
            targetEntity= Car.class,
            mappedBy = "owner",
            cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }
    )

    @ToString.Exclude// pour éviter les boucles
    @OrderBy("immatriculation ASC")
    private Set<Car> cars;


    public void addCar(Car c) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(c);
        c.setOwner(this);
    }


    //---------------------MOVIES-------------------------------

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    // @JoinTable est optionnelle (afin de préciser les tables)
    @JoinTable(
            name = "Person_Movie",
            joinColumns = { @JoinColumn(name = "id_person") },
            inverseJoinColumns = { @JoinColumn(name = "id_movie") }
    )
    @ToString.Exclude
    Set<Movie> movies;

    public void addMovie(Movie movie) {
        if (movies == null) {
            movies = new HashSet<>();
        }
        movies.add(movie);
    }



    //-----------------------------------------------------------------

    @PreUpdate
    public void beforeUpdate() {
        System.err.println("PreUpdate of " + this);
    }

    @PostUpdate
    public void afterUpdate() {
        System.err.println("PostUpdate of " + this);
        updateCounter++;
    }


}
