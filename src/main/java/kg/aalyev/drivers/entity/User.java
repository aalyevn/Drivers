package kg.aalyev.drivers.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Access(AccessType.PROPERTY)
@Entity
@Table(name = "users")
@ToString
public class User extends AbstractEntity<Integer> {

    @Setter
    private String login;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private String password;
    @Setter
    private String salt;
    @Setter
    private String post;
    @Setter
    private Date createDate;
    @Setter
    private Date editDate;
    @Setter
    private Date deleteDate;
    @Setter
    private boolean deleted;


    @Setter
    private Role role;

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    @Column(name = "password", length = 256)
    public String getPassword() {
        return password;
    }

    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    @Column(name = "post", nullable = true)
    public String getPost() {
        return post;
    }

    @Column(name = "create_date", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "edit_date", nullable = true)
    public Date getEditDate() {
        return editDate;
    }

    @Column(name = "delete_date", nullable = true)
    public Date getDeleteDate() {
        return deleteDate;
    }

    @Column(name = "deleted", columnDefinition = "boolean default false")
    public boolean isDeleted() {
        return deleted;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Role getRole() {
        return role;
    }

//    @Transient
//    public String forLog() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("User(ID=").append(getId());
//        builder.append(",Login=").append(getLogin());
//        builder.append(",Role=").append(getRole().getTitle());
//        builder.append(",CreateDate=").append(getCreatedDate());
//        builder.append(")");
//        return String.valueOf(builder);
//    }
}