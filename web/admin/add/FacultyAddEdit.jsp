<%@page import="beans.FacultyBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/FacultyAddUpdate
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
    FacultyBean fb = null;
    String purpose = "addFaculty";
    if (request.getParameter("edit") != null) {
        purpose = "editFaculty";
        fb = new FacultyBean(Long.parseLong(request.getParameter("edit"))).findById();
    }
    String title = "", firstName = "", lastName = "", designation = "", email = "", phone = "";
    long courseId = -1, departmentId = -1, facultyId = -1;
    if (fb != null) {
        facultyId = fb.getId();
        title = fb.getTitle();
        firstName = fb.getFirstName();
        lastName = fb.getLastName();
        designation = fb.getDesignation();
        email = fb.getEmail();
        phone = fb.getPhone();
        courseId = fb.getDepartmentId().getCourseId().getId();
        departmentId = fb.getDepartmentId().getId();
    }
    String result = request.getParameter("result");
    boolean success = false;
    String message = "";
    if (result != null) {
        if (result.equalsIgnoreCase("success")) {
            success = true;
        }else if(result.equalsIgnoreCase("fail")){
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
            var departmentId = <%=departmentId%>;
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
                            findDepartmentByCourseId(courseId);
                        }
                    }});
            }
            function findDepartmentByCourseId(courseId) {
                $.ajax({
                    type: "post",
                    data: {courseId: courseId, purpose: "findDepartmentByCourseId"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var departmentArray = result;
                        for (var i = 0; i < departmentArray.length; i++) {
                            var department = departmentArray[i];
                            $("#select_department").append('<option code="' + department.departmentCode + '" value="' + department.id + '">' + department.departmentCode + ": " + department.departmentName + '</option>');
                        }
                        if (departmentId != -1)
                            $("#select_department").val(departmentId);
                    }});
            }
            $(document).ready(function () {
                findAllCourses();
                $("#select_course").on('change', function () {
                    var courseId = this.value;
                    $("#select_department").html("<option value=\"-1\">Select Department</option>");
                    $("#select_class").html("<option value=\"-1\">Select Class</option>");
                    findDepartmentByCourseId(courseId);
                });
            });
        </script>
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
                <div class="col-12 <%=success?"text-success":"text-danger"%>">
                    <%=message %>
                </div>
            </div>
            <div class="row text-left">
                <div class="offset-2 col-8">
                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                        <input type="hidden" value="<%=purpose%>" name="purpose"/>  
                        <input type="hidden" value="<%=facultyId%>" name="faculty_id"/>
                        <table class="table table-success">
                            <tbody>
                                <tr>
                                    <td>Title</td>
                                    <td><input type="text" class="form-control" value="<%=title%>" name="title"></td>
                                </tr>
                                <tr>
                                    <td>First Name</td>
                                    <td><input type="text" required class="form-control" value="<%=firstName%>" name="first_name"></td>
                                </tr>
                                <tr>
                                    <td>Last Name</td>
                                    <td><input type="text" required class="form-control" value="<%=lastName%>" name="last_name"></td>
                                </tr>
                                <tr>
                                    <td>Designation</td>
                                    <td><input type="text" required class="form-control" value="<%=designation%>" name="designation"></td>
                                </tr>
                                <tr>
                                    <td>Email <span class="text-warning">(Unique)</span></td>
                                    <td><input type="text" required class="form-control" value="<%=email%>" name="email"></td>
                                </tr>
                                <tr>
                                    <td>Phone</td>
                                    <td><input type="text" required class="form-control" value="<%=phone%>" name="phone"></td>
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
                                    <td>Department</td>
                                    <td>
                                        <select class="form-control" required name="department_id" id="select_department">
                                            <option value="-1">Select Department</option>
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
