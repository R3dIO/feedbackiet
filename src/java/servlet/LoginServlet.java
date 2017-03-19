package servlet;

import beans.LoginBean;
import beans.SchedulerBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.SessionManager;
import managers.UtilsManager;

/**
 * @author Sapan
 */
public class LoginServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new ServletException("Get not supported");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("login_type").equalsIgnoreCase("faculty")) {
            //Faculty login request
            new SessionManager(request, response, false).invalidateFacultySession();
            facultyLogin(request, response);
        } else if (request.getParameter("login_type").equalsIgnoreCase("scheduler")) {
            //Scheduler login request
            new SessionManager(request, response, false).invalidateSchedulerSession();
            schedulerLogin(request, response);
        } else {
            //Redirect to index page if neither faculty nor scheduler
            SessionManager sm = new SessionManager(request, response, false);
            sm.invalidateAllSession();
            response.sendRedirect(UtilsManager.getLoginUrl("faculty"));
        }
    }

    private void facultyLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginBean helperLoginBean = new LoginBean(username, password);
        LoginBean loginBean = helperLoginBean.findByUsernameAndPassword();
        String location = UtilsManager.getLoginUrl("faculty");
        if (loginBean == null) {
            //Login Failed! Redirect to index page or login page
            location = UtilsManager.getLoginUrl("faculty") + "?login_error=Incorrect login details";
        } else {
            String type = loginBean.getType();
            SessionManager sm = new SessionManager(request, response, true);
            if (type.equalsIgnoreCase("faculty")) {
                //Login Success! Redirect to Faculty Home Page
                location = UtilsManager.getHomeUrl("faculty");
                sm.startFacultySession(loginBean);
            } else if (type.equalsIgnoreCase("hod")) {
                //Login Success! Redirect to HOD Home Page
                location = UtilsManager.getHomeUrl("hod");
                sm.startFacultySession(loginBean);
            } else if (type.equalsIgnoreCase("director")) {
                //Login Success! Redirect to Director Home Page
                location = UtilsManager.getHomeUrl("director");
                sm.startFacultySession(loginBean);
            } else if (type.equalsIgnoreCase("admin")) {
                //Login Success! Redirect to Admin Home Page
                location = UtilsManager.getHomeUrl("admin");
                sm.startFacultySession(loginBean);
            } else {
                //Login Failed! Redirect to index page or login page
                location = UtilsManager.getLoginUrl("faculty") + "?login_error=Incorrect login details";
            }
        }
        response.sendRedirect(location);
    }

    private void schedulerLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SchedulerBean helperSB = new SchedulerBean(username, password);
        SchedulerBean schedulerBean = helperSB.findByUsernameAndPassword();
        String location = UtilsManager.getLoginUrl("scheduler");
        if (schedulerBean == null) {
            //Login Failed! Redirect to scheduler login page
            location = UtilsManager.getLoginUrl("scheduler") + "?login_error=Incorrect login details";
        } else {
            //Login Success! Redirect to scheduler index page
            SessionManager sm = new SessionManager(request, response, true);
            sm.startSchedulerSession(schedulerBean);
            location = UtilsManager.getHomeUrl("scheduler");
        }
        response.sendRedirect(location);
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "This Servlet is used for login and redirecting user to proper home page";
    }// </editor-fold>

}
