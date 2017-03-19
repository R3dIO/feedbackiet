package servlet;

import beans.ClassBean;
import beans.ScheduledFeedbackBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.JsonApiManager;
import managers.UtilsManager;

/**
 *
 * @author Sapan
 */
public class JsonApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Here We only call the functions.
        //Functions are defined in JsonApiManager
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String purpose = request.getParameter("purpose");
        JsonApiManager jam = new JsonApiManager(request, response);
        if (purpose.equalsIgnoreCase("findAllCourses")) {
            //Find All Courses
            out.print(jam.findAllCourses());
        } else if (purpose.equalsIgnoreCase("findDepartmentByCourseId")) {
            //Find Departments of a Course
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            out.print(jam.findDepartmentByCourseId(courseId));
        } else if (purpose.equalsIgnoreCase("findClassByDepartmentId")) {
            //Find Classes of a Department
            Long departmentId = Long.parseLong(request.getParameter("departmentId"));
            out.print(jam.findClassByDepartmentId(departmentId));
        } else if (purpose.equalsIgnoreCase("findClassByDepartmentIdInScheduledFeedback")) {
            //Find Classes of a Department which are scheduled yet for a Feedback
            Long departmentId = Long.parseLong(request.getParameter("departmentId"));
            out.print(jam.findClassByDepartmentIdInScheduledFeedback(departmentId));
        } else if (purpose.equalsIgnoreCase("findClassByDepartmentIdNotInScheduledFeedback")) {
            //Find Classes of a Department which are not scheduled yet for a Feedback
            Long departmentId = Long.parseLong(request.getParameter("departmentId"));
            out.print(jam.findClassByDepartmentIdNotInScheduledFeedback(departmentId));
        } else if (purpose.equalsIgnoreCase("scheduleClassFeedback")) {
            //Schedule a class for feedback i.e. add a record in scheduled_feedback_table
            //Also Edit a feedback schedule of a class i.e. update record
            int startTime = Integer.parseInt(request.getParameter("startTime"));
            int timeSlotInSecond = UtilsManager.getFeedbackTimeslotInSeconds();
            ScheduledFeedbackBean sfb = new ScheduledFeedbackBean();
            sfb.setClassId(new ClassBean(Long.parseLong(request.getParameter("classId"))));
            sfb.setStartTime(startTime);
            sfb.setPassword(request.getParameter("password"));
            sfb.setEndTime(startTime + timeSlotInSecond);
            out.print(jam.scheduleClassFeedback(sfb));
        } else if (purpose.equalsIgnoreCase("deleteFeedbackScheduleOfClass")) {
            //Delete feedback schedule of a class i.e. add a record in scheduled_feedback_table
            ScheduledFeedbackBean sfb = new ScheduledFeedbackBean();
            sfb.setClassId(new ClassBean(Long.parseLong(request.getParameter("classId"))));
            out.print(jam.deleteFeedbackScheduleOfClass(sfb));
        }else if(purpose.equalsIgnoreCase("findAllFeedbackEligibleClass")){
            //Find those class which can give feedback at current time
            out.print(jam.findAllFeedbackEligibleClass());
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
