<%@page import="beans.DepartmentBean"%>
<%@page import="beans.FacultyBean"%>
<%@page import="java.util.List"%>
<%@page import="beans.ClassBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/ClassAddUpdate
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
            <jsp:param name="title" value="Feedback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <script src="<%=relativeRootPath%>js/jquery.metadata.js" type="text/javascript"></script>
        <script src="<%=relativeRootPath%>js/jquery.tablesorter.min.js" type="text/javascript"></script>
        <script>

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
        </script>
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
            <!--            <hr>
                        Welcome <%=sm.getFaculty().getFirstName()%> as Admin-->
            <hr>
            <div class="row">
                <div id="message" class="col-12 <%=success ? "text-success" : "text-danger"%>">
                    <%=message%>
                </div>
            </div>
            <div class="row text-left">
                <div class="col-12">
                    <table class="table  table-striped tablesorter " id="my_table">
                        <thead class="text-white">
                            <tr>
                                <th>S.No.</th>
                                <th>Department id</th>
                                <th>Department Code</th>
                                <th>Department Name</th>
                                    <% if (request.getParameter("edit") != null) {%>
                                <th>Edit</th>
                                    <%}%>
                                    <% if (request.getParameter("delete") != null) {%>
                                <th>Delete</th>
                                    <%}%>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int sno = 1;
                                List<DepartmentBean> listDB = new DepartmentBean().findAll();
                                for (DepartmentBean db : listDB) {
                            %>
                            <tr>
                                <td><%=sno++%></td>
                                <td><%=db.getId()%></td>
                                <td><%=db.getDepartmentCode()%></td>
                                <td><%=db.getDepartmentName()%></td>
                                <% if (request.getParameter("edit") != null) {%>
                                <td><a class="btn btn-primary" href="../add/DepartmentAddEdit.jsp?edit=<%=db.getId()%>">Edit</a></td>
                                <%}%>
                                <% if (request.getParameter("delete") != null) {%>
                                <td class="hidden-print">
                                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                                        <input type="hidden" value="deleteDepartmentById" name="purpose"/>  
                                        <input type="hidden" value="<%=db.getId()%>" name="department_id"/>
                                        <input type="submit" class="btn btn-primary" value="Delete"/>
                                    </form>
                                </td>  
                                <%}%>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
