<%@page import="com.google.gson.JsonSerializer"%>
<%@page import="com.google.gson.JsonDeserializer"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="beans.DepartmentBean"%>
<%
    String courseId = request.getParameter("courseId");
    DepartmentBean helperDepartmentBean = new DepartmentBean();
    List<DepartmentBean> list = helperDepartmentBean.findAll();
    GsonBuilder gb = new GsonBuilder();
    gb.excludeFieldsWithoutExposeAnnotation();
    Gson gson = gb.create();
    out.println(gson.toJson(list));
%>