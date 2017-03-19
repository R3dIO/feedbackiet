package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.LogManager;
import managers.SessionManager;
import managers.UtilsManager;

/**
 * @author Sapan
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        SessionManager sm = new SessionManager(request, response, false);
        if (type.equalsIgnoreCase("faculty")) {
            logoutFaculty(request, response);
        } else if (type.equalsIgnoreCase("scheduler")) {
            logoutScheduler(request, response);
        } else {
            logoutAll(request, response);
        }
    }

    public boolean logoutFaculty(HttpServletRequest request, HttpServletResponse response) {
        SessionManager sm = new SessionManager(request, response, false);
        sm.invalidateFacultySession();
        try {
            response.sendRedirect(UtilsManager.getLoginUrl("faculty"));
        } catch (IOException ex) {
            LogManager.log("IOException in SessionManager::logoutFaculty(): " + ex);
            return false;
        }
        LogManager.log("Faculty logout success");
        return true;
    }

    public boolean logoutScheduler(HttpServletRequest request, HttpServletResponse response) {
        SessionManager sm = new SessionManager(request, response, false);
        sm.invalidateSchedulerSession();
        try {
            response.sendRedirect(UtilsManager.getLoginUrl("scheduler"));
        } catch (IOException ex) {
            LogManager.log("IOException in SessionManager::logoutScheduler(): " + ex);
            return false;
        }
        LogManager.log("Scheduler logout success");
        return true;
    }

    public boolean logoutAll(HttpServletRequest request, HttpServletResponse response) {
        SessionManager sm = new SessionManager(request, response, false);
        sm.invalidateAllSession();
        try {
            response.sendRedirect(UtilsManager.getLoginUrl("faculty"));
        } catch (IOException ex) {
            LogManager.log("IOException in SessionManager::logoutAll(): " + ex);
            return false;
        }
        LogManager.log("All logout success");
        return true;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
