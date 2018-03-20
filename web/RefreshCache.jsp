<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.EntityManager"%>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("FeedbackSystem2K17PU");
    emf.getCache().evictAll();
%>