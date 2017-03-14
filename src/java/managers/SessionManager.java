package managers;

import beans.FacultyBean;
import beans.LoginBean;
import beans.SchedulerBean;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sapan
 */
public class SessionManager {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    public SessionManager(HttpServletRequest request, HttpServletResponse response, boolean createNewSession) {
        this.request = request;
        this.response = response;
        init(createNewSession);
    }

    private void init(boolean createNewSession) {
        LogManager.log("SessionManager initiated!");
        session = request.getSession(createNewSession);
    }

    public void startFacultySession(LoginBean loginBean) {
        if (isFacultySession()) {
            //Firstly invalidate previous faculty session(if any)
            invalidateFacultySession();
        }
        FacultyBean faculty = loginBean.getFacultyId();
        session.setAttribute("faculty_session_id", session.getId());
        session.setAttribute("faculty_id", faculty.getId());
        session.setAttribute("faculty_type", loginBean.getType());
        session.setAttribute("faculty_bean", faculty);
        LogManager.log("Faculty Session Started!");
        LogManager.log("Faculty Session ID: " + session.getId());
        LogManager.log("Faculty ID: " + faculty.getId());
        LogManager.log("Faculty Type: " + loginBean.getType());
    }

    public void startSchedulerSession(SchedulerBean scheduler) {
        if (isSchedulerSession()) {
            //Firstly invalidate previous scheduler session(if any)
            invalidateSchedulerSession();
        }
        session.setAttribute("scheduler_session_id", session.getId());
        session.setAttribute("scheduler_username", scheduler.getUsername());
        session.setAttribute("scheduler_name", scheduler.getName());
        LogManager.log("Scheduler Session Started!");
        LogManager.log("Scheduler Session ID: " + session.getId());
        LogManager.log("Scheduler Username: " + scheduler.getUsername());
        LogManager.log("Scheduler Name: " + scheduler.getName());
    }

    public void invalidateFacultySession() {
        session.setAttribute("faculty_session_id", null);
        session.setAttribute("faculty_id", null);
        session.setAttribute("faculty_type", null);
        session.setAttribute("faculty_bean", null);
        LogManager.log("Faculty session invalidated");
    }

    public void invalidateSchedulerSession() {
        session.setAttribute("scheduler_session_id", null);
        session.setAttribute("scheduler_username", null);
        session.setAttribute("scheduler_name", null);
        LogManager.log("Scheduler session invalidated");
    }

    public void invalidateAllSession() {
        LogManager.log("Invalidating All Sessions");
        invalidateFacultySession();
        invalidateSchedulerSession();
    }

    public boolean isFacultySession() {
        //Return true for either faculty or hod or director or admin
        if (session.getAttribute("faculty_session_id") != null && session.getAttribute("faculty_id") != null) {
            return ((String) session.getAttribute("faculty_type")).equalsIgnoreCase("faculty")
                    || isHODSession();
        }
        return false;
    }

    public boolean isHODSession() {
        //Return true for either hod or director or admin
        if (session.getAttribute("faculty_session_id") != null && session.getAttribute("faculty_id") != null) {
            return ((String) session.getAttribute("faculty_type")).equalsIgnoreCase("hod")
                    || isDirectorSession();
        }
        return false;
    }

    public boolean isDirectorSession() {
        //Return true for either director or admin
        if (session.getAttribute("faculty_session_id") != null && session.getAttribute("faculty_id") != null) {
            return ((String) session.getAttribute("faculty_type")).equalsIgnoreCase("director")
                    || isAdminSession();
        }
        return false;
    }

    public boolean isAdminSession() {
        //Return true for admin
        if (session.getAttribute("faculty_session_id") != null && session.getAttribute("faculty_id") != null) {
            return ((String) session.getAttribute("faculty_type")).equalsIgnoreCase("admin");
        }
        return false;
    }

    public boolean isSchedulerSession() {
        //Return true for scheduler
        if (session.getAttribute("scheduler_session_id") != null && session.getAttribute("scheduler_username") != null) {
            return true;
        }
        return false;
    }

