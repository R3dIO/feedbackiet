<%@page import="beans.DepartmentBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/DepartmentAddEdit
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
    DepartmentBean db = null;
    String purpose = "addDepartment";
    if (request.getParameter("edit") != null) {
        purpose = "editDepartment";
        db = new DepartmentBean(Long.parseLong(request.getParameter("edit"))).findById();
    }
    String departmentCode = "", departmentName = "";
    long courseId = -1, departmentId = -1, facultyId = -1;
    if (db != null) {
        departmentId = db.getId();
        departmentCode = db.getDepartmentCode();
        departmentName = db.getDepartmentName();
        courseId = db.getCourseId().getId();
        facultyId = db.getFacultyId().getId();
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
        <script>
            var courseId = <%=courseId%>;
            var facultyId = <%=facultyId%>;
            var departmentId = <%=departmentId%>;
            function findAllFaculty() {
                $("#select_faculty").html("<option value=\" - 1\">Select Faculty</option>");
                $.ajax({
                    type: "post",
                    data: {purpose: "findAllFaculty"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var facultyArray = result;
                        for (var i = 0; i < facultyArray.length; i++) {
                            var faculty = facultyArray[i];
                            $("#select_faculty").append('<option value="' + faculty.id + '">' + faculty.firstName + " " + faculty.lastName + '</option>');
                        }
                        if (facultyId != -1) {
                            $("#select_faculty").val(facultyId);
                        }
                    }});
            }
            function findAllCourses() {
                $("#select_course").html("<option value=\" - 1\">Select Course</option>");
                $.ajax({
                    type: "post",
                    data: {purpose: "findAllCourses"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var courseArray = result;
                        for (var i = 0; i < courseArray.length; i++) {
                            var course = courseArray[i];
                            $("#select_course").append('<option code="' + course.courseCode + '" value="' + course.id + '">' + course.courseCode + ": " + course.courseName + '</option>');
                        }
                        if (courseId != -1) {
                            $("#select_course").val(courseId);
                        }
                    }});
            }
            $(document).ready(function () {
                findAllCourses();
                findAllFaculty();
            });
        </script>
    </head>
    <body>
        <%String logoutUrl = relativeRootPath + "LogoutServlet?type=faculty";%>
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
            <!--            <hr>
                        Welcome <%=sm.getFaculty().getFirstName()%> as Admin-->
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
                        <input type="hidden" value="<%=departmentId%>" name="department_id"/>
                        <table class="table table-success">
                            <tbody>
                                <tr>
                                    <td>Department Code</td>
                                    <td><input type="text" required class="form-control" value="<%=departmentCode%>" name="department_code"></td>
                                </tr>
                                <tr>
                                    <td>Department Name</td>
                                    <td><input type="text" required class="form-control" value="<%=departmentName%>" name="department_name"></td>
                                </tr>
                                <tr>
                                    <td>Course</td>
                                    <td>
                                        <select class="form-control" required name="course_id" id="select_course">
                                            <option value="-1">Select Course</option>
                                        </select>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>HOD</td>
                                    <td>
                                        <select class="form-control" required name="faculty_id" id="select_faculty">
                                            <option value="-1">Select Faculty</option>
                                        </select>
                                    </td>
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
