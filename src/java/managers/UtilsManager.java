package managers;

import beans.SessionBean;
import beans.VariableBean;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Sapan
 */
public class UtilsManager {

    private static final String VARIABLE_FEEDBACK_TIMESLOT = "feedback_timeslot";
    private static final String VARIABLE_CURRENT_SESSION = "current_session_id";

    public static String getHomeUrl(String type) {
        if (type.equalsIgnoreCase("faculty")) {
            return "faculty/Home.jsp";
        } else if (type.equalsIgnoreCase("hod")) {
            return "hod/Home.jsp";
        } else if (type.equalsIgnoreCase("director")) {
            return "director/Home.jsp";
        } else if (type.equalsIgnoreCase("admin")) {
            return "admin/Home.jsp";
        } else if (type.equalsIgnoreCase("scheduler")) {
            return "scheduler/Home.jsp";
        } else if (type.equalsIgnoreCase("student")) {
            return "student/Home.jsp";
        } else {
            return null;
        }
    }

    public static String getLoginUrl(String type) {
        if (type.equalsIgnoreCase("admin")) {
            return "FacultyLogin.jsp";
        } else if (type.equalsIgnoreCase("director")) {
            return "FacultyLogin.jsp";
        } else if (type.equalsIgnoreCase("hod")) {
            return "FacultyLogin.jsp";
        } else if (type.equalsIgnoreCase("faculty")) {
            return "FacultyLogin.jsp";
        } else if (type.equalsIgnoreCase("scheduler")) {
            return "SchedulerLogin.jsp";
        } else {
            return null;
        }
    }

    public static SessionBean getCurrentSessionId() {
        //returns the id of current session(2016-17 Sem:E) present in variable table and session table
        VariableBean vb = new VariableBean(VARIABLE_CURRENT_SESSION);
        long sessionId = -1;
        try {
            sessionId = Long.parseLong(vb.findByName().getValue());
            if (sessionId == -1) {
                throw new NullPointerException("Custom NullPointerException in getCurrentSessionId");
            }
        } catch (NumberFormatException e) {
            LogManager.log("NumberFormatException: Value of current session id in not long:\n" + e);
        } catch (NullPointerException e) {
            LogManager.log("NullPointerException: current session id is not found in variable table:\n" + e);
        }
        return new SessionBean(sessionId).findById();
    }

    public static int getFeedbackTimeslot() {
        //returns no. of minutes a feedback can be given from the scheduled time
        VariableBean vb = new VariableBean(VARIABLE_FEEDBACK_TIMESLOT);
        int min = -1;
        try {
            min = Integer.parseInt(vb.findByName().getValue());
            if (min == -1) {
                throw new NullPointerException("Custom NullPointerException");
            }
        } catch (NumberFormatException e) {
            LogManager.log("NumberFormatException: Value of feedback timeslot in not Integer:\n" + e);
        } catch (NullPointerException e) {
            LogManager.log("NullPointerException: feedback timeslot is not found in database:\n" + e);
        }
        return min;
    }

    public static int getFeedbackTimeslotInSeconds() {
        //returns no. of milliseconds a feedback can be given from the scheduled time
        return getFeedbackTimeslot() * 60;
    }

    public static String getDateStringFromTimestamp(int timestamp) {
        //our db timestamp is in seconds...convert it into millisecond and then convert to date
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("Asia/Kolkata"));
        c.setTime(new Date(((long) timestamp) * 1000));
        String dateString = "";
        int date = c.get(Calendar.DATE);
        String month = c.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.getDefault());
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        String am_pm = c.getDisplayName(Calendar.AM_PM, Calendar.SHORT_FORMAT, Locale.getDefault());
        dateString += date + "-" + month + "-" + year + " "
                + hour + ":" + minute + " " + am_pm;
        return dateString;
    }

    public static int getCurrentTimestampInSeconds() {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("Asia/Kolkata"));
        c.setTime(new Date());
        return (int) (c.getTimeInMillis() / 1000);
    }

    public static String getStudentFeedbackUrl() {
        return "student/Feedback.jsp";
    }

    public static String getThankYouPage() {
        return "student/ThankYou.jsp";
    }

    public static String getRow(String attribute, int sno, String name, int radioCount) {
        String row = "";
        row += "<tr>\n";
        row += "<td>" + sno + "</td>\n";
        row += "<td>" + attribute + "</td>\n";
        for (int i = radioCount; i > 0; i--) {
            row += "<td>" + "<input required type='radio' class='form-control' value='" + i + "' name='" + name + "'/>" + "</td>\n";
        }
        row += "</tr>\n";
        return row;
    }
}
