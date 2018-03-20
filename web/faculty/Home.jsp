<%@page import="beans.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="beans.CsfBean"%>
<%@page import="beans.ClassBean"%>
<%@page import="java.util.List"%>
<%@page import="beans.FacultyBean"%>
<%@page import="managers.UtilsManager"%>
<%@page import="managers.LogManager"%>
<%@page import="java.io.IOException"%>
<%@page import="managers.SessionManager"%>
<%
    String relativeRootPath = "../";
%>
<%-- 
    Document   : faculty/index
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SessionManager sm = new SessionManager(request, response, false);
    if (!sm.isFacultySession()) {
        response.sendRedirect(relativeRootPath + UtilsManager.getLoginUrl("faculty"));
        return;
    }
    FacultyBean fb = sm.getFaculty();
    long facultyId = fb.getId();
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
                $('#form_1').on('keyup keypress', function (e) {
                    var keyCode = e.keyCode || e.which;
                    if (keyCode === 13) {
                        e.preventDefault();
                        return false;
                    }
                });
            });
        </script>
        <script src="<%=relativeRootPath%>js/sol.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=relativeRootPath%>css/sol.css">

        <script>
            var sList = Array();
            $(document).ready(function () {
                $("#select_csf").searchableOptionList({
                    data: sList,
//                            useBracketParameters: true
                });
            });
        </script>
        <script>
            var i = 0;
            <%
                for (Iterator<CsfBean> it = fb.getCsfBeanCollection().iterator(); it.hasNext();) {
                    CsfBean csf = it.next();
                    ClassBean cb = csf.getClassId();
                    SubjectBean sb = csf.getSubjectId();
            %>
            var object = {"type": "option", "value": "<%=csf.getId()%>", "label": "<%=cb.getClassCodeById()%> : <%=sb.getSubjectName()%>"};
                sList[i++] = object;
            <%
                }
            %>
        </script>
    </head>
    <body>

        <%
            String logoutUrl = relativeRootPath + "LogoutServlet?type=faculty";
        %>
        <jsp:include page="../includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <!--Check below file to see parameters functionality-->
        <jsp:include page="../includes/Navbar.jsp">
            <jsp:param name="faculty" value="active"/>
            <jsp:param name="logout_url" value="<%=logoutUrl%>"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container text-center">
            <hr>
            Welcome <%=sm.getFaculty().getFirstName()%> as Faculty
            <hr>
            <form id="form_1" action="view_feedback/ViewFeedback.jsp" method="get">
                <div class="row text-left">
                    <div class="col-3">
                        <a href="#" class="h6">View Feedback</a>
                    </div>
                    <div class="col-6">
                        <select id="select_csf" name="csf_id">
                        </select>
                    </div>
                    <div class="col-3">
                        <input type="button" class="btn btn-secondary" onclick="$('#form_1').submit();" value="Submit"/>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
