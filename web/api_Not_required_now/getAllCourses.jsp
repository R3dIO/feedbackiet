<%@page import="com.google.gson.JsonSerializer"%>
<%@page import="com.google.gson.JsonDeserializer"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="beans.CourseBean"%>
<%
    CourseBean helperCourseBean = new CourseBean();
    List<CourseBean> list = helperCourseBean.findAll();
    GsonBuilder gb = new GsonBuilder();
    gb.excludeFieldsWithoutExposeAnnotation();
    Gson gson = gb.create();
    out.println(gson.toJson(list));
%>