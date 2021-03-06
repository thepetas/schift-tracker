package cz.cvut.fit.shiftify.data;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.models.User;

/**
 * Created by petr on 1/5/17.
 */

public class DataSeeder {

    private static final int USERS_COUNT = 8;
    private static final int SCHEDULE_TYPES_COUNT = 2;

    public static void initAll() {
        //initUsers();
        initScheduleTypesAndScheduleShifts();
        //initSchedules();
    }

    private static void initUsers() {
        UserManager userManager = new UserManager();
        List<User> users = userManager.allUsers();

        if (users.size() < USERS_COUNT) {
            try {
                userManager.deleteAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            users.clear();

            users.add(new User("Michal", "Plameňák", "777222111", "random@something.org"));
            users.add(new User("Petr", "Kůň"));
            users.add(new User("Martin", "Salamini", null, "some@address.com", "meloun"));
            users.add(new User("Martin", "Salamini", "+420423458932"));
            users.add(new User("Adam", "Moron", "+420423458932"));
            users.add(new User("Jaromir", "Jagr", "+420423458932"));
            users.add(new User("Nekdo", "Nekdovic", "+420423458932"));
            users.add(new User("Nikdo", "Kdokolic", "+420427458932"));
            for (User u : users) {
                try {
                    userManager.add(u);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static void initScheduleTypesAndScheduleShifts() {
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        try {
            if (scheduleTypeManager.scheduleTypes().size() > 0)
                return;
            scheduleTypeManager.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ScheduleShift> shifts = new ArrayList<>();
        ScheduleType scheduleType;

        shifts.add(new ScheduleShift("1. ranní", new LocalTime(6, 0), new Period(8, 0, 0, 0), 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("2. ranní", new LocalTime(6, 0), new Period(8, 0, 0, 0), 2));
        shifts.add(new ScheduleShift("1. odpolední", new LocalTime(14, 0), new Period(8, 0, 0, 0), 3));
        shifts.add(new ScheduleShift("2. odpolední", new LocalTime(14, 0), new Period(8, 0, 0, 0), 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("1. noční", new LocalTime(22, 0), new Period(8, 0, 0, 0), 5));
        shifts.add(new ScheduleShift("2. noční", new LocalTime(22, 0), new Period(8, 0, 0, 0), 6, "komentář"));
        scheduleType = new ScheduleType("Železárny", 8);
        scheduleType.setShifts(shifts);
        try {
            // shifts are added as in the add method as well
            scheduleTypeManager.add(scheduleType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        shifts.clear();

        shifts.add(new ScheduleShift("pondělí", new LocalTime(8, 0), new Period(8, 30, 0, 0), 1));
        shifts.add(new ScheduleShift("úterý", new LocalTime(8, 0), new Period(8, 30, 0, 0), 2));
        shifts.add(new ScheduleShift("středa", new LocalTime(8, 0), new Period(8, 30, 0, 0), 3));
        shifts.add(new ScheduleShift("čtvrtek", new LocalTime(8, 0), new Period(8, 30, 0, 0), 4));
        shifts.add(new ScheduleShift("pátek", new LocalTime(8, 0), new Period(8, 30, 0, 0), 5));
        scheduleType = new ScheduleType("Po-Pá od osmi", 7);
        scheduleType.setShifts(shifts);

        try {
            // shifts are added as in the add method as well
            scheduleTypeManager.add(scheduleType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        shifts.clear();

        shifts.add(new ScheduleShift("pondělí", new LocalTime(9, 0), new Period(8, 30, 0, 0), 1));
        shifts.add(new ScheduleShift("úterý", new LocalTime(9, 0), new Period(8, 30, 0, 0), 2));
        shifts.add(new ScheduleShift("středa", new LocalTime(9, 0), new Period(8, 30, 0, 0), 3));
        shifts.add(new ScheduleShift("čtvrtek", new LocalTime(9, 0), new Period(8, 30, 0, 0), 4));
        shifts.add(new ScheduleShift("pátek", new LocalTime(9, 0), new Period(8, 30, 0, 0), 5));
        scheduleType = new ScheduleType("Po-Pá od devíti", 7);
        scheduleType.setShifts(shifts);

        try {
            // shifts are added as in the add method as well
            scheduleTypeManager.add(scheduleType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initSchedules() {
        UserManager userManager = new UserManager();
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        userManager.deleteScheduleAll();
        List<ScheduleType> scheduleTypes = new ArrayList<>();

        try {
            scheduleTypes = scheduleTypeManager.scheduleTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (User user : userManager.allUsers()) {
            List<Schedule> schedules = new ArrayList<>();
            schedules.add(new Schedule(user.getId(), scheduleTypes.get(0).getId(),
                    new LocalDate(2016, 10, 2), new LocalDate(2017, 10, 1), new Random().nextInt(6)+1));
            schedules.add(new Schedule(user.getId(), scheduleTypes.get(1).getId(),
                    new LocalDate(2017, 10, 2), new LocalDate(2017, 10, 10), new Random().nextInt(8)+1));
            for (Schedule schedule : schedules) {
                try {
                    userManager.addSchedule(schedule);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
