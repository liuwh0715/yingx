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

    //初始化父级表格
    function pageInit() {
        $("#cateTable").jqGrid({
            url: "${path}/category/queryByPage",   //page,rows   //返回数据：total总页数 records总条数  page当前页  row数据
            editurl: "${path}/category/edit",
            datatype: "json",
            rowNum: 3,
            rowList: [5, 10, 15],
            pager: '#catePage',
            styleUI: "Bootstrap",
            autowidth: true,
            height: "auto",
            viewrecords: true,  //是否显示总条数
            colNames: ['ID', '类别名'],
            colModel: [
                {name: 'id', index: 'id', width: 55, align: "center"},
                {name: 'cateName', editable: true, index: 'invdate', width: 90, align: "center"}

            ],
            subGrid: true, //是否开启子表格
            subGridRowExpanded: function (subgrid_id, row_id) {
                addSubGrid(subgrid_id, row_id)
            }
            //subgrid_id 当开启子表格时 会动态创建div 它的id值
            //row_id  行id
        });
        //父表格工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage', {edit: true, add: true, del: true, addtext: "添加"},
            {closeAfterEdit: true},//修改框操作
            {closeAfterAdd: true},//关闭添加框
            {
                afterSubmit: function (data) {  //删除成功之后执行的内容
                    //1.数据入库    文件数据不对   文件没有上传
                    //2.文件异步上传   添加成功之后  上传
                    //3.修改文件路径   （id,要的的字段内容）
                    //id=  data.responseText
                    console.log(data);
                    //alert(data);
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

    function addSubGrid(subgridId, rowId) {
        var subgridTableId, pagerId;
        subgridTableId = subgridId + "Tale";
        pagerId = "Page" + subgridTableId;
        //创建子表格和工具栏
        $("#" + subgridId).html(
            "<table id='" + subgridTableId + "' class='scroll'/>" +
            "<div id='" + pagerId + "' class='scroll'/>"
        );
        //初始化子表格
        jQuery("#" + subgridTableId).jqGrid(
            {
                url: "${path}/category/queryTwoByPage?parentId=" + rowId, //根据一级类别的id查该类别的二级类别
                editurl: "${path}/category/edit?parentId=" + rowId,
                datatype: "json",
                rowNum: 3,
                pager: "#" + pagerId,
                rowList: [5, 10, 15],
                styleUI: "Bootstrap",
                autowidth: true,
                height: "auto",
                viewrecords: true,  //是否显示总条数
                colNames: ['ID', '类别名'],
                colModel: [
                    {name: 'id', index: 'id', width: 55, align: "center"},
                    {name: 'cateName', editable: true, index: 'invdate', width: 90, align: "center"}

                ],

            });
        //子表格工具栏
        jQuery("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId, {edit: true, add: true, del: true},
            {closeAfterEdit: true},//修改框操作
            {closeAfterAdd: true},//关闭添加框
            {}//删除框操作
        );
    }


</script>

<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <%--表格--%>
    <table id="cateTable"/>
    <%--工具栏--%>
    <div id="catePage"/>


    <div class="alert alert-success" style="display: none" id="addFilesDiv">
        <button class="close">&times;</button>
        <span id="addFilesMsg"></span>
    </div>

</div>