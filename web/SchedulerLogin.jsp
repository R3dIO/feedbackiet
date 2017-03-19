<%@page import="managers.UtilsManager"%>
<%@page import="managers.SessionManager"%>
<%
    String relativeRootPath = "./";
%>
<%-- 
    Document   : schedulerLogin
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%
    SessionManager sm = new SessionManager(request, response, false);
    if (sm.isSchedulerSession()) {
        String redirectUrl = relativeRootPath;
        redirectUrl += UtilsManager.getHomeUrl("scheduler");
        response.sendRedirect(redirectUrl);
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--HeadTag.jsp file contains Basic head with common css and js-->
        <jsp:include page="includes/HeadTag.jsp">
            <jsp:param name="title" value="Feedback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
    </head>
    <body>

        <jsp:include page="includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <!--Check below file to see parameters functionality-->
        <jsp:include page="includes/Navbar.jsp">
            <jsp:param name="scheduler" value="active"/>
            <jsp:param name="logout" value="hide-logout-button"/>
            <jsp:param name="logout_url" value=""/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container text-center">
            <hr>
            <form action="<%=relativeRootPath %>LoginServlet" method="post">
                <input type="hidden" name="login_type" value="scheduler"/>
                <div class="row table">
                    <div class="col-12 text-center text-success font-weight-bold h3">Scheduler Login</div>
                </div>
                <div class="row table">
                    <div class="offset-4 col-1">
                        Username
                    </div>
                    <div class="col-4">
                        <input type="text" name="username" />
                    </div>
                </div>
                <div class="row table">
                    <div class="offset-4 col-1">
                        Password
                    </div>
                    <div class="col-4">
                        <input type="password" name="password" />
                    </div>
                </div>
                <div class="row table">
                    <div class="offset-6 col-2">
                        <input type="submit" class="btn btn-success"/>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 text-center text-danger">
                        <%
                            if (request.getParameter("login_error") != null) {
                                out.println(request.getParameter("login_error"));
                            }
                        %>
                    </div>
                </div>
            </form>
            <hr>
        </div>
    </body>
</html>
