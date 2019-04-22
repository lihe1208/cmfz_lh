<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/china.js"></script>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statistics_china" style="width: 100%;height: 100%;margin-top: 30px;margin-left: 30px">

</div>

<script type="text/javascript">
    var goEasy = new GoEasy({
        appkey: 'BC-c07f58e282ac45f493d42641b6a5259e'
    });

    goEasy.subscribe({
        channel: 'lh',
        onMessage: function (message) {
            alert('收到：' + message.content);
            $(function () {
                $.post("${pageContext.request.contextPath}/userCount/chinaman", function (data) {
                    /*alert(data.value);
                    alert(data.name);*/
                    myChart.setOption({
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '男',
                            data: data.data
                        }]
                    });
                }, "json");

                $.post("${pageContext.request.contextPath}/userCount/chinawoman", function (data) {
                    /* console.log(data);*/
                    myChart.setOption({
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '女',
                            data: data.data
                        }]
                    });
                }, "json");
            });


        }


    });




    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('statistics_china'));

    function randomData() {
        return Math.round(Math.random() * 1000);
    }

    option = {
        title: {
            text: '持名法州APP用户分布图',
            subtext: '2017年6月15日 最新数据',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        // 说明
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男', '女']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],           // 文本，默认为数值文本
            calculable: true
        },
        // 工具箱
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '男',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: []
            },
            {
                name: '女',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: []
            }
        ]
    };
    myChart.setOption(option);
    $(function () {
        $.post("${pageContext.request.contextPath}/userCount/chinaman", function (data) {
            /*alert(data.value);
            alert(data.name);*/
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '男',
                    data: data.data
                }]
            });
        }, "json");

        $.post("${pageContext.request.contextPath}/userCount/chinawoman", function (data) {
            /* console.log(data);*/
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '女',
                    data: data.data
                }]
            });
        }, "json");
    });

</script>
<div id="message" style="width: 50px;height: 38px">
    <h3>消息框</h3>
</div>








<%--mvn install:install-file -Dfile=F:\java\api_sdk\aliyun-java-sdk-core\aliyun-java-sdk-core-3.3.1.jar -DgroupId=com.baizhi -DartifactId=aaa -Dversion=1.0 -Dpackaging=jar



mvn install:install-file -Dfile=F:\java\api_sdk\aliyun-java-sdk-dysmsapi\aliyun-java-sdk-dysmsapi-1.0.0.jar -DgroupId=com.baizhi -DartifactId=bbb -Dversion=1.0 -Dpackaging=jar--%>
















