package myapp.jpa.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Person")
@Data
@NoArgsConstructor

@Table(name = "TPerson",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "first_name", "birth_day"
                })
        })
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