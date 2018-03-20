<%@page import="java.util.ListIterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
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
    Document   : admin/temp/
    Created on : 8 Mar, 2017, 12:34:54 PM
    Author     : Sapan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SessionManager sm = new SessionManager(request, response, false);
    if (!sm.isAdminSession()) {
        response.sendRedirect(relativeRootPath + UtilsManager.getLoginUrl("admin"));
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
        <style>
            .page-break-after{
                page-break-after: always;
            }
        </style>
    </head>
    <body>
        <span class="hidden-print">
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
        </span>
        <div class="container ">
            <span class="hidden-print">
                <hr>
                <div class="text-center">Welcome <%=sm.getFaculty().getFirstName()%> as Admin</div>
                <hr>
            </span>
            <%
                List<ClassBean> cbList = new ClassBean().findAll();
                for (Iterator<ClassBean> it = cbList.iterator(); it.hasNext();) {
                    ClassBean cb = it.next().findById();
                    CsfBean tempCsf = new CsfBean();
                    tempCsf.setClassId(cb);
                    tempCsf.setSessionId(UtilsManager.getCurrentSessionId());
                    List<CsfBean> csfList = tempCsf.findByClassIdAndSessionId();
            %>
            <div class="row " >
                <div class="col-2">
                    <img src="../../images/logo.png" alt="Davv Logo" class="img-fluid logo-image" style="height:70px;">
                </div>
                <div class="col-8 text-uppercase">
                    <div class="h4 college-name text-capitalize">Institute Of Engineering And Technology</div>
                    <div class="row">
                        <div class="offset-5 col-7" style="font-size: 12px;">Feedback System</div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row table">
                <div class="col-4">Class Code: <b> <%=cb.getClassCodeById()%></b></div>
                <div class="col-1">Course:</div>
                <div class="col-7"><b><%=cb.getDepartmentId().getCourseId().getCourseName()%>
                        (<%=cb.getDepartmentId().getCourseId().getCourseCode()%>)</b></div>
            </div>
            <div class="row table">
                <div class="col-1">Year:</div>
                <div class="col-1"><b><%=cb.getYear()%></b></div>
                        <% if (cb.getSection() != null) {%>
                <div class="col-1">Section:</div>
                <div class="col-1"><b><%=cb.getSection()%></b></div>
                        <%}%>
                <div class="col-1">Dept:</div>
                <div class="col-7"><b><%=cb.getDepartmentId().getDepartmentName()%>
                        (<%=cb.getDepartmentId().getDepartmentCode()%>)</b></div>
            </div>
            <br>
            <%
                for (CsfBean csf : csfList) {
                    csf = csf.findById();
                    FeedbackBean feedback = new FeedbackBean();
                    feedback.setCsfId(csf);
                    LogManager.log(csf.toString());
                    SectionAFeedback sectionAFeedback = feedback.findSectionAFeedbackByCsfId();
                    if (sectionAFeedback == null) {
                        continue;
                    }
//                    SectionBFeedback sectionBFeedback = feedback.findSectionBFeedbackByCsfId();
                    SectionCFeedback sectionCFeedback = feedback.findSectionCFeedbackByCsfId();
                    LogManager.log(sectionAFeedback.toString());
//                    LogManager.log(sectionBFeedback.toString());
//                    LogManager.log(sectionCFeedback.toString());
%>
            <hr>
            <div class="row table">
                <div class="col-1">Faculty:</div>
                <div class="col-4"><b><%=csf.getFacultyId().getTitle() + " " + csf.getFacultyId().getFirstName() + " " + csf.getFacultyId().getLastName()%></b></div>
                <div class="col-1">Subject:</div>
                <div class="col-4"><b><%=csf.getSubjectId().getSubjectName() + " (" + csf.getSubjectId().getSubjectCode() + ")"%></b></div>

                <div class="col-1">Total Feedbacks:</div>
                <div class="col-1"><b><%=sectionCFeedback.getComment1().size()%></b></div>
            </div>
            <!--            <div class="row h5 text-center">
                            <div class="col-12">Section A</div>
                        </div>-->
            <div class="row">
                <div class="col-12">
                    <table class="table table-bordered">
                        <thead>
                            <%
                                ArrayList<String> attribList = new ArrayList<String>();

                                attribList.add(
                                        "Clarity");
                                attribList.add(
                                        "Motivation");
                                attribList.add(
                                        "Communication Skill");
                                attribList.add(
                                        "Regularity");
                                attribList.add(
                                        "Knowledge");
                                attribList.add(
                                        "Coverage");
                                attribList.add(
                                        "Practical Approach");
                                attribList.add(
                                        "Interaction");
                                attribList.add(
                                        "IT Skills");
                                attribList.add(
                                        "Overall");
                            %>
                            <%
                                Double[] sectionAFeedbackArray = sectionAFeedback.getAttributesAsArray();
                                String row = "<tr>\n";
                                for (int i = 0; i < attribList.size(); i++) {
                                    String attribute = attribList.get(i);
                                    row += "<th>" + attribute + "</th>\n";
                                }
                                row += "</tr>\n";
                                out.println(row);
                            %>
                        </thead>
                        <tbody>
                            <%
                                row = "<tr>\n";
                                for (int i = 0; i < attribList.size(); i++) {
                                    row += "<td>" + sectionAFeedbackArray[i] + "</td>\n";
                                }
                                row += "</tr>\n";
                                out.println(row);
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <br>
            <%
                }
            %>
            <div class="page-break-after"></div>
            <%
                }
            %>
        </div>
    </body>
</html>
