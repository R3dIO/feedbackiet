<%@page import="managers.UtilsManager"%>
<div class="container-fluid">
    <div class="row">
        <!--<div class="col-12">-->
        <nav class="navbar navbar-toggleable navbar-light bg-faded">
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ${param.student}">
                        <a class="nav-link" href="${param.relative_root_path}student/Home.jsp">Student</a>
                    </li>
                    <li class="nav-item ${param.admin}">
                        <a class="nav-link" href="${param.relative_root_path}<%=UtilsManager.getHomeUrl("admin")%>">Admin</a>
                    </li>
                    <li class="nav-item ${param.director}">
                        <a class="nav-link" href="${param.relative_root_path}<%=UtilsManager.getHomeUrl("director")%>">Director</a>
                    </li>
                    <li class="nav-item ${param.hod}">
                        <a class="nav-link" href="${param.relative_root_path}<%=UtilsManager.getHomeUrl("hod")%>">HOD</a>
                    </li>
                    <li class="nav-item ${param.faculty}">
                        <a class="nav-link" href="${param.relative_root_path}<%=UtilsManager.getHomeUrl("faculty")%>">Faculty</a>
                    </li>
                    <li class="nav-item ${param.scheduler}">
                        <a class="nav-link" href="${param.relative_root_path}<%=UtilsManager.getHomeUrl("scheduler")%>">Scheduler</a>
                    </li>
                    <li class="nav-item ${param.logout}">
                        <a class="nav-link" href="${param.logout_url}">Logout</a>
                    </li>
                </ul>
                <!--                    <span class="navbar-text">
                                        Navbar text with an inline element
                                    </span>-->
            </div>
        </nav>
        <!--</div>-->
    </div>
</div>