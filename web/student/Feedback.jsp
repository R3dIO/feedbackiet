<%@page import="managers.LogManager"%>
<%@page import="beans.CourseBean"%>
<%@page import="beans.DepartmentBean"%>
<%@page import="beans.FacultyBean"%>
<%@page import="beans.SubjectBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beans.ClassBean"%>
<%@page import="managers.UtilsManager"%>
<%@page import="managers.SessionManager"%>
<%
    String relativeRootPath = "../";
%>
<%-- 
    Document   : student/Feedback
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>

<%
    SessionManager sm = new SessionManager(request, response, false);
    if (!sm.isFeedbackSession()) {
        response.sendRedirect(relativeRootPath + UtilsManager.getHomeUrl("student"));
        return;
    }
    ClassBean cb = sm.getFeedbackClassBean();
    SubjectBean sb = sm.getFeedbackSubjectBean();
    FacultyBean fb = sm.getFeedbackFacultyBean();
    DepartmentBean db = sm.getFeedbackDepartmentBean();
    CourseBean course = sm.getFeedbackCourseBean();
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
    </head>
    <body>
        <%String logoutUrl = relativeRootPath + "FeedbackServlet?purpose=logout";%>
        <jsp:include page="../includes/Header.jsp" >
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <!--Check below file to see parameters functionality-->
        <jsp:include page="../includes/Navbar.jsp">
            <jsp:param name="student" value="active"/>
            <jsp:param name="logout_url" value="<%=logoutUrl%>"/>
            <jsp:param name="relative_root_path" value="<%=relativeRootPath%>"/>
        </jsp:include>
        <div class="container ">
            <form action="<%=relativeRootPath%>FeedbackServlet" method="post">
                <input type="hidden" value="addFeedback" name="purpose"/>
                <div class="">
                </div>
                <hr>
                <div class="row text-center text-success h4 text-capitalize">
                    <div class="col-12 ">Student feedback form <span class="text-gray-dark">(<%=cb.getClassCodeById()%>)</span></div>
                </div>
                <hr>
                <div class="row table">
                    <div class="col-1">Course:</div>
                    <div class="col-3"><b><%=course.getCourseName()%></b></div>

                    <div class="col-1">Department:</div>
                    <div class="col-3"><b><%=db.getDepartmentName()%></b></div>

                    <div class="col-1">Year:</div>
                    <div class="col-3"><b><%=cb.getYear()%></b></div>
                </div>
                <div class="row table">
                    <% if (cb.getSection() != null) {%>
                    <div class="col-1">Section:</div>
                    <div class="col-3"><b><%=cb.getSection()%></b></div>
                            <%}%>
                    <div class="col-1">Subject:</div>
                    <div class="col-3"><b><%=sb.getSubjectName() + " (" + sb.getSubjectCode() + ")"%></b></div>

                    <div class="col-1">Faculty:</div>
                    <div class="col-3"><b><%=fb.getTitle() + " " + fb.getFirstName() + " " + fb.getLastName()%></b></div>
                </div>
                <div class="row">
                    <div class="offset-2 col-8 justify-content-end">
                        Dear Student,
                        You are requested to give your frank and objective opinion about the teaching of faculty on under mentioned points.
                        It will help us to improve and maintain quality of teaching. Your response will be kept confidential. Rank each 
                        point on a five point scale.<span class="text-warning"> Please do not indicate your identity.</span>
                    </div>
                </div>
                <br>
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
                                    <th>Very Poor</th>
                                    <th>Poor</th>
                                    <th>Average</th>
                                    <th>Good</th>
                                    <th>Excellent</th>
                                </tr>
                            </thead>
                            <%
                                ArrayList<String> attribList = new ArrayList<String>();
                                attribList.add("Ability to bring conceptual clarity and promotion of thinking ability by teacher");
                                attribList.add("Motivation provided");
                                attribList.add("Teacher's Communication Skill");
                                attribList.add("Teacher's Regularity and Punctuality");
                                attribList.add("Teacher's Subject Knowledge");
                                attribList.add("Completion and Coverage of Course");
                                attribList.add("Compliments theory with practical examples");
                                attribList.add("Teacher interaction and guidance outside");
                                attribList.add("Teacher's Computer/IT Skills");
                                attribList.add("Teacher's overall performance");
                            %>
                            <tbody>
                                <%
                                    int sno = 1;
                                    for (String attribute : attribList) {
                                        out.println(UtilsManager.getRow(attribute, sno, "attribute" + sno, 5));
                                        sno++;
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
                                attribList.add("Result of test declared within two weeks of it being conducted.");
                                attribList.add("Adequate number of assignments / cases given.");
                                attribList.add("Would you recommend him / her to teach the same subject to your juniors?");
                                attribList.add("Would you recommend him / her to teach you any other subject?");
                                attribList.add("In your opinion is this syllabus adequate?");
                            %>
                            <tbody>
                                <%
                                    sno = 1;
                                    for (String attribute : attribList) {
                                        out.println(UtilsManager.getRow(attribute, sno, "attribute" + (sno + 10), 3));
                                        sno++;
                                    }
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
                <div class="row">
                    <div class="col-12 text-center">
                        <input type="submit" value="Submit and Next" class="btn btn-primary">
                    </div>
                </div>
                <hr>
            </form>
        </div>
    </body>
</html>