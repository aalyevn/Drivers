package kg.aalyev.drivers.controller.admin;

import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.controller.BaseBean;
import kg.aalyev.drivers.entity.Patient;
import kg.aalyev.drivers.entity.Schedule;
import kg.aalyev.drivers.enums.InequalityConstants;
import kg.aalyev.drivers.enums.SortEnum;
import kg.aalyev.drivers.service.PatientService;
import kg.aalyev.drivers.service.ScheduleService;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class PatientController extends BaseBean {
    @EJB
    private PatientService patientService;

    @Setter
    @Getter
    private Patient patient;

    @Setter
    @Getter
    private String name, phone;

    @Setter
    @Getter
    private List<Patient> patientList;

    private boolean flag = false;

    public void init() {
        if (!flag) {
            List<FilterExample> filter = getFilters();
            patientList = patientService.findByExample(0, 0, SortEnum.ASCENDING, filter, "name");
        }
    }

    private List<FilterExample> getFilters() {
        List<FilterExample> filter = new ArrayList<>();
        filter.add(new FilterExample("name", InequalityConstants.IS_NOT_NULL));
        return filter;
    }

    public void search() {
        List<FilterExample> filter = getFilters();
        if (!name.isEmpty()) {
            filter.add(new FilterExample("name", "%" + name + "%", InequalityConstants.LIKE));
        }
        if (!phone.isEmpty()) {
            filter.add(new FilterExample("phone", "%" + phone + "%", InequalityConstants.LIKE));
        }
        patientList = patientService.findByExample(0, 0, SortEnum.ASCENDING, filter, "name");
        flag = true;
    }

    public String add() {
        patient = new Patient();
        return navigate("form.xhtml");
    }

    public String edit(Patient p) {
        patient = p;
        return navigate("form.xhtml");
    }

    public String back() {
        patient = null;
        return navigate("list.xhtml");
    }

    public String save() {
        if (patient.getId() == null) {
            patient.setCreateDate(new Date());
            patientService.persist(patient);
            patient = null;
        } else {
            patient.setEditDate(new Date());
            patientService.merge(patient);
            patient = null;
        }
        return navigate("list.xhtml");
    }

    @Setter
    @Getter
    private List<Schedule> historyList;

    @Setter
    @Getter
    private Patient choosedPatient;

    @EJB
    private ScheduleService scheduleService;

    public void initPatient(){
        List<FilterExample> filter = new ArrayList<>();
        filter.add(new FilterExample("deleteDate",InequalityConstants.IS_NULL));
        filter.add(new FilterExample("patient",choosedPatient, InequalityConstants.EQUAL));
        historyList = scheduleService.findByExample(0, 0, SortEnum.DESCENDING, filter, "eventDate");
    }

    public String showHistory(Patient p){
        choosedPatient = p;
        return navigate("patient.xhtml");
    }


}
