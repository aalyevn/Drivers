package kg.aalyev.drivers.entity;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Access(AccessType.PROPERTY)
@Entity
@Table(name = "patients")
@ToString
public class Patient extends AbstractEntity<Integer> {

    @Setter
    private String name;
    @Setter
    private String firstVisit;
    @Setter
    private Date birthday;
    @Setter
    private String address;
    @Setter
    private String phone;
    @Setter
    private Date createDate;
    @Setter
    private Date editDate;

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @Column(name = "first_visit")
    public String getFirstVisit() {
        return firstVisit;
    }

    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "edit_date")
    public Date getEditDate() {
        return editDate;
    }
}
