/**
 * 用户短信列表
 */
var pageCurr;
$(function () {
    layui.use('table', function () {
        var table = layui.table
            , form = layui.form;

        tableRecomIns = table.render({
            elem: '#recomTable'
            , url: '/recommend/getNotes'
            , method: 'post' //默认：get请求
            , cellMinWidth: 80
            , page: true
            ,limit: 30,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                , limitName: 'limit' //每页数据量的参数名，默认：limit
            }, response: {
                statusName: 'code' //数据状态的字段名称，默认：code
                , statusCode: 200 //成功的状态码，默认：0
                , countName: 'totals' //数据总数的字段名称，默认：count
                , dataName: 'list' //数据列表的字段名称，默认：data
            },frozenColumns:[[
                    {field:'id',title:'ID',width:80}
                ]]
            , cols: [[
                // {checkbox: true, fixed: true}
                {type: 'checkbox'}
                // , {field: 'id', title: 'ID', width: 80, sort: true,hidden:true}
                , {field: 'smsTitle', width:100, title: '短信标题'}
                , {field: 'smsTemplate', title: '短信内容'}
                ,{field:'sendCnt',title: '发送人数',width:100, edit: 'text'}
                // , {fixed: 'right', title: '操作', width: 140, align: 'center', toolbar: '#optBar'}
            ]]
            , done: function (res, curr, count) {
                pageCurr = curr;
            }
        });

        //监听提交
        form.on('submit(searNoteSubmit)', function (data) {
            // TODO 校验
            load(data);
            return false;
        });

        form.on('submit(sendSubmit)',function(){
            //配置一个透明的询问框
            layer.msg('还未完成,正在进行中', {
                time: 200000, //20s后自动关闭
                btn: [ '知道了', '哦']
            });
            return false;
        });
    });
    //搜索框
    layui.use(['form', 'laydate'], function () {
        var form = layui.form, layer = layui.layer;
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searNoteSubmit)', function (data) {
            //重新加载table
            load(data);
            return false;
        });
    });
});

function load(obj) {
    //重新加载table
    tableRecomIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

