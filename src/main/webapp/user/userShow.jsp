<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/btn.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%-- jquery核心.js --%>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <%--表单验证--%>
    <script src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <%-- boostrap核心.js --%>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <%-- jqGrid核心.js --%>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%-- jqGrid国际化.js --%>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>

    <script>
        //页面加载触发查询所有
        $(function () {
            //页面加载发送ajax请求查询所有
            $.ajax({
                url: "${path}/yxUser/queryAllUser",
                data: {rows: 5, page: 1},
                datatype: "json",
                success: function (data) {
                    init(data)
                }
            })
        })

        //主页数据初始化
        function init(data) {
            var tbody = $("#tbody");
            tbody.empty();
            //主要数据遍历
            $.each(data.rows, function (index, user) {
                var tr = $("<tr></tr>");
                var tdId = $("<td></td>").text(user.id);
                var tdUserName = $("<td></td>").text(user.userName);
                var tdBrief = $("<td></td>").text(user.brief);
                var tdPhone = $("<td></td>").text(user.phone);
                var tdHeadImg = $("<td></td>").html("<img src=\"${pageContext.request.contextPath}/upload/photo/" + user.headImg + "\" class=\"img\" alt=\"\"\n" +
                    "                   width=\"60px\" height=\"40px\">");
                var tdWechat = $("<td></td>").text(user.wechat);
                var tdCreateDate = $("<td></td>").text(user.createDate);
                var tdStatus;
                if (user.status == "激活") {
                    tdStatus = $("<td></td>").html("<button type=\"button\" class=\"btn btn-info\" onclick='updateStatus(\"" + user.id + "\",\"" + user.status + "\")'>冻结</button>")
                } else {
                    tdStatus = $("<td></td>").html("<button type=\"button\" class=\"btn btn-danger\" onclick='updateStatus(\"" + user.id + "\",\"" + user.status + "\")'>解除冻结</button>")
                }

                var del = $(" <button type=\"button\" class=\"btn btn-warning\" onclick='delById(\"" + user.id + "\")'>删除</button>");
                var update = $("<button  data-toggle=\"modal\" data-target=\".bs-example-modal-lg\" type=\"button\" class=\"btn bg-primary\" onclick='updateById(\"" + user.id + "\")'>修改</button>");
                tr.append(tdId).append(tdUserName)
                    .append(tdBrief).append(tdPhone)
                    .append(tdHeadImg).append(tdWechat).append(tdCreateDate).append(tdStatus)
                    .append(del).append(update)
                tbody.append(tr)
            });

            //分页模块 运行此处先清空
            //首页
            var pageul = $("#pageul");
            pageul.empty()
            if (data.page > data.totalpage) {
                var liLift = $(" <li>\n" +
                    "                <a href=\"#\" aria-label=\"Previous\">\n" +
                    "                    <span aria-hidden=\"true\">首页</span>\n" +
                    "                </a>\n" +
                    "            </li>");
                pageul.append(liLift)
            }
            var b = 0;

            for (var a = data.totalpage; a > 0; a--) {
                b++;
                var pageLi = $("  <li><a onclick=\"queryByPage(" + b + ")\">" + b + "</a></li>");
                pageul.append(pageLi)
            }

            //尾页
            if (data.page < data.totalpage) {

                var liRight = $("<li>\n" +
                    "                <a href=\"#\" aria-label=\"Next\">\n" +
                    "                    <span aria-hidden=\"true\">尾页</span>\n" +
                    "                </a>\n" +
                    "            </li>");
            }
            pageul.append(liRight)

        }

        //分页查询
        function queryByPage(b) {
            $.ajax({
                url: "${path}/yxUser/queryAllUser",
                data: {rows: 5, page: b},
                datatype: "json",
                success: function (data) {
                    console.log(data);
                    init(data)
                }
            });
        }

        //添加用户
        function addUser() {
            $.ajax({
                url: "${path}/yxUser/addUser",
                data: $("#UserAddForm").serialize(),
                datatype: "json",
                success: function (data) {
                    //数据添加成功
                    if (data.add == "ok") {
                        alert("文件上传中")
                        console.log($("#headImg").val());
                        var formData = new FormData()
                        formData.append("headImg", document.getElementById("headImg").files[0]);
                        $.ajax({
                            url: "${path}/yxUser/upLode?id=" + data.id,
                            dataType: 'json',
                            method: 'POST', //提交方式
                            async: false,//安全协议提交
                            data: formData,
                            processData: false, // 数据不做处理
                            contentType: false, // 不要设置Content-Type请求头内容类型
                            success: function (data) {
                                console.log("文件上传结果：" + data.update)
                                if (data.update == "ok") {

                                    //添加警告添加成功提示
                                    $("#addOneClassOk").show()
                                    //设置隐藏
                                    setTimeout(function () {
                                        $("#addOneClassOk").hide()
                                        $('#myOneModal').modal('hide')
                                        document.getElementById("OneClassForm").reset();
                                    }, 2000)
                                    init(data)
                                }
                            }
                        })


                    }

                }
            })
        }

        //删除用户信息
        function delById(idd) {
            $.ajax({
                url: "${path}/yxUser/delById",
                data: {id: idd},
                dataType: "json",
                success: function (data) {
                    if (data.del == "ok") {
                        $("#delOk").show()
                        //设置隐藏
                        setTimeout(function () {
                            $("#delOk").hide()
                        }, 2000)
                    }
                    init(data);
                }
            })
        }

        //修改用户信息数据回显
        function updateById(idd) {
            alert("update" + idd)
            $.ajax({
                url: "${path}/yxUser/selectOne",
                data: {id: idd},
                datatype: "json",
                success: function (data) {
                    $("#updateid").attr("value", data.id)
                    var updateTable = $("#updateTable");
                    updateTable.empty();
                    var trUserName = $(" <tr>\n" +
                        "                    <td>用户名：</td>\n" +
                        "                    <td><input type=\"text\" name=\"userName\" class=\"el-input__inner\" value=" + data.userName + "></td>\n" +
                        "                    <td></td>\n" +
                        "                </tr>");

                    var trBrief = $(" <tr>\n" +
                        "                    <td>用户简介：</td>\n" +
                        "                    <td><input type=\"text\" name=\"brief\" class=\"el-input__inner\" value=" + data.brief + "></td>\n" +
                        "                    <td></td>\n" +
                        "                </tr>");

                    var trPhone = $(" <tr>\n" +
                        "                    <td>用户电话：</td>\n" +
                        "                    <td><input type=\"text\" name=\"phone\" class=\"el-input__inner\" value=" + data.phone + "></td>\n" +
                        "                    <td></td>\n" +
                        "                </tr>");

                    var trHeadImg = $("<tr>\n" +
                        "\t\t\t\t\n" +
                        "\t\t\t\t<td>用户头像：</td>\n" +
                        "\t\t\t\t<td rowspan=\"3\">\n" +
                        "\t\t\t\t\t<label id=\"file_upload1_label\" for=\"file_upload1\">\n" +
                        "\t\t\t\t\t\t<img id=\"uploadimg\" src=\"${path}/upload/photo/" + data.headImg + "\" alt=\"\" width=\"100\" height=\"100\" />\n" +
                        "\t\t\t\t\t</label>\n" +
                        "\t\t\t\t\t<input type=\"file\" name=\"image\" class=\"\" id=\"file_upload1\"\tonchange=\"upload_review()\">\n" +
                        "\t\t\t\t</td>\n" +
                        "\t\t\t</tr>"
                    )

                    var trWechat = $(" <tr>\n" +
                        "                    <td>用户微信：</td>\n" +
                        "                    <td><input type=\"text\" name=\"wechat\" class=\"el-input__inner\" value=" + data.wechat + "></td>\n" +
                        "                    <td></td>\n" +
                        "                </tr>");

                    updateTable.append(trUserName).append(trBrief).append(trPhone).append(trWechat).append(trHeadImg)
                    console.log(data);
                }

            })
        }

        //修改数据执行
        function updateAffirm() {
            alert("确认修改执行")
            $.ajax({
                url: "${path}/yxUser/update",
                data: $("#updateForm").serialize(),
                datatype: "json",
                success: function (data) {
                    //添加警告添加成功提示
                    $("#updateOk").show()
                    //设置隐藏
                    //添加警告添加成功提示
                    $("#updateOk").show()
                    //设置隐藏
                    setTimeout(function () {
                        $("#myUpDate").hide()
                        $('#myUpDate').modal('hide')
                    }, 2000)
                    init(data)
                }
            })
        }

        //修改用户状态/激活-冻结-激活
        function updateStatus(idd, stautss) {
            console.log(idd);
            console.log(stautss);
            if (stautss == "激活") {
                stautss = "冻结";
                $.ajax({
                    url: "${path}/yxUser/update",
                    data: {id: idd, status: stautss},
                    datatype: "json",
                    success: function (data) {
                        init(data)
                        console.log(data);
                    }
                });
            } else {
                stautss = "激活";
                $.ajax({
                    url: "${path}/yxUser/update",
                    data: {id: idd, status: stautss},
                    datatype: "json",
                    success: function (data) {
                        init(data)
                        console.log(data);
                    }
                });
            }

        }
    </script>
