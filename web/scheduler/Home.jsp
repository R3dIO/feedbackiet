<%@page import="managers.UtilsManager"%>
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
        <div class="container text-center">
            <hr>
            Welcome <%=sm.getSchedulerName()%> as Scheduler
            <hr>
            <div class="row text-center ">
                <div class="col-4">
                    <a href="ScheduleFeedback.jsp" class="btn btn-info">Schedule feedback for a class</a>
                </div>
                <div class="col-4">
                    <a href="ScheduleFeedback.jsp?edit" class="btn btn-info">Edit a feedback schedule</a>
                </div>
                <div class="col-4">
                    <a href="ScheduleFeedback.jsp?delete" class="btn btn-info">Delete a feedback schedule</a>
                </div>
            </div>
            <hr>
            <div class="row text-center ">
                <div class="col-4">
                    <a href="ViewScheduledFeedback.jsp" class="btn btn-info">View scheduled feedbacks</a>
                </div>
                <div class="col-4">
                </div>
                <div class="col-4">
                </div>
            </div>
        </div>
    </body>
</html>
