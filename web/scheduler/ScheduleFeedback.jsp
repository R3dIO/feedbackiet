<%
    String relativeRootPath = "../";
%>
<%-- 
    Document   : scheduler/index
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%@page import="managers.LogManager"%>
<%@page import="java.io.IOException"%>
<%@page import="managers.SessionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SessionManager sm = new SessionManager(request, response, false);
    String onFailUrl = relativeRootPath + "SchedulerLogin.jsp";
    if (!sm.checkSchedulerSession(onFailUrl)) {
        return;
    }
%>
<!DOCTYPE html>
<html> 
    <head>
        <!--HeadTag.jsp file contains Basic head with common css and js-->
        <jsp:include page="../includes/HeadTag.jsp">
            <jsp:param name="title" value="Feedback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <script type="text/javascript">
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
                    }});
            }
            function findClassByDepartmentId(departmentId) {
                $.ajax({
                    type: "post",
                    data: {departmentId: departmentId, purpose: "findClassByDepartmentId"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var classArray = result;
                        var courseCode = $("#select_course option:selected").attr("code");
                        var departmentCode = $("#select_department option:selected").attr("code");
                        for (var i = 0; i < classArray.length; i++) {
                            var class_ = classArray[i];
                            var year = class_.year;
                            var section = class_.section == null ? "" : class_.section;
                            $("#select_year_and_section").append('<option code="' + courseCode + year + departmentCode + section + '" value="' + class_.id + '">' + courseCode + " " + year + " " + departmentCode + " " + section + ' </option>');
                        }
                    }});
            }
            $(document).ready(function () {
                findAllCourses();
                $("#select_course").on('change', function () {
                    var courseId = this.value;
                    $("#select_department").html("<option value=\" - 1\">Select Department</option>");
                    $("#select_year_and_section").html("<option value=\" - 1\">Select Year & Section</option>");
                    findDepartmentByCourseId(courseId);
                });
                $("#select_department").on('change', function () {
                    var departmentId = this.value;
                    $("#select_year_and_section").html("<option value=\" - 1\">Select Year & Section</option>");
                    findClassByDepartmentId(departmentId);
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="../includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <jsp:include page="../includes/Navbar.jsp">
            <jsp:param name="home" value="active"/>
            <jsp:param name="features" value=""/>
            <jsp:param name="pricing" value=""/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container ">
            <hr>
            <div class="row text-center">
                <div class="col-12">
                    Welcome <%=sm.getSchedulerName()%> as Scheduler
                </div>
            </div>
            <hr>
            <div class="row ">
                <div class="offset-3 col-7">
                    <table class="table">
                        <tbody>
                            <tr>
                                <th>Select Course</th>
                                <td>
                                    <select class="form-control" id="select_course">
                                        <option value="-1">Select Course</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>Select Department</th>
                                <td>
                                    <select class="form-control" id="select_department">
                                        <option value="-1">Select Department</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>Select Year & Section</th>
                                <td>
                                    <select class="form-control" id="select_year_and_section">
                                        <option value="-1">Select Year & Section</option>  
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
