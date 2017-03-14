<div class="container-fluid">
    <div class="row">
        <!--<div class="col-12">-->
        <nav class="navbar navbar-toggleable navbar-light bg-faded">
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ${param.home}">
                        <a class="nav-link" href="${param.relative_root_path}Home.jsp">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item ${param.features}">
                        <a class="nav-link" href="${param.relative_root_path}SchedulerLogin.jsp">Scheduler</a>
                    </li>
                    <li class="nav-item ${param.pricing}">
                        <a class="nav-link" href="#">Pricing</a>
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