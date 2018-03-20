<%@page import="beans.ClassBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/ClassAddEdit
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
    ClassBean cb = null;
    String purpose = "addClass";
    if (request.getParameter("edit") != null) {
        purpose = "editClass";
        cb = new ClassBean(Long.parseLong(request.getParameter("edit"))).findById();
    }
    String section = "";
    long courseId = -1, classId = -1, departmentId = -1, facultyId = -1, year = -1;
    if (cb != null) {
        classId = cb.getId();
        year = cb.getYear();
        section = cb.getSection();
        courseId = cb.getDepartmentId().getCourseId().getId();
        departmentId = cb.getDepartmentId().getId();
        facultyId = cb.getFacultyId().getId();
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
            <jsp:param name="title" value="Feecback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <script>
            var courseId = <%=courseId%>;
            var departmentId = <%=departmentId%>;
            var facultyId = <%=facultyId%>;
            var year = <%=year%>;
            var section = "<%=section%>";
            function findAllFaculty() {
                $("#select_faculty").html("<option value=\"-1\">Select Faculty</option>");
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
                $("#select_course").html("<option value=\"-1\">Select Course</option>");
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
                findAllFaculty();
                $("#select_course").on('change', function () {
                    var courseId = this.value;
                    $("#select_department").html("<option value=\"-1\">Select Department</option>");
                    $("#select_class").html("<option value=\"-1\">Select Class</option>");
                    findDepartmentByCourseId(courseId);
                });
                if (section != "") {
                    $("#select_section").val(section);
                }
                if (year != -1) {
                    $("#select_year").val(year);
                }
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
                        <input type="hidden" value="<%=classId%>" name="class_id"/>
                        <table class="table table-success">
                            <tbody>
                                <tr>
                                    <td>Course</td>
                                    <td>
                                        <select class="form-control" required id="select_course">
                                            <option value="-1">Select Course</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Department</td>
                                    <td>
                                        <select required class="form-control" required name="department_id" id="select_department">
                                            <option value="-1">Select Department</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Year</td>
                                    <td>
                                        <select required class="form-control" required name="year" id="select_year">
                                            <option value="-1">Select Year</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Section</td>
                                    <td>
                                        <select  class="form-control" required name="section" id="select_section">
                                            <option value="">Select Section</option>
                                            <option value="A">A</option>
                                            <option value="B">B</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Class teacher</td>
                                    <td>
                                        <select  class="form-control" name="faculty_id" id="select_faculty">
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
