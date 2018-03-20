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
            });
        </script>
        <script src="<%=relativeRootPath%>js/sol.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=relativeRootPath%>css/sol.css">

        <script>
            var sList = Array();
            function findSubjectByFacultyId(facultyId) {
                $.ajax({
                    type: "post",
                    data: {facultyId: facultyId, purpose: "findSubjectByFacultyId"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var subjectArray = result;
                        sList = Array(subjectArray.length);
                        for (var i = 0; i < subjectArray.length; i++) {
                            var subject = subjectArray[i];
//                            $("#select_subject").append('<option value="' + subject.id + '">' + subject.subjectCode + ": " + subject.subjectName + '</option>');
                            var object = {"type": "option", "value": subject.id, "label": subject.subjectName + "(" + subject.subjectCode + ")"};
                            sList[i] = object;
                        }
                        $("#select_subject").searchableOptionList({
                            data: sList,
//                            useBracketParameters: true
                        });
                    }
                });
            }
            $(document).ready(function () {
                findSubjectByFacultyId("<%=facultyId%>");
            });
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
            <form action="view_feedback/ViewFeedbackSubjectwise.jsp" method="post">
                <div class="row text-left">
                    <div class="col-3">
                        <a href="#" class="h6">View Feedback (Subjectwise)</a>
                    </div>
                    <div class="col-6">
                        <select id="select_subject" name="subject_id">
                        </select>
                    </div>
                    <div class="col-3">
                        <input type="submit" class="btn btn-secondary" value="Submit"/>
                    </div>
                </div>
            </form>
            <br>
            <div class="row text-left">
                <div class="col-3 h6">View Feedback (Classwise):-</div>
            </div>
            <div class="row text-left">
                <div class="col-12">
                    <table class="table  table-striped tablesorter " id="my_table">
                        <thead class="text-white">
                            <tr>
                                <th>S.No.</th>
                                <th>Class id</th>
                                <th>Course</th>
                                <th>Department</th>
                                <th>Class Code</th>
                                <th>View Feedback</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int sno = 1;
                                CsfBean csf = new CsfBean();
                                csf.setFacultyId(fb);
                                csf.setSessionId(UtilsManager.getCurrentSessionId());
                                List<ClassBean> listCB = csf.findClassByFacultyId();
                                for (ClassBean cb : listCB) {
                            %>
                            <tr>
                                <td><%=sno++%></td>
                                <td><%=cb.getId()%></td>
                                <td><%=cb.getDepartmentId().getCourseId().getCourseName()%></td>
                                <td><%=cb.getDepartmentId().getDepartmentName()%></td>
                                <td><%=cb.getClassCodeById()%></td>
                                <td><a href="view_feedback/ViewFeedbackClasswise.jsp?class_id=<%=cb.getId()%>">View Feedback</a></td>
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
