package kg.aalyev.drivers.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Access(AccessType.PROPERTY)
@Entity
@Table(name = "schedule")
@ToString
public class Schedule extends AbstractEntity<Integer> {

    @Setter
    private String diagnosis;
    @Setter
    private String totalWork;
    @Setter
    private String notes;
    @Setter
    private String allergy;
    @Setter
    private Date eventDate;
    @Setter
    private Date eventTime;
    @Setter
    private Date createDate;
    @Setter
    private Date editDate;
    @Setter
    private Date deleteDate;
    @Setter
    private Patient patient;

    public Schedule() {
    }



    @Column(name = "diagnosis")
    public String getDiagnosis() {
        return diagnosis;
    }

    @Column(name = "total_work")
    public String getTotalWork() {
        return totalWork;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    @Column(name = "allergy")
    public String getAllergy() {
        return allergy;
    }

    @Column(name = "event_date")
    public Date getEventDate() {
        return eventDate;
    }

    @Column(name = "event_time")
    public Date getEventTime() {
        return eventTime;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "edit_date")
    public Date getEditDate() {
        return editDate;
    }

    @Column(name = "delete_date")
    public Date getDeleteDate() {
        return deleteDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Patient getPatient() {
        return patient;
    }
}
