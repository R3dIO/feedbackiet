<%@page import="java.util.List"%>
<%@page import="beans.CsfBean"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="beans.SubjectBean"%>
<%@page import="beans.ClassBean"%>
<%@page import="managers.UtilsManager"%>
<%
    String relativeRootPath = "../../";
%>
<%-- 
    Document   : admin/aud/CSFEdit
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
        purpose = "editCSF";
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
            function findAllFaculty() {
                $.ajax({
                    type: "post",
                    data: {purpose: "findAllFaculty"},
                    url: "<%=relativeRootPath%>JsonApiServlet",
                    success: function (result) {
                        var facultyArray = result;
                        list = Array(facultyArray.length);
                        for (var i = 0; i < facultyArray.length; i++) {
                            var faculty = facultyArray[i];
                            var object = {"type": "option", "value": faculty.id, "label": faculty.title + " " + faculty.firstName + " " + faculty.lastName};
                            list[i] = object;
                        }
            <%
                Collection<SubjectBean> sbCollection1 = cb.getSubjectBeanCollection();
//                Collection<CsfBean> csfCollection1 = cb.getCsfBeanCollection();
                for (SubjectBean sb : sbCollection1) {
            %>
                        $("#faculty_select<%=sb.getId()%>").searchableOptionList({
                            data: list,
//                            useBracketParameters: true
                        });
            <%
                }
            %>
                    }
                });
            }

            $(document).ready(function () {
                findAllFaculty();
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
                <div class="col-12 text-capitalize h4 text-info">
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
                <div class="col-12 <%=success ? "text-success" : "text-danger"%>">
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
                                <%
                                    Collection<SubjectBean> sbCollection = cb.getSubjectBeanCollection();
                                    for (SubjectBean sb : sbCollection) {
                                %>
                                <tr>
                                    <td><%=sb.getSubjectName()%>(<%=sb.getSubjectCode()%>)</td>
                                    <td>
                                        <!--Faculty Dropdown-->
                                        <select id="faculty_select<%=sb.getId()%>" name="faculty_id<%=sb.getId()%>" multiple="multiple">
                                        </select>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
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
            <div class="row">
                <div class="offset-2 col-8">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>CSF Id</th>
                                <th>Subject Code</th>
                                <th>Subject Name</th>
                                <th>Faculty Name</th> 
                                <th>Delete</th>

                            </tr>
                        </thead>
                        <tbody>
                            <%
                                //Show all subjects related to the current class show 
                                CsfBean helperCSF = new CsfBean();
                                helperCSF.setClassId(cb);
                                helperCSF.setSessionId(UtilsManager.getCurrentSessionId());
                                List<CsfBean> csfList = helperCSF.findByClassIdAndSessionId();
                                for (CsfBean csf : csfList) {
                            %>
                            <tr>
                                <td><%=csf.getId()%></td>
                                <td><%=csf.getSubjectId().getSubjectCode()%></td>
                                <td><%=csf.getSubjectId().getSubjectName()%></td>
                                <td><%=csf.getFacultyId().getTitle()%> <%=csf.getFacultyId().getFirstName()%> <%=csf.getFacultyId().getLastName()%></td>
                                <td class="hidden-print">
                                    <form action="<%=relativeRootPath%>CRUDServlet" method="post">
                                        <input type="hidden" value="deleteCSFById" name="purpose"/>  
                                        <input type="hidden" value="<%=csf.getId()%>" name="csf_id"/>
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
