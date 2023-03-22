package myapp.jpa.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "immatriculation")
@NamedQuery(
        name="findPersonsByCarModel",
        query="SELECT p from Car c JOIN c.owner p WHERE c.model LIKE :pattern"
)
public class Car {

    @Id
    private String immatriculation;

    @Basic(optional = false)
    private String model;

    @ManyToOne(optional = true)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude // afin d'éviter les boucles
    private Person owner;

    public Car(String immatriculation, String model) {
        this(immatriculation, model, null);
    }

}