    public boolean checkAdminSession(String onFailUrl) throws IOException {
        //Check for admin session return true on success else redirect to onFailUrl 
        if (isAdminSession()) {
            LogManager.log("Admin session check: Success");
            return true;
        } else {
            try {
                response.sendRedirect(onFailUrl);
            } catch (IOException ex) {
                LogManager.log("IOException in SessionManager::checkAdminSession(): " + ex);
                return false;
            }
        }
        return false;
    }

    public boolean checkDirectorSession(String onFailUrl) throws IOException {
        //Check for director session return true on success else redirect to onFailUrl 
        if (isDirectorSession()) {
            LogManager.log("Director session check: Success");
            return true;
        } else {
            try {
                response.sendRedirect(onFailUrl);
            } catch (IOException ex) {
                LogManager.log("IOException in SessionManager::checkDirectorSession(): " + ex);
                return false;
            }
        }
        return false;
    }

    public boolean checkHODSession(String onFailUrl) throws IOException {
        //Check for hod session return true on success else redirect to onFailUrl 
        if (isHODSession()) {
            LogManager.log("HOD session check: Success");
            return true;
        } else {
            try {
                response.sendRedirect(onFailUrl);
            } catch (IOException ex) {
                LogManager.log("IOException in SessionManager::checkHODSession(): " + ex);
                return false;
            }
        }
        return false;
    }

    public boolean checkFacultySession(String onFailUrl) throws IOException {
        //Check for faculty session return true on success else redirect to onFailUrl 
        if (isFacultySession()) {
            LogManager.log("Faculty session check: Success");
            return true;
        } else {
            try {
                response.sendRedirect(onFailUrl);
            } catch (IOException ex) {
                LogManager.log("IOException in SessionManager::checkFacultySession(): " + ex);
                return false;
            }
        }
        return false;
    }

    public boolean checkSchedulerSession(String onFailUrl) throws IOException {
        //Check for scheduler session return true on success else redirect to onFailUrl 
        if (isSchedulerSession()) {
            LogManager.log("Scheduler session check: Success");
            return true;
        } else {
            try {
                response.sendRedirect(onFailUrl);
            } catch (IOException ex) {
                LogManager.log("IOException in SessionManager::checkSchedulerSession(): " + ex);
                return false;
            }
        }
        return false;
    }

    public String getFacultyType() {
        if (isFacultySession()) {
            return (String) session.getAttribute("faculty_type");
        }
        return null;
    }

    public FacultyBean getFaculty() {
        if (isFacultySession()) {
            return (FacultyBean) session.getAttribute("faculty_bean");
        }
        return null;
    }

    public String getSchedulerName() {
        if (isSchedulerSession()) {
            return (String) session.getAttribute("scheduler_name");
        }
        return null;
    }

    public boolean logoutFaculty(String logoutUrl) {
        invalidateFacultySession();
        try {
            response.sendRedirect(logoutUrl);
        } catch (IOException ex) {
            LogManager.log("IOException in SessionManager::logoutFaculty(): " + ex);
            return false;
        }
        LogManager.log("Faculty logout success");
        return true;
    }

    public boolean logoutScheduler() {
        return false;
    }
}

/*
    public void startAdminSession(LoginBean loginBean) {
        FacultyBean faculty = loginBean.getFacultyId();
        HttpSession session = request.getSession(true);
        session.setAttribute("faculty_session_id", session.getId());
        session.setAttribute("faculty_id", faculty.getId());
        session.setAttribute("faculty_type", loginBean.getType());
        session.setAttribute("faculty_bean", faculty);
    }

    public void startDirectorSession(LoginBean loginBean) {
        FacultyBean faculty = loginBean.getFacultyId();
        HttpSession session = request.getSession(true);
        session.setAttribute("faculty_session_id", session.getId());
        session.setAttribute("faculty_id", faculty.getId());
        session.setAttribute("faculty_type", loginBean.getType());
        session.setAttribute("faculty_bean", faculty);
    }

    public void startHODSession(LoginBean loginBean) {
        FacultyBean faculty = loginBean.getFacultyId();
        HttpSession session = request.getSession(true);
        session.setAttribute("faculty_session_id", session.getId());
        session.setAttribute("faculty_id", faculty.getId());
        session.setAttribute("faculty_type", loginBean.getType());
        session.setAttribute("faculty_bean", faculty);
    }
 */
