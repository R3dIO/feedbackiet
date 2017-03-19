<%@page import="java.util.Date"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../";
%>
<%-- 
    Document   : scheduler/ScheduleFeedback
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%@page import="managers.LogManager"%>
<%@page import="java.io.IOException"%>
<%@page import="managers.SessionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SessionManager sm = new SessionManager(request, response, false);
    if (!sm.isSchedulerSession()) {
        response.sendRedirect(relativeRootPath + UtilsManager.getLoginUrl("scheduler"));
        return;
    }
    boolean isEditPage = (request.getParameter("edit") == null) ? false : true;
    boolean isDeletePage = (request.getParameter("delete") == null) ? false : true;
    boolean isNewSchedulePage = false;
    if (isEditPage && isDeletePage) {
        //if both are true
        isNewSchedulePage = true;
        isEditPage = false;
        isDeletePage = false;
    } else if (!isEditPage && !isDeletePage) {
        //if both are false
        isNewSchedulePage = true;
        isEditPage = false;
        isDeletePage = false;
    } else {
        //if one of them is true
        isNewSchedulePage = false;
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
        <script src="<%=relativeRootPath%>js/moment.js" type="text/javascript"></script>
        <script src="<%=relativeRootPath%>js/moment-timezone-with-data.js" type="text/javascript"></script>
        <script type="text/javascript">
            var isNewSchedulePage =<%=isNewSchedulePage ? "true" : "false"%>;
            var isEditPage =<%=isEditPage ? "true" : "false"%>;
            var isDeletePage =<%=isDeletePage ? "true" : "false"%>;
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
            function findClassByDepartmentIdInScheduledFeedback(departmentId) {
                $.ajax({
                    type: "post",
                    data: {departmentId: departmentId, purpose: "findClassByDepartmentIdInScheduledFeedback"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var classArray = result;
                        var courseCode = $("#select_course option:selected").attr("code");
                        var departmentCode = $("#select_department option:selected").attr("code");
                        for (var i = 0; i < classArray.length; i++) {
                            var class_ = classArray[i];
                            var year = class_.year;
                            var section = class_.section == null ? "" : class_.section;
                            $("#select_class").append('<option code="' + courseCode + year + departmentCode + section + '" value="' + class_.id + '">' + courseCode + " " + year + " " + departmentCode + " " + section + ' </option>');
                        }
                    }});
            }
            function findClassByDepartmentIdNotInScheduledFeedback(departmentId) {
                $.ajax({
                    type: "post",
                    data: {departmentId: departmentId, purpose: "findClassByDepartmentIdNotInScheduledFeedback"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var classArray = result;
                        var courseCode = $("#select_course option:selected").attr("code");
                        var departmentCode = $("#select_department option:selected").attr("code");
                        for (var i = 0; i < classArray.length; i++) {
                            var class_ = classArray[i];
                            var year = class_.year;
                            var section = class_.section == null ? "" : class_.section;
                            $("#select_class").append('<option code="' + courseCode + year + departmentCode + section + '" value="' + class_.id + '">' + courseCode + " " + year + " " + departmentCode + " " + section + ' </option>');
                        }
                    }});
            }
            function scheduleClassFeedback(data) {
                data.purpose = "scheduleClassFeedback";
                $.ajax({
                    type: "post",
                    data: data,
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var success = result.success;
                        if (success) {
                            $("#message").addClass("text-success");
                            if (!isEditPage) {
                                $("#select_class option:selected").remove();
                            }
                        } else {
                            $("#message").addClass("text-danger");
                        }
                        $("#message").html(result.message);
                    }
                });
            }
            function deleteFeedbackScheduleOfClass(data) {
                data.purpose = "deleteFeedbackScheduleOfClass";
                $.ajax({
                    type: "post",
                    data: data,
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var success = result.success;
                        if (success) {
                            $("#message").addClass("text-success");
                            $("#select_class option:selected").remove();
                        } else {
                            $("#message").addClass("text-danger");
                        }
                        $("#message").html(result.message);
                    }
                });
            }
            $(document).ready(function () {
                findAllCourses();
                $("#select_course").on('change', function () {
                    var courseId = this.value;
                    $("#select_department").html("<option value=\" - 1\">Select Department</option>");
                    $("#select_class").html("<option value=\" - 1\">Select Class</option>");
                    findDepartmentByCourseId(courseId);
                });
                $("#select_department").on('change', function () {
                    var departmentId = this.value;
                    $("#select_class").html("<option value=\" - 1\">Select Class</option>");
                    if (isEditPage || isDeletePage) {
                        findClassByDepartmentIdInScheduledFeedback(departmentId);
                    } else if (isNewSchedulePage) {
                        findClassByDepartmentIdNotInScheduledFeedback(departmentId);
                    }
                });
                $("#schedule_feedback_button").on('click', function () {
                    var classId = $("#select_class option:selected").attr("value");
                    if (isNewSchedulePage || isEditPage) {
                        var date_time_picker = $("#date_time_picker").val();
                        var password = $("#password").val();
                        password = password.trim();
                        if (classId == -1) {
                            alert("Choose a class!");
                            return;
                        } else if (!moment(date_time_picker, 'DD-MM-YYYY HH:mm', true).isValid()) {
                            alert("Choose correct date and time!");
                            return;
                        } else if (password === "") {
                            alert("Password can't be empty!");
                            return;
                        }
                        var startTime = moment(date_time_picker, 'DD-MM-YYYY HH:mm').unix();
                        var data = {
                            classId: classId,
                            startTime: startTime,
                            password: password
                        };
                        scheduleClassFeedback(data);
                    } else if (isDeletePage) {
                        if (classId == -1) {
                            alert("Choose a class to schedule!");
                            return;
                        }
                        var data = {
                            classId: classId,
                        };
                        deleteFeedbackScheduleOfClass(data);
                    }
                });
                $('#date_time_picker').datetimepicker({
                    format: 'd-m-Y H:i',
                    step: 15,
                });
//                $("#scheduler_form").on("submit",function (){
//                    $("#schedule_feedback_button").click();
//                });
            });
        </script>
    </head>
    <body>
        <%
            String logoutUrl = relativeRootPath + "LogoutServlet?type=scheduler";
        %>

        <jsp:include page="../includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <!--Check below file to see parameters functionality-->
        <jsp:include page="../includes/Navbar.jsp">
            <jsp:param name="faculty" value=""/>
            <jsp:param name="scheduler" value="active"/>
            <jsp:param name="logout_url" value="<%=logoutUrl%>"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container ">
            <hr>
            <!--            <div class="row text-center">
                            <div class="col-12">
                                Welcome <%=sm.getSchedulerName()%> as Scheduler
                            </div>
                        </div>-->
            <div class="row">
                <div class="col-12 text-center text-primary h4">
                    <%=isNewSchedulePage ? "Schedule feedback of a new class" : ""%>
                    <%=isEditPage ? "Edit feedback schedule of a class" : ""%>
                    <%=isDeletePage ? "Delete feedback schedule of a class" : ""%>
                </div>
            </div>
            <hr>
            <div class="row">
                <div id="message" class="col-12 text-center">
                </div>
            </div>
            <form id="scheduler_form">
                <div class="row ">
                    <div class="offset-3 col-7">
                        <table class="table ">
                            <tr>
                                <td class=" text-warning">Select Course</td>
                                <td class="">
                                    <select class="form-control" id="select_course">
                                        <option value="-1">Select Course</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class=" text-warning">Select Department</td>
                                <td class="">
                                    <select class="form-control" id="select_department">
                                        <option value="-1">Select Department</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class=" text-warning">Select Class</td>
                                <td class="">
                                    <select class="form-control" id="select_class">
                                        <option value="-1">Select Class</option>  
                                    </select>
                                </td>
                            </tr>
                            <% if (isNewSchedulePage || isEditPage) {%>
                            <tr>
                                <td class=" text-warning">Select Start Time</td>
                                <td class="">
                                    <input required id="date_time_picker" class="form-control" placeholder="DD-MM-YYYY hh:mm" type="text" >  
                                    <!--<input type="datetime-local" id="date_time_picker" />-->
                                </td>
                            </tr>
                            <!--                        <tr>
                                                        <td class=" text-warning">Select Timeslot (in min)</td>
                                                        <td class="">
                                                            <input type="number" min="0" class="form-control" id="timeslot" value="<%=UtilsManager.getFeedbackTimeslot()%>" />
                                                        </td>
                                                    </tr>-->
                            <tr>
                                <td class=" text-warning">Select Password</td>
                                <td class="">
                                    <input required type="password" class="form-control" placeholder="Password" id="password"/>
                                </td>
                            </tr>
                            <%}%>
                            <tr>
                                <td class=" text-warning"></td>
                                <td class="">
                                    <input type="button" class="btn btn-primary" id="schedule_feedback_button" value="<%=isNewSchedulePage ? "Schedule" : ""%><%=isEditPage ? "Edit schedule" : ""%><%=isDeletePage ? "Delete schedule" : ""%>"  />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>

