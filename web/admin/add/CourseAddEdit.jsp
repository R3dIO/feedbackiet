<%@page import="beans.CourseBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/CourseAddUpdate
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%@page import="managers.LogManager"%>
<%@page import="java.io.IOException"%>
<%@page import="managers.SessionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SessionManager sm = new SessionManager(request, response, false);
    if (!sm.isAdminSession()) {
        response.sendRedirect(relativeRootPath + UtilsManager.getLoginUrl("admin"));
        return;
    }
    CourseBean cb = null;
    String purpose = "addCourse";
    if (request.getParameter("edit") != null) {
        purpose = "editCourse";
        cb = new CourseBean(Long.parseLong(request.getParameter("edit"))).findById();
    }
    String courseCode = "", courseName = "";
    long duration = 0 , courseId = -1;
    if (cb != null) {
        courseId = cb.getId();
        courseCode = cb.getCourseCode();
        courseName = cb.getCourseName();
    }
    String result = request.getParameter("result");
    boolean success = false;
    String message = "";
    if (result != null) {
        if (result.equalsIgnoreCase("success")) {
            success = true;
        } else if (result.equalsIgnoreCase("fail")) {
            success = false;
        }
        message = request.getParameter("message");
    }
%>
<!DOCTYPE html>
<html> 
    <head>
        <!--HeadTag.jsp file contains Basic head with common css and js-->
        <jsp:include page="../../includes/HeadTag.jsp">
            <jsp:param name="title" value="Feedback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
    </head>
    <body>
        <%
            String logoutUrl = relativeRootPath + "LogoutServlet?type=faculty";
        %>
        <jsp:include page="../../includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <!--Check below file to see parameters functionality-->
        <jsp:include page="../../includes/Navbar.jsp">
            <jsp:param name="admin" value="active"/>
            <jsp:param name="logout_url" value="<%=logoutUrl%>"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container text-center">
            <hr>
            <div class="row">
                <div class="col-12 text-capitalize h4 text-info">
                    <%=purpose%>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-12 <%=success ? "text-success" : "text-danger"%>">
                    <%=message%>
                </div>
            </div>
            <div class="row text-left">
                <div class="offset-2 col-8">
                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                        <input type="hidden" value="<%=purpose%>" name="purpose"/>  
                        <input type="hidden" value="<%=courseId%>" name="course_id"/>
                        <table class="table table-success">
                            <tbody>
                                <tr>
                                    <td>Course code</td>
                                    <td><input type="text" required class="form-control" value="<%=courseCode%>" name="course_code"></td>
                                </tr>
                                <tr>
                                    <td>Course Name</td>
                                    <td><input type="text" required class="form-control" value="<%=courseName%>" name="course_name"></td>
                                </tr>
                                <tr>
                                    <td>Duration(in years)</td>
                                    <td><input type="number" required class="form-control" value="<%=duration%>" name="duration"></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input type="submit" class="form-control btn btn-success" value="Submit"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
