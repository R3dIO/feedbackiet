package servlet;

import beans.ClassBean;
import beans.CsfBean;
import beans.FeedbackBean;
import beans.ScheduledFeedbackBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.LogManager;
import managers.SessionManager;
import managers.UtilsManager;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Sapan
 */
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sm = new SessionManager(req, resp, false);
        String purpose = req.getParameter("purpose");
        if (purpose.equalsIgnoreCase("logout")) {
            sm.invalidateFeedbackSession();
            LogManager.log("Feedback done! Redirect to Thank you page");
            resp.sendRedirect(UtilsManager.getThankYouPage());
        } else {
            super.doGet(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sm = new SessionManager(req, resp, false);
        String purpose = req.getParameter("purpose");
        if (purpose.equalsIgnoreCase("startFeedbackSession")) {
            startFeedbackSession(req, resp);
            //redirect command is given nothing should be done now
        } else if (purpose.equalsIgnoreCase("addFeedback")) {
            if (sm.isFeedbackSession() && addFeedback(req, resp, (CsfBean) sm.getFeedbackCSFBean())) {//true if Feedback is added successfully after validation
                //Updating the CSF List in session variable
                List csfList = sm.getCSFList();
                String location = UtilsManager.getHomeUrl("student");
                //delete the zeroth index csf 
                csfList.remove(0);
                //updating the session csf list with the above csf list
                sm.updateCSFList(csfList);
                LogManager.log("Redirect to studentFeedbackPage!");
                location = UtilsManager.getStudentFeedbackUrl();
                resp.sendRedirect(location);
            } else {
//                super.doPost(req, resp);
            }
        } else {
            super.doPost(req, resp);
        }
    }

    private void startFeedbackSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String classId = req.getParameter("class_id");
        String password = req.getParameter("password");
        ScheduledFeedbackBean helperSFB = new ScheduledFeedbackBean();
        helperSFB.setClassId(new ClassBean(Long.parseLong(classId)));
        helperSFB.setPassword(password);
        ScheduledFeedbackBean sfb = helperSFB.findByClassIdAndPassword();
        String location = UtilsManager.getHomeUrl("student");
        if (sfb == null) {
            //Wrong password or class_id! Redirect to student home     
            location = UtilsManager.getHomeUrl("student") + "?login_error=Incorrect class or password";
            LogManager.log("Wrong password or class_id! Redirect to student home");
        } else {
            //Correct class_id and password! Redirect to feedback page
            SessionManager sm = new SessionManager(req, resp, true);
            sm.startFeedbackSession(sfb);
            location = UtilsManager.getStudentFeedbackUrl();
            LogManager.log("Correct class_id and password!Creating session & Redirect to feedback page");
        }
        resp.sendRedirect(location);
    }

    private boolean addFeedback(HttpServletRequest request, HttpServletResponse response, CsfBean csf) throws IOException {
        //validate and add a new feedback
        if (csf == null) {
            LogManager.log("No CSF! FeedbackServlet::AddFeedback()");
            response.sendRedirect(UtilsManager.getThankYouPage());
            return false;
        }
        int attribute1 = 0, attribute2 = 0, attribute3 = 0, attribute4 = 0, attribute5 = 0, attribute6 = 0, attribute7 = 0, attribute8 = 0, attribute9 = 0, attribute10 = 0, attribute11 = 0, attribute12 = 0, attribute13 = 0, attribute14 = 0, attribute15 = 0;
        try {
            attribute1 = Integer.parseInt(request.getParameter("attribute1"));
            attribute2 = Integer.parseInt(request.getParameter("attribute2"));
            attribute3 = Integer.parseInt(request.getParameter("attribute3"));
            attribute4 = Integer.parseInt(request.getParameter("attribute4"));
            attribute5 = Integer.parseInt(request.getParameter("attribute5"));
            attribute6 = Integer.parseInt(request.getParameter("attribute6"));
            attribute7 = Integer.parseInt(request.getParameter("attribute7"));
            attribute8 = Integer.parseInt(request.getParameter("attribute8"));
            attribute9 = Integer.parseInt(request.getParameter("attribute9"));
            attribute10 = Integer.parseInt(request.getParameter("attribute10"));
            attribute11 = Integer.parseInt(request.getParameter("attribute11"));
            attribute12 = Integer.parseInt(request.getParameter("attribute12"));
            attribute13 = Integer.parseInt(request.getParameter("attribute13"));
            attribute14 = Integer.parseInt(request.getParameter("attribute14"));
            attribute15 = Integer.parseInt(request.getParameter("attribute15"));
        } catch (NumberFormatException | NullPointerException e) {
            LogManager.log("Numberformat or Nullpointer exception in FeedbackServlet::addFeedback() while parsing attributes" + e);
            response.sendRedirect(UtilsManager.getStudentFeedbackUrl() + "?error=Either any attribute is not submitted or attributes value is not integer!");
            return false;
        }

        String comment1 = request.getParameter("comment1");
        String comment2 = request.getParameter("comment2");
        String comment3 = request.getParameter("comment3");
        try {
            comment1 = StringEscapeUtils.escapeHtml4(comment1);
            comment2 = StringEscapeUtils.escapeHtml4(comment2);
            comment3 = StringEscapeUtils.escapeHtml4(comment3);
        } catch (NullPointerException e) {
            LogManager.log("Nullpointer exception in FeedbackServlet::addFeedback() while parsing comments" + e);
            response.sendRedirect(UtilsManager.getStudentFeedbackUrl() + "?error=Some of the comments are not submitted!");
            return false;
        }
        int timestamp = UtilsManager.getCurrentTimestampInSeconds();
        FeedbackBean fb = new FeedbackBean(null, timestamp, attribute1, attribute2, attribute3, attribute4, attribute5, attribute6, attribute7, attribute8, attribute9, attribute10, attribute11, attribute12, attribute13, attribute14, attribute15, comment1, comment2, comment3);
        fb.setIpAddress(request.getRemoteAddr());
        fb.setCsfId(csf);
        if (fb.validateFields()) {
            if (fb.persist(fb)) {
                //do not redirect here. some processing needs to be done and that will be done in doPost() method
                return true;
            } else {
                LogManager.log("Could not add feedback!");
                LogManager.log(feedbackAsJson(fb));
                response.sendRedirect(UtilsManager.getStudentFeedbackUrl() + "?error = Could not add feedback! Please resubmit.");
                return false;
            }
        } else {
            LogManager.log("Feedback validate failed!");
            LogManager.log(feedbackAsJson(fb));
            response.sendRedirect(UtilsManager.getStudentFeedbackUrl() + "?error=Feedback validate failed! Please resubmit correct data.");
            return false;
        }
    }

    private String feedbackAsJson(FeedbackBean fb) {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gb.create();
        return (String) gson.toJson(fb);
    }
}
