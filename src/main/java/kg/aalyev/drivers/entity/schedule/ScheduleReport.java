package kg.aalyev.drivers.entity.schedule;

import kg.aalyev.drivers.entity.Patient;
import lombok.Data;

@Data
public class ScheduleReport {
    private String time;
    private Patient patient;

    public ScheduleReport(String time, Patient patient) {
        this.patient = patient;
        this.time = time;
    }
}
