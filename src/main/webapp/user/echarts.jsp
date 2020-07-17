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
    <script type="text/javascript">
        $(function () {
            $.post('${path}/user/echarts', function (data) {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户注册量', //标题
                        link: '${path}/main/main.jsp',
                        subtext: '用户', //副标题
                        target: 'self'
                    },
                    tooltip: {},
                    legend: {
                        data: ['男生', '女生'] //选项卡
                    },
                    xAxis: {
                        data: ["1月", "2月", "3月", "4月", "5月", "6月"]
                    },
                    yAxis: {},
                    series: [{
                        name: '男生',
                        type: 'bar',
                        data: [5, 20, 36, 10, 10, 20]
                    }, {
                        name: '女生',
                        type: 'bar',
                        data: [5, 20, 36, 10, 10, 20]
                    }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }, 'JSON');

        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;">

</div>
</body>
</html>