</head>
<body>
<br>
<%--删除成功动态提示--%>
<div id="delOk" class="alert alert-success alert-dismissable"
     style="display: none">删除成功
</div>

<%--添加用户模态框展示--%>
<div class="modal fade" id="myOneModal" tabindex="-1" role="dialog" aria-labelledby="myOneModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myOneModalLabel">添加用户信息</h4>

            </div>

            <form id="UserAddForm">
                <div class="modal-body">
                    用户名：&nbsp;&nbsp;&nbsp; <input type="text" class="el-input__inner" name="userName"><br>
                    用户简介：<input type="text" class="el-input__inner" name="brief" value=""><br>
                    用户电话：<input type="text" class="el-input__inner" name="phone" value=""><br>
                    用户头像：<input type="file" id="headImg" name="headImg" value=""><br>
                    用户微信：<input type="text" class="el-input__inner" name="wechat" value=""><br>
                </div>
                <div class="modal-footer">
                    <div id="addOneClassOk" class="alert alert-success alert-dismissable"
                         style="display: none">添加成功
                    </div>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="addUser()">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%--修改用户模态框展示--%>
<div class="modal fade bs-example-modal-lg" id="myUpDate" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel">

    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <form id="updateForm">
                <table id="updateTable">
                    <div class="panel-heading text-center">用户修改</div>
                </table>
                <%--id隐藏--%>
                <input type="hidden" name="id" value="" id="updateid">

            </form>
            <div class="form-group">
                <label class="col-md-offset-10 control-label"></label>
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-info" onclick="updateAffirm()">确定</button>
            </div>
            <div id="updateOk" class="alert alert-success alert-dismissable"
                 style="display: none">修改成功
            </div>
        </div>
    </div>
</div>
<%--中部展示--%>
<div class="panel panel-info">
    <!-- Default panel contents -->
    <div class="panel-heading">用户信息</div>
    <div class="panel-body">
        <div>
            <!-- 面板组 -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                          data-toggle="tab">用户管理</a></li>

                <li role="presentation"><a href="#profile" data-toggle="modal" data-target="#myOneModal">添加用户</a></li>

            </ul>

            <!-- 标签组 -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <!-- 数据展示-->
                    <table class="table table-hover">
                        <tr>
                            <td>Id</td>
                            <td>用户名名字</td>
                            <td>用户简介</td>
                            <td>用户手机号</td>
                            <td>用户头像</td>
                            <td>微信</td>
                            <td>用户注册时间</td>
                            <td style="width: 100px">状态</td>
                            <td>操作</td>
                        </tr>
                        <tbody id="tbody">
                        </tbody>
                    </table>
                </div>

                <%--分页模块--%>
                <nav aria-label="Page navigation" class="text-center">
                    <ul class="pagination  text-center" id="pageul">

                    </ul>
                </nav>

            </div>

        </div>
    </div>
</div>
</body>
</html>