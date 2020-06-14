package kg.aalyev.drivers.entity;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author aziko
 */

@Access(AccessType.PROPERTY)
@Entity
@Table(name = "role")
@ToString
public class Role extends AbstractEntity<Integer> {
    @Setter
    private String title;
    @Setter
    private String description;

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }
}
