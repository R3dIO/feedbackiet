<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beans.ClassBean"%>
<%@page import="managers.UtilsManager"%>
<%@page import="managers.SessionManager"%>
<%
    String relativeRootPath = "../";
%>
<%-- 
    Document   : student/home
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%
    SessionManager sm = new SessionManager(request, response, false);
    if (sm.isFeedbackSession()) {
        String redirectUrl = relativeRootPath;
        redirectUrl += UtilsManager.getStudentFeedbackUrl();
        response.sendRedirect(redirectUrl);
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--HeadTag.jsp file contains Basic head with common css and js-->
        <jsp:include page="../includes/HeadTag.jsp">
            <jsp:param name="title" value="Feedback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <script>
            function findAllCourses() {
                $("#select_class").html("<option value=\" - 1\">Select Class</option>");
                $.ajax({
                    type: "post",
                    data: {purpose: "findAllFeedbackEligibleClass"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var classArray = result;
                        for (var i = 0; i < classArray.length; i++) {
                            var class_ = classArray[i];
                            var class_code = class_.departmentId.courseId.courseCode + class_.year +
                                    class_.departmentId.departmentCode;
                            var section = class_.section == null ? "" : class_.section;
                            class_code += section;
                            $("#select_class").append('<option code="' + class_code + '" value="' + class_.id + '">' + class_code + '</option>');
                        }
                    }});
            }
            $(document).ready(function () {
                findAllCourses();
            });
        </script>    
    </head>
    <body>

        <jsp:include page="../includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <!--Check below file to see parameters functionality-->
        <jsp:include page="../includes/Navbar.jsp">
            <jsp:param name="student" value="active"/>
            <jsp:param name="logout" value="hide-logout-button"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container text-center">
            <hr>
            <div class="row">
                <div class="col-12 text-primary">
                    Thank you for your feedback!
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-12">
                    <a class="btn btn-secondary" href="<%=relativeRootPath + UtilsManager.getHomeUrl("student")%>">
                        Start new feedback session?
                    </a>
                </div>
            </div>

            <hr>
        </div>
    </body>
</html>
