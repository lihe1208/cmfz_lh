<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {

        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                //alert('编辑按钮')
                $('#dd_banner').dialog('open');

            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function (rowData) {
                $('#updateDiv').dialog('open');
                //把原本的数据填入到修改对话框中
                $("#title").val(rowData.title);
                $("#id").val(rowData.id);
                $("#imgPath").val(rowData.imgPath);
                if(rowData.status==0){
                    $("#add").prop("checked",true);
                }else{
                    $("#noadd").prop("checked",true);
                }


            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {

                $('#dg_banner').edatagrid('destroyRow');

                $('#dg_banner').edatagrid('load');
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '保存',
            handler: function () {

                $('#dg_banner').edatagrid('saveRow');
            }
        }, '-', {
            iconCls: 'icon-redo',
            text: '导入',
            handler: function () {
                location.href="${pageContext.request.contextPath}/banner/redo"

            }
        }, '-', {
            iconCls: 'icon-undo',
            text: '导出',
            handler: function () {
                location.href="${pageContext.request.contextPath}/banner/undo"
            }
        }];

        $('#dg_banner').edatagrid({
            method: 'get',
            url: '${pageContext.request.contextPath}/banner/queryAll',
            saveUrl: '${pageContext.request.contextPath}/banner/updateBanner',
            updateUrl:'${pageContext.request.contextPath}/banner/updateBanner',
            destroyUrl: '${pageContext.request.contextPath}/banner/deleteBanner',

            fit: true,
            fitColumns: true,
            onDblClickRow:function(rowIndex,rowData){
                //打开修改对话框，把要修改的内容写入到修改对话框中
                toOpenUpdateDialog(rowData);
            },
            pagination: true,
            pageSize: 2,
            pageList: [2, 4, 6, 8, 10],
            columns: [[
                {field: 'title', title: '标题', width: 100},
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                },
                {field: 'createDate', title: '日期', width: 100}
            ]],
            toolbar: tb,


            view: detailview,
            //rowIndex:行的索引
            //rowData ：行数据
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/lun/' + rowData.imgPath + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>描述: ' + rowData.title + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addBanner() {
        // call 'submit' method of form plugin to submit the form
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/banner/insertBanner',
            onSubmit: function () {
                // do some check
                // return false to prevent submit;
            },
            success: function (data) {

                data = JSON.parse(data);
                if(data.isInsert){
                    //关闭对话框
                    $("#dd_banner").dialog("close");

                    //刷新
                    $("#dg_banner").datagrid("load");
                }else{
                    alert("添加失败，请确认")
                }


            }
        });

    }

    function updateBanner() {
        // call 'submit' method of form plugin to submit the form
        $('#updateForm').form('submit', {
            url: '${pageContext.request.contextPath}/banner/updateBanner',
            onSubmit: function () {
                // do some check
                // return false to prevent submit;
            },
            success: function (data) {

                data = JSON.parse(data);
                if(data.isUpload){
                    //关闭对话框
                    $("#updateDiv").dialog("close");
                    //刷新
                    $("#dg_banner").datagrid("load");
                }else{
                    $("#updateDiv").dialog("close");
                    $("#dg_banner").datagrid("load");
                }


            }
        });

    }
    function toOpenUpdateDialog(rowData) {
        //打开修改对话框
        $('#updateDiv').dialog('open');
        //把原本的数据填入到修改对话框中
        $("#title").val(rowData.title);
        $("#id").val(rowData.id);
        $("#imgPath").val(rowData.imgPath);
        if(rowData.status==0){
            $("#add").prop("checked",true);
        }else{
            $("#noadd").prop("checked",true);
        }

    }

</script>
<table id="dg_banner"></table>
<div id="dd_banner" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">图片名称:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="status">直接展示:</label>


            <input type="radio" name="status"  id="status"value="0">展示
            <input type="radio" name="status" id="status"value="1">先不展示
           <%-- <input id="email" class="easyui-validatebox" type="text" name="email" data-options="validType:'email'"/>--%>
        </div>
        选择图片：<input class="easyui-filebox" name="file" style="width:150px">
        <%--<div id="insertBtn">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="doinsert()">提交</a>
        </div>--%>
    </form>


    <%--修改对话框对应的HTML代码--%>
    <div id="updateDiv" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
         data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    updateBanner();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">
        <form id="updateForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" id="id"/>
            图片名称：<input name="title" id="title"/><br/>

            是否展示：<input type="radio" value="1" name="status" id="noadd"/>不展示
            <input type="radio" value="0" name="status" id="add"/>展示</br>

            选择图片：<input class="easyui-filebox" name="file" id="imgPath" style="width:150px">

        </form>
    </div>
</div>
