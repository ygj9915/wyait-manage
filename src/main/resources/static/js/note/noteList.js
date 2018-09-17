/**
 * 短信列表
 */
var pageCurr;
$(function () {
    layui.use('table', function () {
        var table = layui.table
            , form = layui.form;

        tableNoteIns = table.render({
            elem: '#noteList'
            , url: '/note/getNotes'
            , method: 'post' //默认：get请求
            , cellMinWidth: 80
            , page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                , limitName: 'limit' //每页数据量的参数名，默认：limit
            }, response: {
                statusName: 'code' //数据状态的字段名称，默认：code
                , statusCode: 200 //成功的状态码，默认：0
                , countName: 'totals' //数据总数的字段名称，默认：count
                , dataName: 'list' //数据列表的字段名称，默认：data
            }
            , frozenColumns:[[
                    {field:'id',title:'ID',width:80}
                ]]
            , cols: [[
                {type: 'numbers'}
                // , {field: 'id', title: 'ID', width: 80, unresize: true, sort: true}
                , {field: 'smsTitle', title: '短信标题'}
                , {field: 'smsTemplate', title: '短信内容'}
                , {fixed: 'right', title: '操作', width: 140, align: 'center', toolbar: '#optBar'}
            ]]
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr = curr;
            }
        });

        //监听在职操作
        form.on('switch(isJobTpl)', function (obj) {
            //console.log(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
            var data = obj.data;
            setJobUser(obj, this.value, this.name, obj.elem.checked);
        });
        //监听工具条
        table.on('tool(noteTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                delNote(data,data.id);
            } else {
                //编辑
                getNoteInfo( data.id);
            }
        });

    });
    //搜索框
    layui.use(['form', 'laydate'], function () {
        var form = layui.form, layer = layui.layer;
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function (data) {
            //重新加载table
            load(data);
            return false;
        });
    });
});

function getNoteInfo(id) {
    //回显数据
    $.get("/note/getNoteInfo", {"id": id}, function (data) {
        if (isLogin(data)) {
            if (data.msg == "ok" && data.note != null) {

                $("#id").val(data.note.id == null ? '' : data.note.id);
                $("#smsTitle").val(data.note.smsTitle == null ? '' : data.note.smsTitle);
                $("#smsTemplate").val(data.note.smsTemplate == null ? '' : data.note.smsTemplate);


                openNote(id, "编辑渠道");
                //重新渲染下form表单中的复选框 否则复选框选中效果无效
                // layui.form.render();
                layui.form.render('checkbox');
            } else {
                //弹出错误提示
                layer.alert(data.msg, function () {
                    layer.closeAll();
                });
            }
        }
    });
}

function openNote(id, title) {
    if (id == null || id == "") {
        $("#id").val("");
    }
    layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['550px'],
        content: $('#setChannel'),
        end: function () {
            cleanChannel();
        }
    });
}

function cleanChannel() {
    $("#smsTitle").val("");
    $("#smsTemplate").val("");
    $("#id").val("");
}

function delNote(obj, id) {
    if (null != id) {
        layer.confirm('您确定要删除短信模板吗？', {
            btn: ['确认', '返回'] //按钮
        }, function () {
            $.post("/channel/delChannel", {"id": id}, function (data) {
                if (isLogin(data)) {
                    if (data == "ok") {
                        //回调弹框
                        layer.alert("删除成功！", function () {
                            layer.closeAll();
                            //加载load方法
                            load(obj);//自定义
                        });
                    } else {
                        layer.alert(data, function () {
                            layer.closeAll();
                            //加载load方法
                            load(obj);//自定义
                        });
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
}

function load(obj) {
    //重新加载table
    tableNoteIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

