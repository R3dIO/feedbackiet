<%@page import="beans.SectionCFeedback"%>
<%@page import="beans.SectionBFeedback"%>
<%@page import="beans.SectionAFeedback"%>
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
    CsfBean csf = new CsfBean(Long.parseLong(request.getParameter("csf_id"))).findById();
    if (csf.getFacultyId().getId() != fb.getId()) {
        response.sendRedirect(relativeRootPath + UtilsManager.getLoginUrl("faculty"));
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
            <jsp:param name="faculty" value="active"/>
            <jsp:param name="logout_url" value="<%=logoutUrl%>"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container ">
            <hr>
            <div class="text-center">Welcome <%=sm.getFaculty().getFirstName()%> as Faculty</div>
            <hr>
            <%
                FeedbackBean feedback = new FeedbackBean();
                feedback.setCsfId(csf);
                LogManager.log(csf.toString());
                SectionAFeedback sectionAFeedback = feedback.findSectionAFeedbackByCsfId();
                SectionBFeedback sectionBFeedback = feedback.findSectionBFeedbackByCsfId();
                SectionCFeedback sectionCFeedback = feedback.findSectionCFeedbackByCsfId();
                LogManager.log(sectionAFeedback.toString());
                LogManager.log(sectionBFeedback.toString());
                LogManager.log(sectionCFeedback.toString());

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
                <% if (csf.getClassId().getSection() != null) {%>
                <div class="col-1">Section:</div>
                <div class="col-3"><b><%=csf.getClassId().getSection()%></b></div>
                        <%}%>
                <div class="col-1">Subject:</div>
                <div class="col-3"><b><%=csf.getSubjectId().getSubjectName() + " (" + csf.getSubjectId().getSubjectCode() + ")"%></b></div>

                <div class="col-1">Total Feedbacks:</div>
                <div class="col-3"><b><%=sectionCFeedback.getComment1().size()%></b></div>
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
                                <th>Average (On Scale: 0 to 5)</th>
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
                                Double[] sectionAFeedbackArray = sectionAFeedback.getAttributesAsArray();
                                for (int i = 0; i < attribList.size(); i++) {
                                    String attribute = attribList.get(i);
                                    String row = "<tr>\n";
                                    row += "<td>" + (i + 1) + "</td>\n";
                                    row += "<td>" + attribute + "</td>\n";
                                    row += "<td>" + sectionAFeedbackArray[i] + "</td>\n";
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
                                int iTemp = 0;
                            %>
                            <tr>
                                <td><%=iTemp + 1%></td>
                                <td><%=attribList.get(iTemp++)%></td>
                                <td><%=sectionBFeedback.getAttribute11Yes()%></td>
                                <td><%=sectionBFeedback.getAttribute11No()%></td>
                                <td><%=sectionBFeedback.getAttribute11NoComment()%></td>
                            </tr>
                            <tr>
                                <td><%=iTemp + 1%></td>
                                <td><%=attribList.get(iTemp++)%></td>
                                <td><%=sectionBFeedback.getAttribute12Yes()%></td>
                                <td><%=sectionBFeedback.getAttribute12No()%></td>
                                <td><%=sectionBFeedback.getAttribute12NoComment()%></td>
                            </tr>
                            <tr>
                                <td><%=iTemp + 1%></td>
                                <td><%=attribList.get(iTemp++)%></td>
                                <td><%=sectionBFeedback.getAttribute13Yes()%></td>
                                <td><%=sectionBFeedback.getAttribute13No()%></td>
                                <td><%=sectionBFeedback.getAttribute13NoComment()%></td>
                            </tr>
                            <tr>
                                <td><%=iTemp + 1%></td>
                                <td><%=attribList.get(iTemp++)%></td>
                                <td><%=sectionBFeedback.getAttribute14Yes()%></td>
                                <td><%=sectionBFeedback.getAttribute14No()%></td>
                                <td><%=sectionBFeedback.getAttribute14NoComment()%></td>
                            </tr>
                            <tr>
                                <td><%=iTemp + 1%></td>
                                <td><%=attribList.get(iTemp++)%></td>
                                <td><%=sectionBFeedback.getAttribute15Yes()%></td>
                                <td><%=sectionBFeedback.getAttribute15No()%></td>
                                <td><%=sectionBFeedback.getAttribute15NoComment()%></td>
                            </tr>
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
                                <th>Strength</th>
                                <th>Weakness</th>
                                <th>General Suggestions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int size = sectionCFeedback.getComment1().size();
                                for (int i = 0; i < size; i++) {
                            %>
                            <tr>
                                <td><%=i + 1%></td>
                                <td><%=sectionCFeedback.getComment1().get(i)%></td> 
                                <td><%=sectionCFeedback.getComment2().get(i)%></td> 
                                <td><%=sectionCFeedback.getComment3().get(i)%></td> 
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <br>
        </div>
    </body>
</html>
