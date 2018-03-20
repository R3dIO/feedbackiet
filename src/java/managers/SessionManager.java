package managers;

import beans.ClassBean;
import beans.CourseBean;
import beans.CsfBean;
import beans.DepartmentBean;
import beans.FacultyBean;
import beans.LoginBean;
import beans.ScheduledFeedbackBean;
import beans.SchedulerBean;
import beans.SessionBean;
import beans.SubjectBean;
import java.util.List;
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
        if (createNewSession) {
            LogManager.log("New session created!");
        }
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

    public void startFeedbackSession(ScheduledFeedbackBean sfb) {
        SessionBean currentSessionId = UtilsManager.getCurrentSessionId();
        CsfBean csf = new CsfBean();
        csf.setClassId(sfb.getClassId());
        csf.setSessionId(currentSessionId);
        List<CsfBean> listCsf = csf.findByClassIdAndSessionId();
        session.setAttribute("feedback_session_id", session.getId());
        session.setAttribute("feedback_class_id", sfb.getClassId().getId());
        session.setAttribute("feedback_class_code", sfb.getClassId().getClassCodeById());
        session.setAttribute("feedback_current_session_id", currentSessionId);
        session.setAttribute("feedback_csf_list", listCsf);
        LogManager.log("Feedback Session Started!");
        LogManager.log("Feedback Session ID: " + session.getId());
        LogManager.log("Feedback Class ID: " + sfb.getClassId().getId());
        LogManager.log("Feedback Class code: " + sfb.getClassId().getClassCodeById());
        LogManager.log("Feedback Current Session: " + currentSessionId);
        LogManager.log("Feedback Csf list: " + listCsf);
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

    public void invalidateFeedbackSession() {
        session.setAttribute("feedback_session_id", null);
        session.setAttribute("feedback_class_id", null);
        session.setAttribute("feedback_class_code", null);
        session.setAttribute("feedback_current_session_id", null);
        session.setAttribute("feedback_csf_list", null);
        LogManager.log("Feedback session invalidated");
    }

    public void invalidateAllSession() {
        //Invalidate session of faculty and scheduler
        LogManager.log("Invalidating Sessions of faculty and scheduler");
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

    public boolean isFeedbackSession() {
        //Return true if feedback is started
        if (session.getAttribute("feedback_session_id") != null && session.getAttribute("feedback_class_id") != null && session.getAttribute("feedback_current_session_id") != null) {
            return true;
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

    public List getCSFList() {
        if (isFeedbackSession()) {
            return (List) session.getAttribute("feedback_csf_list");
        }
        return null;
    }

    public void updateCSFList(List csfList) {
        if (isFeedbackSession()) {
            session.setAttribute("feedback_csf_list", csfList);
            LogManager.log("Feedback CSF list updated!");
        }
    }

    public CsfBean getFeedbackCSFBean() {
        if (isFeedbackSession()) {
            //if it does not fetch all the details of class faculty and subject then findById function should be call from each one
            if (getCSFList().size() > 0) {
                return ((CsfBean) getCSFList().get(0));
            } else {
                return null;
            }
        }
        return null;
    }

    public ClassBean getFeedbackClassBean() {
        if (isFeedbackSession()) {
            //if all the details are not found then findById function need to be called
            return getFeedbackCSFBean().getClassId();
        }
        return null;
    }

    public DepartmentBean getFeedbackDepartmentBean() {
        if (isFeedbackSession()) {
            return getFeedbackClassBean().getDepartmentId();
        }
        return null;
    }

    public CourseBean getFeedbackCourseBean() {
        if (isFeedbackSession()) {
            return getFeedbackDepartmentBean().getCourseId();
        }
        return null;
    }

    public SubjectBean getFeedbackSubjectBean() {
        if (isFeedbackSession()) {
            return getFeedbackCSFBean().getSubjectId();
        }
        return null;
    }

    public FacultyBean getFeedbackFacultyBean() {
        if (isFeedbackSession()) {
            return getFeedbackCSFBean().getFacultyId();
        }
        return null;
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
