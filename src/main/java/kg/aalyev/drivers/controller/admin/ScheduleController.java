package kg.aalyev.drivers.controller.admin;

import kg.aalyev.drivers.beans.FilterExample;
import kg.aalyev.drivers.controller.BaseBean;
import kg.aalyev.drivers.entity.Patient;
import kg.aalyev.drivers.entity.Schedule;
import kg.aalyev.drivers.entity.schedule.ScheduleReport;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class ScheduleController extends BaseBean {
    @EJB
    private ScheduleService scheduleService;
    @EJB
    private PatientService patientService;

    @Setter
    @Getter
    private List<ScheduleReport> currentScheduleReport, nextScheduleReport, randomScheduleReport;

    @Setter
    @Getter
    private Schedule schedule;

    private List<FilterExample> getFilters(int day) {
        List<FilterExample> filter = new ArrayList<>();
        filter.add(new FilterExample("deleteDate", InequalityConstants.IS_NULL));
        filter.add(new FilterExample("eventDate", getCurrentDate(day), InequalityConstants.GREATER_EQUAL));
        filter.add(new FilterExample("eventDate", getCurrentDate(day + 1), InequalityConstants.LESSER));
        return filter;
    }

    public Date getCurrentDate(int addDay) {
        Calendar c = Calendar.getInstance();
        if (addDay != 0) {
            c.add(Calendar.DAY_OF_MONTH, addDay);
        } else
            c.add(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
//        System.out.println(c.getTime());
        return c.getTime();
    }

    public String checkDate() {
        return navigate("add.xhtml");
    }

    public void init() {
        List<Schedule> currentScheduleList = scheduleService.findByExample(0, 0, SortEnum.ASCENDING, getFilters(0), "eventDate");
        List<Schedule> nextScheduleList = scheduleService.findByExample(0, 0, SortEnum.ASCENDING, getFilters(1), "eventDate");
        currentScheduleReport = new ArrayList<>();
        nextScheduleReport = new ArrayList<>();

        for (int i = 7; i <= 22; i++) {
            String time;
            if (i == 7)
                time = "0" + i + ":00";
            else
                time = i + ":00";
            Patient patient = null;
            Patient nextPatient = null;
            for (Schedule sched : currentScheduleList) {
                if (getFormatString("HH:mm", sched.getEventTime()).contains(time)) {
                    patient = sched.getPatient();
                }
            }
            for (Schedule schedule : nextScheduleList) {
                if (getFormatString("HH:mm", schedule.getEventTime()).contains(time)) {
                    nextPatient = schedule.getPatient();
                }
            }
            currentScheduleReport.add(new ScheduleReport(time, patient));
            nextScheduleReport.add(new ScheduleReport(time, nextPatient));
        }

    }

    public String add(Date date, String time) {
        schedule = new Schedule();
        schedule.setEventDate(date);
        schedule.setEventTime(getFormatDate("HH:mm", time));
        return navigate("search.xhtml");
    }

    public String edit(Date date, String time) {
        schedule = getScheduleByDateTime(date, time);
        return navigate("form.xhtml");
    }

    public String delete(Date date, String time) {
        Schedule s = getScheduleByDateTime(date, time);
        s.setDeleteDate(new Date());
        scheduleService.merge(s);
        return navigate("list.xhtml");
    }

    private Schedule getScheduleByDateTime(Date date, String time) {
        List<FilterExample> filter = new ArrayList<>();
        filter.add(new FilterExample("eventDate", date, InequalityConstants.EQUAL));
        filter.add(new FilterExample("eventTime", getFormatDate("HH:mm", time), InequalityConstants.EQUAL));
        return scheduleService.findByExample(0, 1, SortEnum.ASCENDING, filter, "eventDate").get(0);
    }

    @Setter
    @Getter
    private String name, phone;
    @Setter
    @Getter
    private List<Patient> patientList;

    private boolean flag = false;

    public void initSearch() {
        if (!flag) {
        }
    }

    public String cancel() {
        schedule = null;
        name = null;
        phone = null;
        return navigate("list.xhtml");
    }

    public String save() {
        if (schedule.getId() == null) {
            schedule.setCreateDate(new Date());
            scheduleService.persist(schedule);
        } else {
            schedule.setEditDate(new Date());
            scheduleService.merge(schedule);
        }
        schedule = null;
        return navigate("list.xhtml");
    }

    public void search() {
        List<FilterExample> filter = new ArrayList<>();
        if (!name.isEmpty())
            filter.add(new FilterExample("name", "%" + name + "%", InequalityConstants.LIKE));
        if (!phone.isEmpty())
            filter.add(new FilterExample("phone", "%" + phone + "%", InequalityConstants.LIKE));
        patientList = patientService.findByExample(0, 20, SortEnum.ASCENDING, filter, "name");
        flag = true;
    }

    public String choose(Patient p) {
        if (schedule != null) {
            schedule.setPatient(p);
            schedule.setCreateDate(new Date());
            scheduleService.persist(schedule);
        }
        return navigate("list.xhtml");
    }


    @Setter
    @Getter
    private Date selectDate;

    private boolean flagAdd = false;

    public void initAdd() {
        if (!flagAdd) {
            selectDate = getCurrentDate(0);
            List<Schedule> randomScheduleList = scheduleService.findByExample(0, 0, SortEnum.ASCENDING, getFilters(0), "eventDate");
            randomScheduleReport = new ArrayList<>();

            for (int i = 7; i <= 22; i++) {
                String time;
                if (i == 7)
                    time = "0" + i + ":00";
                else
                    time = i + ":00";
                Patient patient = null;
                for (Schedule sched : randomScheduleList) {
                    if (getFormatString("HH:mm", sched.getEventTime()).contains(time)) {
                        patient = sched.getPatient();
                    }
                }
                randomScheduleReport.add(new ScheduleReport(time, patient));
            }
        }
    }

    public void searchAdd() {
        List<FilterExample> filter = new ArrayList<>();
        filter.add(new FilterExample("deleteDate", InequalityConstants.IS_NULL));
        filter.add(new FilterExample("eventDate", selectDate, InequalityConstants.EQUAL));
//        filter.add(new FilterExample("eventDate", selectDate, InequalityConstants.LESSER));
        List<Schedule> randomScheduleList = scheduleService.findByExample(0, 0, SortEnum.ASCENDING, filter, "eventDate");
        randomScheduleReport = new ArrayList<>();

        for (int i = 7; i <= 22; i++) {
            String time;
            if (i == 7)
                time = "0" + i + ":00";
            else
                time = i + ":00";
            Patient patient = null;
            for (Schedule sched : randomScheduleList) {
                if (getFormatString("HH:mm", sched.getEventTime()).contains(time)) {
                    patient = sched.getPatient();
                }
            }
            randomScheduleReport.add(new ScheduleReport(time, patient));
        }
        flagAdd = true;
    }
}
