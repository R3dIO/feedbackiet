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
        <div class="container text-center">
            <hr>
            Welcome <%=sm.getSchedulerName()%> as Scheduler
            <hr>
            <div class="row text-center text-capitalize">
                <div class="col-12">
                    <a href="ScheduleFeedback.jsp" class="btn btn-info">Schedule A Feedback</a>
                </div>
            </div>
        </div>
    </body>
</html>
