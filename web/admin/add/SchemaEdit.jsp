<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="beans.SubjectBean"%>
<%@page import="beans.ClassBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/SchemaAddUpdate
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
    ClassBean cb = null;
    String purpose = "";
    if (request.getParameter("edit") != null) {
        purpose = "editSchema";
        cb = new ClassBean(Long.parseLong(request.getParameter("edit"))).findById();
    } else {
        response.sendRedirect(relativeRootPath + UtilsManager.getHomeUrl("admin"));
        return;
    }
    long classId = -1;
    if (cb != null) {
        classId = cb.getId();
    } else {
        response.sendRedirect(relativeRootPath + UtilsManager.getHomeUrl("admin"));
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
            <jsp:param name="title" value="Feecback System"/>   
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <script src="<%=relativeRootPath%>js/sol.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=relativeRootPath%>css/sol.css">

        <script>
            var list = Array();
            function findSubjectNotAssosiatedWithClassByClassId(classId) {
                $.ajax({
                    type: "post",
                    data: {classId: classId, purpose: "findSubjectNotAssosiatedWithClassByClassId"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var subjectArray = result;
                        list = Array(subjectArray.length);
                        for (var i = 0; i < subjectArray.length; i++) {
                            var subject = subjectArray[i];
//                            $("#select_subject").append('<option value="' + subject.id + '">' + subject.subjectCode + ": " + subject.subjectName + '</option>');
                            var object = {"type": "option", "value": subject.id, "label": subject.subjectName + "(" + subject.subjectCode + ")"};
                            list[i] = object;
                        }
                        $("#select_subject").searchableOptionList({
                            data: list,
//                            useBracketParameters: true
                        });
                    }
                });
            }
            $(document).ready(function () {
                findSubjectNotAssosiatedWithClassByClassId("<%=classId%>");
            });
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
                <div  class="col-12 text-capitalize h4 text-info">
                    <%=purpose%>
                </div>
            </div>
            <div class="row">
                <div class="offset-4 col-4">
                    <a class="btn btn-info" href="<%=relativeRootPath%>admin/view_edit_delete/ClassViewDelete.jsp">View All Class</a>
                </div>
            </div> 
            <hr>
            <div class="row">
                <div id="message" class="col-12 <%=success ? "text-success" : "text-danger"%>">
                    <%=message%>
                </div>
            </div>
            <div class="row text-left">
                <div class="offset-2 col-8">
                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                        <input type="hidden" value="<%=purpose%>" name="purpose"/>  
                        <input type="hidden" value="<%=classId%>" name="class_id"/>
                        <table class="table table-success">
                            <tbody>
                                <tr>
                                    <td>Course</td>
                                    <td><%=cb.getDepartmentId().getCourseId().getCourseName()%></td>
                                </tr>
                                <tr>
                                    <td>Department</td>
                                    <td><%=cb.getDepartmentId().getDepartmentName()%></td>
                                </tr>
                                <tr>
                                    <td>Class</td>
                                    <td><%=cb.getClassCodeById()%></td>
                                </tr>
                                <tr>
                                    <td>Subject</td>
                                    <td>
                                        <!--                                        <select class="form-control" required name="subject_id" id="select_subject">
                                                                                    <option value="-1">Select Subject</option>
                                                                                </select>-->
                                        <select id="select_subject" name="subject_id" multiple="multiple">
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input type="submit" class="form-control btn btn-success" value="Add Subject"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="offset-2 col-8">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Subject Id</th>
                                <th>Subject Code</th>
                                <th>Subject Name</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                //Show all subjects related to the current class show 
                                Collection<SubjectBean> subjectCollection = cb.getSubjectBeanCollection();
                                for (SubjectBean sb : subjectCollection) {
                            %>
                            <tr id="schema_id_<%=cb.getId()%>_<%=sb.getId()%>">
                                <td><%=sb.getId()%></td>
                                <td><%=sb.getSubjectCode()%></td>
                                <td><%=sb.getSubjectName()%></td>
                                <td class="hidden-print">
                                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                                        <input type="hidden" value="deleteSchemaByClassIdAndSubjectId" name="purpose"/>  
                                        <input type="hidden" value="<%=sb.getId()%>" name="subject_id"/>
                                        <input type="hidden" value="<%=cb.getId()%>" name="class_id"/>
                                        <input type="submit" class="btn btn-primary" value="Delete"/>
                                    </form>
                                </td>
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
