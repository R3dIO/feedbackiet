package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.JsonApiManager;

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
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
