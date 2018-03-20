<%@page import="beans.SubjectBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/SubjectAddEdit
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
    SubjectBean sb = null;
    String purpose = "addSubject";
    if (request.getParameter("edit") != null) {
        purpose = "editSubject";
        sb = new SubjectBean(Long.parseLong(request.getParameter("edit"))).findById();
    }
    String subjectCode = "", subjectName = "";
    long subjectId = -1;
    if (sb != null) {
        subjectId = sb.getId();
        subjectCode = sb.getSubjectCode();
        subjectName = sb.getSubjectName();
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
                        <input type="hidden" value="<%=subjectId%>" name="subject_id"/>
                        <table class="table table-success">
                            <tbody>
                                <tr>
                                    <td>Subject Code</td>
                                    <td>
                                        <input type="text" required value="<%=subjectCode%>" name="subject_code" class="form-control"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Subject Name</td>
                                    <td>
                                        <input type="text" required name="subject_name" value="<%=subjectName%>" class="form-control"/>
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
