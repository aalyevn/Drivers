package kg.aalyev.drivers.entity;

import java.io.Serializable;

/**
 * @param <PK>
 * @author aziko
 */

public interface PersistentEntity<PK extends Serializable> extends Serializable {

    PK getId();

    void setId(PK pk);

}
