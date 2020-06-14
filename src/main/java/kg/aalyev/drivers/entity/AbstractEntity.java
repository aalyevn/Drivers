package kg.aalyev.drivers.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @param <S>
 * @author aziko
 */

@MappedSuperclass
public abstract class AbstractEntity<S extends Serializable> implements PersistentEntity<S> {

    private static final long serialVersionUID = 4635290765498914296L;
    protected S id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public S getId() {
        return id;
    }

    public void setId(S id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        try {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!obj.getClass().equals(this.getClass()))
                return false;

            AbstractEntity<S> other = (AbstractEntity<S>) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getClass() + " [id=" + id + "]";
    }

}
