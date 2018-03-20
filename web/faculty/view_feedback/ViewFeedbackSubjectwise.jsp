<%@page import="beans.AverageFeedback"%>
<%@page import="beans.FeedbackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beans.SubjectBean"%>
<%@page import="beans.CsfBean"%>
<%@page import="beans.ClassBean"%>
<%@page import="java.util.List"%>
<%@page import="beans.FacultyBean"%>
<%@page import="managers.UtilsManager"%>
<%@page import="managers.LogManager"%>
<%@page import="java.io.IOException"%>
<%@page import="managers.SessionManager"%>
<%
    String relativeRootPath = "../../";
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
    Long subjectId = (long) -1;
    try {
        subjectId = Long.parseLong(request.getParameter("subject_id"));
    } catch (NumberFormatException e) {
        LogManager.log(e.getMessage());
        return;
    }
    CsfBean helperCsf = new CsfBean();
    helperCsf.setFacultyId(fb);
    helperCsf.setSessionId(UtilsManager.getCurrentSessionId());
    helperCsf.setSubjectId(new SubjectBean(subjectId));
    List<CsfBean> csfList = helperCsf.findByFacultyIdAndSubjectId();
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
            <jsp:param name="faculty" value="active"/>
            <jsp:param name="logout_url" value="<%=logoutUrl%>"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container ">
            <hr>
            <div class="text-center">Welcome <%=sm.getFaculty().getFirstName()%> as Faculty</div>
            <hr>
            <%
                for (CsfBean csf : csfList) {
                    AverageFeedback avgFeedback = csf.getAverageFeedbackById();
                    out.print(avgFeedback.getAttribute1());
            %>
            <div class="row table">
                <div class="col-1">CSF Id:</div>
                <div class="col-3"><b><%=csf.getId()%></b></div>
                <div class="col-4">Class Code: <b> <%=csf.getClassId().getClassCodeById()%></b></div>
                <div class="col-1">Faculty:</div>
                <div class="col-3"><b><%=csf.getFacultyId().getTitle() + " " + csf.getFacultyId().getFirstName() + " " + csf.getFacultyId().getLastName()%></b></div>
            </div>
            <div class="row table">
                <div class="col-1">Course:</div>
                <div class="col-3"><b><%=csf.getClassId().getDepartmentId().getCourseId().getCourseName()%>
                        (<%=csf.getClassId().getDepartmentId().getCourseId().getCourseCode()%>)</b></div>

                <div class="col-1">Department:</div>
                <div class="col-3"><b><%=csf.getClassId().getDepartmentId().getDepartmentName()%>
                        (<%=csf.getClassId().getDepartmentId().getDepartmentCode()%>)</b></div>

                <div class="col-1">Year:</div>
                <div class="col-3"><b><%=csf.getClassId().getYear()%></b></div>
            </div>
            <div class="row table">
                <% if (csf.getClassId().getSection()
                            != null) {%>
                <div class="col-1">Section:</div>
                <div class="col-3"><b><%=csf.getClassId().getSection()%></b></div>
                        <%}%>
                <div class="col-1">Subject:</div>
                <div class="col-3"><b><%=csf.getSubjectId().getSubjectName() + " (" + csf.getSubjectId().getSubjectCode() + ")"%></b></div>

            </div>
            <div class="row h5 text-center">
                <div class="col-12">Section A</div>
            </div>
            <div class="row">
                <div class="col-12">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>S.no.</th>
                                <th>Attributes</th>
                                <th>Excellent</th>
                                <th>Good</th>
                                <th>Average</th>
                                <th>Poor</th>
                                <th>Very Poor</th>
                            </tr>
                        </thead>
                        <%
                            ArrayList<String> attribList = new ArrayList<String>();

                            attribList.add(
                                    "Ability to bring conceptual clarity and promotion of thinking ability by teacher");
                            attribList.add(
                                    "Motivation provided");
                            attribList.add(
                                    "Teacher's Communication Skill");
                            attribList.add(
                                    "Teacher's Regularity and Punctuality");
                            attribList.add(
                                    "Teacher's Subject Knowledge");
                            attribList.add(
                                    "Completion and Coverage of Course");
                            attribList.add(
                                    "Compliments theory with practical examples");
                            attribList.add(
                                    "Teacher interaction and guidance outside");
                            attribList.add(
                                    "Teacher's Computer/IT Skills");
                            attribList.add(
                                    "Teacher's overall performance");
                        %>
                        <tbody>
                            <%
                                for (int i = 0; i < attribList.size(); i++) {
                                    String attribute = attribList.get(i);
                                    String row = "<tr>\n";
                                    row += "<td>" + (i + 1) + "</td>\n";
                                    row += "<td>" + attribute + "</td>\n";
//                                    row += "<td>" + feedback.getAttribute1() + "</td>\n";
                                    row += "</tr>\n";
                                    out.println(row);
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row h5 text-center">
                <div class="col-12">Section B</div>
            </div>
            <div class="row">
                <div class="col-12">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>S.no.</th>
                                <th>Attributes</th>
                                <th>Yes</th>
                                <th>No</th>
                                <th>No comments</th>
                            </tr>
                        </thead>
                        <%
                            attribList.clear();

                            attribList.add(
                                    "Result of test declared within two weeks of it being conducted.");
                            attribList.add(
                                    "Adequate number of assignments / cases given.");
                            attribList.add(
                                    "Would you recommend him / her to teach the same subject to your juniors?");
                            attribList.add(
                                    "Would you recommend him / her to teach you any other subject?");
                            attribList.add(
                                    "In your opinion is this syllabus adequate?");
                        %>
                        <tbody>
                            <%
//                                sno = 1;
//                                for (String attribute : attribList) {
//                                    out.println(UtilsManager.getRow(attribute, sno, "attribute" + (sno + 10), 3));
//                                    sno++;
//                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row h5 text-center">
                <div class="col-12">Section C</div>
            </div>
            <div class="row">
                <div class="col-12">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>S.no.</th>
                                <th>Attributes</th>
                                <th>Comments</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>What are the strengths of the teacher?</td>
                                <td><textarea required cols="40" rows="3" name="comment1" maxlength="250"></textarea></td>
                            </tr> 
                            <tr>
                                <td>2</td>
                                <td>What are the areas of weaknesses in teacher?</td>
                                <td><textarea required cols="40" rows="3" name="comment2" maxlength="250"></textarea></td>
                            </tr> 
                            <tr>
                                <td>3</td>
                                <td>Any other suggestion (regarding curriculum, subject(s), faculty)</td>
                                <td><textarea required cols="40" rows="3" name="comment3" maxlength="250"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <br>
            <%
                }
            %>
        </div>
    </body>
</html>
