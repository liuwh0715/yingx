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
    <script src="${path}/js/china.js"></script>
    <script type="text/javascript">
        $(function () {
            $.get('${path}/json/china.json', function (data) {
                var series = [];
                //遍历集合
                for (var i = 0; i < data.length; i++) {
                    var e = data[i];
                    series.push({
                        name: e.sex,
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: e.city
                        // data:[
                        //     {name: '北京',value: 34},
                        //     {name: '天津',value: 45},
                        //     {name: '澳门',value: Math.round(Math.random()*1000)}
                        // ]
                    })
                }
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                var option = {
                    title: {
                        text: '用户分布图',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['女', '男']  //选项卡
                    },
                    visualMap: {
                        min: 0,
                        max: 200,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: series
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