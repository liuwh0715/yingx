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
    });

    function pageInit() {
        $("#userTable").jqGrid({
            url: "${path}/user/queryByPage",   //page,rows   //返回数据：total总页数 records总条数  page当前页  row数据
            editurl: "${path}/user/edit",
            datatype: "json",
            rowNum: 2,
            rowList: [10, 20, 30],
            pager: '#userPage',
            styleUI: "Bootstrap",
            autowidth: true,
            height: "auto",
            viewrecords: true,  //是否显示总条数
            colNames: ['ID', '昵称', '手机号', '头像', '描述', '注册时间', '状态'],
            colModel: [
                {name: 'id', index: 'id', width: 55, align: "center"},
                {name: 'username', editable: true, index: 'invdate', width: 90, align: "center"},
                {name: 'phone', editable: true, index: 'invdate', width: 90, align: "center"},
                {
                    name: 'headImg',
                    editable: true,
                    index: 'name asc, invdate',
                    edittype: "file",
                    width: 100,
                    align: "center",
                    //参数：各子的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/upload/photo/" + cellvalue + "' width='200px' height='100px'   >"
                    }
                },
                {name: 'brief', editable: true, index: 'amount', width: 80, align: "center"},
                {name: 'createDate', index: 'tax', width: 80, align: "center"},
                {
                    name: 'status', index: 'note', width: 150, sortable: false, align: "center",
                    //参数：各子的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == 1) { //1=正常   绿色按钮   点击冻结
                            return "<button id='frozen' class='btn btn-success' >冻结</button>"
                        } else { //0=不正常   红色按钮   点击解除冻结
                            return "<button id='upfrozen' class='btn btn-danger' >解除冻结</button>"
                        }
                    }
                }
            ]
        });

        //编辑工具栏
        $("#userTable").jqGrid('navGrid', '#userPage',
            {edit: true, add: true, del: true, addtext: "添加"},
            {},  //修改之后的额外操作
            {
                closeAfterAdd: true,//关闭添加框
                afterSubmit: function (data) {  //添加成功之后执行的内容
                    //1.数据入库    文件数据不对   文件没有上传
                    //2.文件异步上传   添加成功之后  上传
                    //3.修改文件路径   （id,要的的字段内容）
                    //id=  data.responseText
                    $.ajaxFileUpload({
                        url: "${path}/user/headUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "headImg", //文件选择框的id属性，即<input type="file">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },  //添加之后的额外操作
            {}   //删除之后的额外操作
        );
    }


</script>

<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <%--选项卡--%>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home">用户管理</a></li>
        </ul>
    </div>
    <br>

    <%--按钮组--%>
    <div>
        <button class="btn btn-info">导出用户信息</button>
        <button class="btn btn-success">导入用户信息</button>
    </div>
    <br>

    <%--表格--%>
    <table id="userTable"/>
    <%--工具栏--%>
    <div id="userPage"/>


</div>
