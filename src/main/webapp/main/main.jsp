<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>持明法州后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <style>
        #title {
            font-size: 45px;
            font-weight: revert;
        }

        #jumbotron {
            text-align: center;
        }
    </style>

</head>
<body>


<!--顶部导航-->
<%-- 导航条(navbar 包含着布局容器)
        navbar-inverse 黑色导航条（反色）
 --%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">应学视频APP后台管理系统 <small>v1.0</small></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:
                    <mark>${sessionScope.admins.username}</mark>
                </a></li>
                <li class="dropdown">
                    <a href="${path}/admin/logout">
                        退出登录 <span class="glyphicon glyphicon-log-out"></span>
                    </a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--栅格系统-->

<%-- 栅格系统(手风琴 2 内容10(巨幕 弹出框 进度条)) --%>
<div class="container-fluid">
    <div class="row">
        <!--左边手风琴部分-->
        <div class="col-md-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" align="center">
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <button class="btn btn-info">
                                <a href="javascript:$('#showContent').load('${path}/user/user.jsp')">
                                    用户展示
                                </a>
                            </button>
                            <br><br>
                            <button class="btn btn-info">
                                <a href="javascript:$('#showContent').load('${path}/user/echarts.jsp')">
                                    用户统计
                                </a>
                            </button>
                            <br><br>
                            <button class="btn btn-info">用户分布</button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                类别管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <button class="btn btn-warning">
                                <a href="javascript:$('#showContent').load('${path}/category/category.jsp')">
                                    类别展示
                                </a>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                视频管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <button class="btn btn-success">
                                <a href="javascript:$('#showContent').load('${path}/video/video.jsp')">
                                    视频展示
                                </a>
                            </button>
                            <br><br>
                            <button class="btn btn-success">
                                <a href="javascript:$('#showContent').load('${path}/video/searchVideo.jsp')">
                                    视频检索
                                </a>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFour">
                        <div class="panel-body">
                            <button class="btn btn-success">
                                <a href="javascript:$('#showContent').load('${path}/feedback/feedback.jsp')">
                                    反馈展示
                                </a>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFive">
                        <div class="panel-body">
                            <button class="btn btn-primary">日志展示</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--右边部分-->
        <%-- 内容 --%>
        <div class="col-sm-10" id="showContent">
            <!--巨幕开始-->
            <div class="jumbotron" id="jumbotron">
                <span id="title">欢迎来到应学视频APP后台管理系统</span>
            </div>
            <!--右边轮播图部分-->
            <div id="carousel-example-generic" class="carousel slide" style="height: 400px; width: 800px"
                 data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="${path}/bootstrap/img/pic1.jpg" style="height: 400px; width: 800px" alt="...">
                        <!--<div class="carousel-caption">
                            图片的说明
                        </div>-->
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic2.jpg" style="height: 400px; width: 800px" alt="...">
                        <!--<div class="carousel-caption">
                            ...
                        </div>-->
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic3.jpg" style="height: 400px; width: 800px" alt="...">
                        <!--<div class="carousel-caption">
                            ...
                        </div>-->
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic4.jpg" style="height: 400px; width: 800px" alt="...">
                        <!--<div class="carousel-caption">
                            ...
                        </div>-->
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>


        </div>
    </div>
</div>


<!--页脚-->
<!--栅格系统-->

</body>
</html>
