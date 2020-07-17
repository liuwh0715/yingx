<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/js/echarts.js"></script>
    <%--引入GoEasy的js文件--%>
    <script type="text/javascript" src="https://cdn.goeasy.io/json2.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.17.js"></script>
    <script type="text/javascript">
        /*初始化GoEasy对象*/
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-113617c316ef4dedbd28fdee951ed9c1", //替换为您的应用appkey
        });
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            /*接收消息*/
            goEasy.subscribe({
                channel: "hang_channel", //替换为您自己的channel
                onMessage: function (message) {
                    //获取内容
                    var datas = message.content;
                    //将json字符串转为javascript对象
                    var data = JSON.parse(datas);
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户注册数量趋势图', //标题
                            show: true,
                            link: "${path}/main/main.jsp",
                            target: 'self',
                            subtext: "纯属虚构"
                        },
                        tooltip: {},  //鼠标提示
                        legend: {
                            data: ['小男孩', '小姑娘']  //选项卡
                        },
                        xAxis: {
                            data: data.month  //横坐标
                        },
                        yAxis: {}, //纵坐标   自适应
                        series: [{
                            name: '小男孩',  //选项卡名称
                            type: 'bar',  //图表类型
                            data: data.boys
                        }, {
                            name: '小姑娘',  //选项卡名称
                            type: 'line',  //图表类型
                            data: data.girls
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });

        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;">

</div>
</body>
</html>