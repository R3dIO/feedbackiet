<%@page import="java.util.List"%>
<%@page import="beans.ScheduledFeedbackBean"%>
<%@page import="java.util.Date"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../";
%>
<%-- 
    Document   : scheduler/ViewScheduledFeedback
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
%>
<!DOCTYPE html>
<html> 
    <head>
        <!--HeadTag.jsp file contains Basic head with common css and js-->
        <jsp:include page="../includes/HeadTag.jsp">
            <jsp:param name="title" value="Feedback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <script src="<%=relativeRootPath%>js/jquery.metadata.js" type="text/javascript"></script>
        <script src="<%=relativeRootPath%>js/jquery.tablesorter.min.js" type="text/javascript"></script>

        <script type="text/javascript">
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
                            $("#" + data.classId).remove();
                        } else {
                            $("#message").addClass("text-danger");
                        }
                        $("#message").html(result.message);
                    }
                });
            }
            $(document).ready(function () {
                $.tablesorter.addWidget({
                    // give the widget a id
                    id: "indexFirstColumn",
                    // format is called when the on init and when a sorting has finished
                    format: function (table) {
                        // loop all tr elements and set the value for the first column  
                        for (var i = 0; i < table.tBodies[0].rows.length; i++) {
                            $("tbody tr:eq(" + i + ") td:first", table).html(i + 1);
                        }
                    }
                });
                $("#my_table").tablesorter({
                    widgets: ['zebra', 'indexFirstColumn']
                });
                $("#my_table_without_sno").tablesorter({
                });
            });
            function deleteFeedback(obj) {
                var classId = $(obj).attr("class_id");
                if (classId == -1) {
                    alert("Choose a class to schedule!");
                    return;
                }
                var data = {
                    classId: classId,
                };
                //Delete feedback using JsonApiServlet
                deleteFeedbackScheduleOfClass(data);
            }
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
                    All scheduled feedbacks
                </div>
            </div>
            <hr>
            <div class="row">
                <div id="message" class="col-12 text-center">
                </div>
            </div>
            <form id="scheduler_form">
                <div class="row ">
                    <div class=" col-12">
                        <table class="table tablesorter " id="my_table">
                            <thead>
                                <tr>
                                    <th><a href="#" class="text-white">S.No.</a></th>
                                    <th><a href="#" class="text-white">Course</a></th>
                                    <th><a href="#" class="text-white">Department</a></th>
                                    <th><a href="#" class="text-white">Year</a></th>
                                    <th><a href="#" class="text-white">Section</a></th>
                                    <th><a href="#" class="text-white">Class code</a></th>
                                    <th><a href="#" class="text-white">Start time</a></th>
                                    <th><a href="#" class="text-white">End time</a></th>
                                    <th class="hidden-print"><a href="#" class="text-white">Password</a></th>
                                    <th class="hidden-print"><a href="#" class="text-white">Delete?</a></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<ScheduledFeedbackBean> listSFB = new ScheduledFeedbackBean().findAll();
                                    int sno = 0;
                                    for (ScheduledFeedbackBean sfb : listSFB) {
                                        sno++;
                                %>
                                <tr id="<%=sfb.getClassId().getId()%>">
                                    <td class=""><%=sno%></td>
                                    <td class=""><%=sfb.getClassId().getDepartmentId().getCourseId().getCourseCode()%></td>
                                    <td class=""><%=sfb.getClassId().getDepartmentId().getDepartmentCode()%></td>
                                    <td class=""><%=sfb.getClassId().getYear()%></td>
                                    <td class=""><%=sfb.getClassId().getSection() != null ? sfb.getClassId().getSection() : ""%></td>
                                    <td class="text-primary"><%=sfb.getClassId().getClassCodeById()%></td>
                                    <td class=""><%=UtilsManager.getDateStringFromTimestamp(sfb.getStartTime())%></td>
                                    <td class=""><%=UtilsManager.getDateStringFromTimestamp(sfb.getEndTime())%></td>
                                    <td class="text-warning hidden-print"><%=sfb.getPassword()%></td>
                                    <td class="hidden-print"><input type="button" class="btn btn-primary" id="delete_feedback_button" onclick="deleteFeedback(this);" class_id="<%=sfb.getClassId().getId()%>" value="Delete"/></td>
                                </tr>
                                <%}
                                    if (sno == 0) {
                                        out.println("<td></td><td class='text-center' colspan=\"9\"><b>No class scheduled yet</b></td>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>

