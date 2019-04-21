<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {

        var tb = [{
            iconCls: 'icon-search',
            text: '专辑详情',
            handler: function () {
               /* $('#dd_album').dialog('open');*/
                var row = $('#tt_album').treegrid('getSelected');

                if(row!=null){
                    if(row.size==null){
                        selectOne(row);
                    }else{
                        alert("请选择专辑")
                    }
                }else{
                    alert("至少选择一个专辑")
                }
                /*if (row){
                    /!*alert(row.title)*!/
                    alert(row.title)
                    selectOne(row);
                }
*/
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加专辑',
            handler: function () {
                /*alert('帮助按钮')*/
                $('#dd_album').dialog('open');
            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '添加章节',
            handler: function () {
                var row = $('#tt_album').treegrid('getSelected');
                if(row!=null){
                    if(row.size==null){
                        $('#insert_chapter').dialog('open');
                    }else{
                        alert("请选择一个专辑才能添加")
                    }
                }else{
                    alert("至少选择一个专辑")
                }

            }
        }, '-', {
            iconCls: 'icon-save',
            text: '下载',
            handler: function () {
                var row = $('#tt_album').treegrid('getSelected');
                if (row){
                    alert(row.title)
                    downloadOne(row);
                }
            }
        },'-', {
            iconCls: 'icon-bell',
            text: '播放',
            handler: function () {
                /*var row = $('#tt_album').treegrid('getSelected');
                var x = document.getElementsByName("[name=size]");*/
                /*var obj=document.getElementById("")*/
                if(row!=null){
                    if(row.size==null){
                        /*alert(row)*/
                        alert("请选择章节")
                    }else{
                        alert(row)
                        /*x.play();*/
                    }
                }else{
                    alert("至少选择一个章节")
                }
            }
        }, '-', {
            iconCls: 'icon-redo',
            text: '导入',
            handler: function () {
                location.href="${pageContext.request.contextPath}/album/redo"

            }
        }, '-', {
            iconCls: 'icon-undo',
            text: '导出',
            handler: function () {
                location.href="${pageContext.request.contextPath}/album/undo"
            }
        }];

        /*function play(row) {
            $("#").html("<embed src"+row.downLoadPath+" type='audio/x-pn-realaudio-plugin' autoStart='true' CONTROLS='console' width='570' height='200'"))
        }*/


        $('#tt_album').treegrid({
            //后台Controller查询所有专辑以及对应的章节集合
           /* method:'get',*/
            url:'${pageContext.request.contextPath}/album/queryAll',
            saveUrl: '${pageContext.request.contextPath}/chapter/download',
            idField:'id',//用来标识标识树节点   主干树与分支树节点  ID不能有相同  并且使用相同的字段  否则ID冲突
            treeField:'title',//用来定义树节点   树形表格上要展示的信息   注意使用相同的字段 用来展示对应节点名称
            toolbar:tb,
            fit:true,
            fitColumns:true,
            columns:[[
                {field:'title',title:'名字',width:180},
                {field:'size',title:'章节大小',width:60},
                {field:'duration',title:'章节的时长',width:80}
            ]]
        });

    })
    function updateOne(rowData) {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/album/insertAlbum',
            onSubmit: function () {
                // do some check
                // return false to prevent submit;
            },
            success: function (data) {
                data = JSON.parse(data);
                if(data.isInsert){
                    //关闭对话框
                    $("#dd_album").dialog("close");

                    //刷新
                    $("#tt_album").treegrid("load");
                }else{
                    alert("添加失败，请确认")
                }


            }
        });
    }

    function selectOne(rowData) {
        alert(rowData)
        $('#one_album').dialog('open');
        $("#title1").val(rowData.title);
        $("#boardcast1").val(rowData.boardcast)
        $("#author1").val(rowData.author)
        $("#amount1").val(rowData.amount)
        $("#brief1").val(rowData.brief)
        $("#publishDate1").val(rowData.publishDate)
        $("#albumImg").prop("src","${pageContext.request.contextPath}"+rowData.imgPath)

    }
    function addAlbum() {
        // call 'submit' method of form plugin to submit the form
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/album/insertAlbum',
            onSubmit: function () {
                // do some check
                // return false to prevent submit;
            },
            success: function (data) {

                data = JSON.parse(data);
                if(data.isInsert){
                    //关闭对话框
                    $("#dd_album").dialog("close");

                    //刷新
                    $("#tt_album").treegrid("load");
                }else{
                    alert("添加失败，请确认")
                }


            }
        });

    }

    function addchapter() {
        // call 'submit' method of form plugin to submit the form
        $('#cc').form('submit', {
            url: '${pageContext.request.contextPath}/chapter/insert',
            onSubmit: function () {
                // 表单验证
            },
            success: function (data) {
                data = JSON.parse(data);
                if(data.isInsert){
                    //关闭对话框
                    $("#insert_chapter").dialog("close");
                    //刷新
                    $("#tt_album").treegrid("load");
                }else{
                    alert("添加失败，请确认")
                }


            }
        });

    }
    function downloadOne(row){
       /* var file = $('#tt_album').treegrid("getSelected");*/
        window.location.href="${pageContext.request.contextPath}/chapter/download?downLoadPath="+row.downLoadPath+"&title="+row.title;
    }
