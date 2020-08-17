var $evalNameUrl = '/dropDown/getEvalNameDropdown';
var initControllers = (function () {
    return {
        init: function () {
            // 柱状图
            initLine();
            // 柱状图
            // initSimpleBar();
            // 初始化折线
            // initBrokenLine();
            initEvents();
        },
        totalJindu: function () {
            initTotalJindu();
            initEvents();
        },
        teamJindu: function (teamName) {
            initTeamJindu(teamName);
        },
        initTeamJinduAll: function (domID, teamName, gaugeDomID) {
            initTeamJinduWithDate(domID, teamName, gaugeDomID);
        },
        compareTeamWork: function (domID, teamName) {
            compareTeamWork(domID, teamName);
        },
        initTeamMajorJindu: function(domID, teamName, date) {
            initTeamMajorJindu(domID, teamName, date);
        },
        initGauge: function (fatherDomID, gaugeDomID, date) {
            initGauge(fatherDomID, gaugeDomID, date);
        }
    }
})();

// 对比每个小队的计划工作量和实际工作量
function compareTeamWork(domID, teamName) {
    var configure = {
        id: domID,
        titleText: teamName,
        url: '/report/echarts/barStack',
        xAxisName: '',
        yAxisName: '',
    };
    var data = {};
    data['teamName'] = teamName;

    var queryParams = function (params) {
        return BsTool.getFormData("searchFormCondition");
    };
    var myChart = EchartsTool.initBarStack(configure, data);

    // 默认显示当天的各专业进度
    var today = getNowFormatDate();
    var domID = "station_" + teamName.substring(0,1) + "_major";
    initTeamMajorJindu(domID, teamName, today);


    // 编写点击事件
    //  echarts.init($("#" + domID)[0], "eval");
    myChart.getZr().on('click', function (params) {
        var pointInPixel= [params.offsetX, params.offsetY];
        if (myChart.containPixel('grid',pointInPixel)) {
            var pointInGrid=myChart.convertFromPixel({seriesIndex:0},pointInPixel);
            var xIndex=pointInGrid[0];
            var op=myChart.getOption();
            var month = op.xAxis[0].data[xIndex];
            var value = op.series[1].data[xIndex];
            var teamName = op['title'][0]['text'];
            var domID = "station_" + teamName.substring(0,1) + "_major";
            var date = month;
            $("#" + domID).show();
            initTeamMajorJindu(domID, teamName, date);
        }
    });
}

// 显示某一站某天内，其下属专业的所有工作进度
function initTeamMajorJindu(domID, teamName, date) {
    var configure = {
        id: domID,
        titleText: date + "各专业工作量",
        url: '/report/echarts/teamMajorCompareBar',
        xAxisName: '',
        yAxisName: '',
    };
    var data = {};
    data['teamName'] = teamName;
    data['date'] = date;

    var queryParams = function (params) {
        return BsTool.getFormData("searchFormCondition");
    };
    EchartsTool.initBarStack(configure, data);
    // 编写点击事件
    var myChart = echarts.init($("#" + domID)[0], "eval");
    myChart.getZr().on('click', function (params) {
        var pointInPixel= [params.offsetX, params.offsetY];
        if (myChart.containPixel('grid',pointInPixel)) {
            var pointInGrid=myChart.convertFromPixel({seriesIndex:0},pointInPixel);
            var xIndex=pointInGrid[0];
            var op=myChart.getOption();
            var month = op.xAxis[0].data[xIndex];
            var value = op.series[1].data[xIndex];

            var domID = 'station_' + teamName.substring(0, 1) + '_consume';
            var major = month;

            initMajorConsume(domID, teamName, major, date);
        }
    });
}

// 显示各专业在某一天消耗的各种材料，用中空的环表示
function initMajorConsume(domID, teamName, major, date) {
    configure = {
        id: domID,
        url: '/report/echarts/pieSimple',
        titleText: major + ' ' + date + "具体工序统计",
        seriesName: "消耗量",
        seriesRadius: '70%',
        tooltipShow: true, // 不显示tooltip
    };
    var data = {};
    data['teamName'] = teamName;
    data['major'] = major;
    data['date'] = date;

    var queryParams = function (params) {
        return BsTool.getFormData("searchFormCondition");
    }
    EchartsTool.initPieSimple(configure, data);
}

// 单独汇报小队进度，参数为DOM的id和小队名称
function initTeamJinduWithDate(domID, teamName, gaugeDomID) {
    var configure = {
        id: domID,
        url: '/report/echarts/lineStack',
        titleText: teamName,
        xAxisName: '日期', // x轴的名称
        yAxisName: '进度(%)', // y轴的名称
        seriesName: '日期'
    };
    var data = {};
    data['station'] = teamName;

    var queryParams = function () {
        return BsTool.getFormData("searchFormCondition");
    };
    var myChart = EchartsTool.initLineStackTotal(configure, data);

    // 默认显示当天的进度在仪表盘上
    var op = myChart.getOption();
    var axis = op.xAxis[0].data;
    console.log(axis);
    var date = axis[axis.length - 1];
    var value = op.series[0].data[axis.length - 1];

    var gaugeConf = {
        id: gaugeDomID,
        date: date,
        value: value
    };
    EchartsTool.initGauge(gaugeConf);
    addGaugeClickEvent(myChart, gaugeDomID, null);
}

