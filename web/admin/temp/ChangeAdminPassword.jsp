<%@page import="beans.LoginBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
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
    LoginBean lb = new LoginBean(sm.getFaculty().getLoginBeanCollection().iterator().next().getUsername(),
            sm.getFaculty().getLoginBeanCollection().iterator().next().getPassword()).findByUsernameAndPassword();
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
            Welcome <%=sm.getFaculty().getFirstName()%> as Admin
            <hr>
            <div class="row text-left">
                <div class="offset-5 col-3">
                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                        Username: <input type="text"  class="form-control" name="username" value="<%=lb.getUsername()%>"/>
                        <br>
                        Password: <input type="password" class="form-control" name="password" value="<%=lb.getPassword()%>"/>
                        <br>
                        <input type="hidden" name="purpose" value="changeAdminUsernamePassword"/>
                        <input type="submit" class="btn btn-primary" value="Submit"/>
                    </form>
                </div>
            </div>
            <hr>
        </div>
    </body>
</html>
