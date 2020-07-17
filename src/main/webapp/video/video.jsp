<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<style>
    th {
        text-align: center;
    }
</style>
<script>
    $(function () {
        pageInit();
        /*
        * 点击发送验证码
        * */
        $("#sendPhoneCode").click(function () {
            //获取手机号
            var phone = $("#phone").val();
            alert(phone);
            //发送验证码请求
            $.post("${path}/video/sendPhoneCode", {"phone": phone}, function (data) {
                /* 信息提示 */
                $("#addFilesMsg").text(data.msg);
                $("#addFilesDiv").show();
                setTimeout(function () {
                    $("#addFilesDiv").hide();
                }, 3000);
                //清空input
                var phone = $("#phone").val("");
            }, "JSON");
        })
    });

    //初始化父级表格
    function pageInit() {
        $("#videoTable").jqGrid({
            url: "${path}/video/queryByPage",   //page,rows   //返回数据：total总页数 records总条数  page当前页  row数据
            editurl: "${path}/video/edit",
            datatype: "json",
            rowNum: 9,
            rowList: [5, 10, 15],
            pager: '#videoPage',
            styleUI: "Bootstrap",
            autowidth: true,
            height: "auto",
            viewrecords: true,  //是否显示总条数
            colNames: ['ID', '名称', '视频', '上传时间', '描述', '封面', '所属类别', '所属用户'],
            colModel: [
                {name: 'id', index: 'id', width: 55, align: "center"},
                {name: 'title', editable: true, index: 'invdate', width: 90, align: "center"},
                {
                    name: 'videoPath', editable: true, edittype: "file", index: 'invdate', width: 90, align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        console.log(rowObject);
                        return "<video  src='" + cellvalue + "' controls='controls' width='100px' heigt='200px' />";
                    }
                },
                {name: 'createDate', index: 'invdate', width: 90, align: "center"},
                {name: 'brief', editable: true, index: 'invdate', width: 90, align: "center"},
                {
                    name: 'coverPath', index: 'invdate', width: 90, align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        console.log(rowObject);
                        return "<img  src='" + cellvalue + "'  width='100px' heigt='100px' />";
                    }
                },
                {name: 'category.id', editable: true, index: 'invdate', width: 90, align: "center"},
                {name: 'user.id', editable: true, index: 'invdate', width: 90, align: "center"}
            ]
        });
        //父表格工具栏
        $("#videoTable").jqGrid('navGrid', '#videoPage', {edit: true, add: true, del: true, addtext: "添加"},
            {
                closeAfterEdit: true,  //关闭对话框
                beforeShowForm: function (obj) {
                    obj.find("#videoPath").attr("disabled", true);//禁用input
                }
            }, //执行修改之后的额外操作
            {
                closeAfterAdd: true,//关闭添加框
                afterSubmit: function (data) {  //添加成功之后执行的内容
                    console.log(data);
                    //1.数据入库    文件数据不对   文件没有上传
                    //2.文件异步上传   添加成功之后  上传
                    //3.修改文件路径   （id,要的的字段内容）
                    //id=  data.responseText
                    $.ajaxFileUpload({
                        url: "${path}/video/videoUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "videoPath", //文件选择框的id属性，即<input type="file">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },  //添加之后的额外操作
            {
                afterSubmit: function (data) {  //删除成功之后执行的内容
                    console.log(data);
                    if (data.responseJSON.msg == 'error') {
                        /* 信息提示 */
                        $("#addFilesMsg").text("禁止该操作");
                        $("#addFilesDiv").show();
                        setTimeout(function () {
                            $("#addFilesDiv").hide();
                        }, 3000)
                    }

                    return "hello";
                }
            }//删除框操作

        );
    }
</script>

<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>
    <%--选项卡--%>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home">用户管理</a></li>
        </ul>
    </div>
    <%--验证码输入框--%>
    <div class="input-group" style="width: 300px;float: right">
        <input type="text" id="phone" class="form-control" placeholder="请输入手机号" aria-describedby="basic-addon2">
        <span class="input-group-addon" id="sendPhoneCode">点击发送验证码</span>
    </div>
    <br><br>
    <%--表格--%>
    <table id="videoTable"/>
    <%--工具栏--%>
    <div id="videoPage"/>
    <div class="alert alert-success" style="display: none" id="addFilesDiv">
        <button class="close">&times;</button>
        <span id="addFilesMsg"></span>
    </div>

</div>