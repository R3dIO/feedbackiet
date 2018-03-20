<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/index
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

            <div class="row text-center">
                <div class="col-4">
                    <a href="ClassViewDelete.jsp?edit&delete" class="btn btn-info">Class</a>
                </div>
                <div class="col-4">
                    <a href="ClassViewDelete.jsp" class="btn btn-info">Class Timetable</a>
                </div>
                <div class="col-4">
                    <a href="ClassViewDelete.jsp" class="btn btn-info">Class & Subjects</a>
                </div>
            </div>
            <hr>
            <div class="row text-center">
                <div class="col-4">
                    <a href="FacultyViewDelete.jsp?edit&delete" class="btn btn-info">Faculty</a>
                </div>
                 <div class="col-4">
                    <a href="DepartmentViewDelete.jsp?edit&delete" class="btn btn-info">Department</a>
                </div>
                <div class="col-4">
                    <a href="FacultyViewDelete.jsp" class="btn btn-info">Faculty & Subjects</a>
                </div>
            </div>
            <hr>
            <!--            <div class="row text-center ">
                            <div class="col-4">
                                <a href="#" class="btn btn-info">Department</a>
                            </div>
                            <div class="col-4">
                                <a href="#" class="btn btn-info">Course</a>
                            </div>
                            <div class="col-4">
                                <a href="#" class="btn btn-info">Schema</a>
                            </div>
                        </div>
                        <hr>
                        <div class="row text-center ">
                            <div class="col-4">
                                <a href="#" class="btn btn-info">CSF (Timetable)</a>
                            </div>
                            <div class="col-4">
                            </div>
                            <div class="col-4">
                            </div>
                        </div>-->


        </div>
    </body>
</html>