</script>
<table id="tt_album" style="width:600px;height:400px"></table>

<div id="dd_album" class="easyui-dialog" title="album Dialog" style="width:500px;height:700px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
           buttons:[{
				text:'保存',
				handler:function(){
				    addAlbum();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">专辑名称:</label>
            <input id="title" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>

        <%--shangchuan<img src="${pageContext.request.contextPath}/image/audioCollection/A-1.jpg" style="weight:140px;height:200px;">--%>
        选择专辑图片：<input class="easyui-filebox" name="file" style="width:150px">



        <div>
            <label for="boardcast">播音支持:</label>
            <input id="boardcast" class="easyui-validatebox" type="text" name="boardcast" data-options="required:false"/>
        </div>

       <%-- <div>
            <label for="publishDate">上传时间：</label>
            <input id="publishDate" class="easyui-validatebox" type="text" name="publishDate" data-options="required:false"/>
        </div>--%>
        <div>
            <label for="author">作者名字:</label>
            <input id="author" class="easyui-validatebox" type="text" name="author" data-options="required:true"/>
        </div>

        <div>
            <label for="amount">集数:</label>
            <input id="amount" class="easyui-validatebox" type="text" name="amount" data-options="required:false"/>
        </div>


        <%--<div>
            <label for="publishDate">上传音乐：</label>
            <input id="publishDate" class="easyui-validatebox" type="text" name="publishDate" data-options="required:false"/>
        </div>--%>

       <%-- 选择音乐：<input type="file" style="width: 90%;"  class="form-control" name="file" >--%>

        <div>
            <label for="brief">专辑简介:</label>
            <input id="brief" class="easyui-validatebox" type="text" name="brief" data-options="required:false"/>
        </div>

    </form>


</div>













<%--展示一个的div--%>
<div id="one_album" class="easyui-dialog" title="album Dialog" style="width:500px;height:700px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
           buttons:[<%--{
				text:'保存',
				handler:function(){
				    addAlbum();
				}
			},--%>{
				text:'关闭',
				handler:function(){
				closeDiv();
				}
			}]">

    <form id="f" method="post" enctype="multipart/form-data">
        <div>
            <label for="title1">专辑名称:</label>
            <input id="title1" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>

        <%--shangchuan<img src="${pageContext.request.contextPath}/image/audioCollection/A-1.jpg" style="weight:140px;height:200px;">--%>
        专辑图片：<img src="" id="albumImg" style="weight:140px;height:200px;">



        <div>
            <label for="boardcast1">播音支持:</label>
            <input id="boardcast1" class="easyui-validatebox" type="text" name="boardcast" data-options="required:false"/>
        </div>

        <%-- <div>
             <label for="publishDate">上传时间：</label>
             <input id="publishDate" class="easyui-validatebox" type="text" name="publishDate" data-options="required:false"/>
         </div>--%>
        <div>
            <label for="author1">作者名字:</label>
            <input id="author1" class="easyui-validatebox" type="text" name="author" data-options="required:true"/>
        </div>

        <div>
            <label for="amount1">集数:</label>
            <input id="amount1" class="easyui-validatebox" type="text" name="amount" data-options="required:false"/>
        </div>

        <div>
            <label for="publishDate1">上传时间:</label>
            <input id="publishDate1" class="easyui-validatebox" type="text" name="publishDate" data-options="required:false"/>
        </div>


        <div>
            <label for="brief1">专辑简介:</label>
            <input id="brief1" class="easyui-validatebox" type="text" name="brief" data-options="required:false"/>
        </div>

    </form>


</div>



<div id="insert_chapter" class="easyui-dialog" title="chapter Dialog" style="width:500px;height:700px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
           buttons:[{
				text:'保存',
				handler:function(){
				    addchapter();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="cc" method="post" enctype="multipart/form-data">
        <div>
            <label for="title2">章节名称:</label>
            <input id="title2" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <label>选择音乐:</label>
        <input type="file" style="width: 90%;"  class="form-control" name="file">
        <label>选择专辑:</label>
        <input id="cc2" name="albumId" class="easyui-combobox" data-options="
                valueField: 'id',
                textField: 'title',
                url: '${pageContext.request.contextPath}/album/queryAll'
            " />

    </form>

</div>

<%--<div id="download_chapter" class="easyui-dialog" title="chapter Dialog" style="width:500px;height:700px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
           buttons:[{
				text:'保存',
				handler:function(){
				    addchapter();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">

    <form id="dd" method="post" enctype="multipart/form-data">
        <div>
            <label for="title3">章节名称:</label>
            <input id="title3" class="easyui-validatebox" type="text" name="title3" data-options="required:true"/>
        </div>

        <label>选择音乐:</label>

        <input type="file" style="width: 90%;"  class="form-control" name="file">
        <label>选择专辑:</label>
        <input id="cc2" name="id" class="easyui-combobox" data-options="
                valueField: 'id',
                textField: 'title',
                url: '${pageContext.request.contextPath}/album/queryAll'
            " />

    </form>


</div>--%>