// 添加
function addGaugeClickEvent(myChart, gaugeDomID, date) {
    myChart.getZr().on('click', function (params) {
        var pointInPixel= [params.offsetX, params.offsetY];
        if (myChart.containPixel('grid',pointInPixel)) {
            /*此处添加具体执行代码*/
            var pointInGrid=myChart.convertFromPixel({seriesIndex:0},pointInPixel);
            //X轴序号
            var xIndex=pointInGrid[0];
            //获取当前图表的option
            var op=myChart.getOption();
            //获得图表中我们想要的数据
            var month = op.xAxis[0].data[xIndex];
            var value = op.series[0].data[xIndex];
            // alert("电费回收率点击事件");
            var teamName = op['title'][0]['text'];
            console.log(op);
            console.log(teamName);
            console.log(month+"："+value+"%");

            var date1 = (date == null) ? month : date;
            // 仪表盘显示
            option = {
                tooltip: {
                    formatter: '{a} <br/>{b} : {c}%'
                },
                toolbox: {
                    feature: {
                        restore: {},
                        saveAsImage: {}
                    }
                },
                series: [
                    {
                        name: '当前进度',
                        type: 'gauge',
                        detail: {formatter: '{value}%'},
                        data: [{value: value, name: date1 + "\n完成率"}]
                    }
                ]
            };
            var dom = $("#" + gaugeDomID)[0];
            var domChart = echarts.init(dom, "eval");
            domChart.setOption(option);
        }
    });
}


/**
 * 初始化按钮事件
 */
function initEvents() {
    $("#btn_export").click(function () {
        html2canvas($('#mainContext')[0], {
            onrendered: function (canvas) {
                context = canvas.getContext("2d");
                context.fillStyle = "#fff";
                //返回图片URL，参数：图片格式和清晰度(0-1)
                var pageData = canvas.toDataURL('image/jpeg', 1.0);
                //方向默认竖直，尺寸ponits，格式a4【595.28,841.89]
                var pdf = new jsPDF('', 'pt', 'a4');
                //需要dataUrl格式
                pdf.addImage(pageData, 'JPEG', 0, 0, 595.28, 592.28 / canvas.width * canvas.height);
                pdf.save("echarts图表.pdf");
            },
            background: '#fff'
        })
    })
}


function initLine() {
    var configure = {
        id: 'line-stack',
        url: '/report/echarts/lineStack',
        titleText: '',
        xAxisName: '日期', // x轴的名称
        yAxisName: '访问数', // y轴的名称
        seriesName: '日期'
    };
    var queryParams = function () {
        return BsTool.getFormData("searchFormCondition");
    };
    var data = {};
    data['station'] = "3号站";
    EchartsTool.initLineStack(configure, data);

    echarts.init($("#line-stack")[0], "eval").on('click', function (params) {
        console.log(params);
    });
}


//总体进展，显示所有站的进展
function initTotalJindu() {
    var configure = {
        id: 'line-stack-total',
        url: '/report/echarts/lineStack',
        titleText: '',
        xAxisName: '日期', // x轴的名称
        yAxisName: '进度(%)', // y轴的名称
        seriesName: '日期'
    };
    var data = {};
    data['station'] = "all";

    var queryParams = function () {
        return BsTool.getFormData("searchFormCondition");
    };
    EchartsTool.initLineStackTotal(configure, data);

    var myChart = echarts.init($("#line-stack-total")[0], "eval");

    // myChart.getZr().on('click', function (params) {
    //     var pointInPixel= [params.offsetX, params.offsetY];
    //     if (myChart.containPixel('grid',pointInPixel)) {
    //         /*此处添加具体执行代码*/
    //
    //         var pointInGrid=myChart.convertFromPixel({seriesIndex:0},pointInPixel);
    //         //X轴序号
    //         var xIndex=pointInGrid[0];
    //
    //         //获取当前图表的option
    //         var op=myChart.getOption();
    //
    //         //获得图表中我们想要的数据
    //         var month = op.xAxis[0].data[xIndex];
    //         var value = op.series[0].data[xIndex];
    //         alert("电费回收率点击事件");
    //         console.log(op);
    //         console.log(month+"："+value+"%");
    //
    //     }
    // });
    // var ecConfig = require('echarts/config');
    // myChart.on(ecConfig.EVENT.CLICK, eConsole);

    myChart.on('click', function (params) {
        console.log(params);
        initTeamJindu(params['seriesName']);
    });
}

// 点击总进度之后，会呈现每个队伍的进度，只显示每日完成工作量
function initTeamJindu(teamName) {
    $("#bar-simple-team").show();
    var configure = {
        id: 'bar-simple-team',
        titleText: teamName + "每日完成进度",
        url: '/report/echarts/barSimple',
        xAxisName: '',
        yAxisName: '进度',
        yAxisLabel: {},
        seriesName: '今日完成进度',
    };
    var data = {};
    data['teamName'] = teamName;
    var queryParams = function (params) {
        return BsTool.getFormData("searchFormCondition");
    };
    EchartsTool.initBarSimple(configure, data);
}

// 获取类似YYYY-MM-DD格式的日期
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}