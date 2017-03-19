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
            <form action="<%=relativeRootPath%>FeedbackServlet" method="post">
                <input type="hidden" name="purpose" value="startFeedbackSession"/>
                <div class="row table">
                    <div class="offset-2 col-3">
                        Choose class
                    </div>
                    <div class="col-4">
                        <select name="class_id" id="select_class" class="form-control">
                            <option value="-1">Select Class</option>

                        </select>
                    </div>
                </div>
                <div class="row table">
                    <div class="offset-2 col-3">
                        Password
                    </div>
                    <div class="col-4 ">
                        <input type="password" placeholder="Password" class="form-control" name="password" />
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
