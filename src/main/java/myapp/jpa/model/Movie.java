package myapp.jpa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    public Movie(String name) {
        this.name = name;
    }

}