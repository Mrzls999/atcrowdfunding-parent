<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/3/5
  Time: 11:40:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

<%@include file="/WEB-INF/common/css.jsp"%>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
        a{
            cursor:pointer;
        }
    </style>
    <title>角色页面</title>
</head>

<body>

<%@include file="/WEB-INF/common/nav.jsp"%>>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/common/side.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" onclick="loadData(1)"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <!--toggle:若关闭则弹出，若弹出则关闭；target：目标地址-->
                    <button type="button" class="btn btn-primary" id="toRoleAdd" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th style="width: 30px">#</th>
                                <th style="width: 30px"><input type="checkbox"></th>
                                <th>名称</th>
                                <th style="width: 100px;">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%--添加角色信息--%>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" style="text-align: center">
                                    <ul class="pagination">
                                        <%--添加角色分页信息--%>
                                    </ul>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="roleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">角色添加</h4>
            </div>
            <div class="modal-body">
                <form method="post" role="form">
                    <div class="form-group">
                        <label for="roleName">角色名称</label>
                        <input type="text" name="name" class="form-control" id="roleName" placeholder="请输入角色名称">
                    </div>
                    <button id="addRole" type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                </form>
            </div>
<%--            <div class="modal-footer">--%>
<%--                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
<%--                <button type="button" class="btn btn-primary">Save changes</button>--%>
<%--            </div>--%>
        </div>
    </div>
</div>


<%@include file="/WEB-INF/common/js.jsp"%>>
<script src="${applicationScope.appPath}/static/layer/layer.js"></script>

<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        // loadData(1,3);
        loadData(1);//页面静态元素加载完成之后立马异步获取数据
    });

    //异步获取角色和分页信息 pageNum pageSize keyword
    let keyWord;//查询条件
    // function loadData(pageNum,pageSize) {
    function loadData(pageNum) {
        // $(" #test ").val()
        // $(" input[ name='test' ] ").val()
        // $(" input[ type='text' ] ").val()
        // $(" input[ type='text' ]").attr("value")
        keyWord=$("input[ class='form-control has-success']").val();
        //$.ajax({}) $.get(url,param,function(res){}) $.post() $.getJSON()
        $.getJSON("${applicationScope.appPath}/role/loadData",
                    {"pageNum":pageNum,"pageSize":3,"keyWord":keyWord},
                    function (res) {
                        showRoles(res.list);
                        showPages(res);
                    }
        )
    }
    //显示角色信息
    function showRoles(roles){
        let content="";
        for(let i=0;i<roles.length;i++){
            content+='<tr>';
            content+='	<td>'+(i+1)+'</td>';
            content+='	<td><input type="checkbox"></td>';
            content+='	<td>'+(roles[i].name)+'</td>';
            content+='	<td>';
            content+='		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            content+='		<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
            content+='		<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            content+='	</td>';
            content+='</tr>';
        }
        $("tbody").html(content);
    }
    //显示分页信息
    function showPages(pageInfo) {
        let content="";
        if(pageInfo.isFirstPage&&pageInfo.pageSize!==0){
            content+='<li class="disabled"><a href="#">上一页</a></li>';
        }else {
            content+='<li><a onclick="loadData('+(pageInfo.pageNum-1)+')">上一页</a></li>';
        }
        for(let i=0;i<pageInfo.navigatepageNums.length;i++){
            if(pageInfo.pageNum===pageInfo.navigatepageNums[i]){
                content+='<li class="active"><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+pageInfo.navigatepageNums[i]+' <span class="sr-only">(current)</span></a></li>';
            }else {
                content+='<li><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+pageInfo.navigatepageNums[i]+' <span class="sr-only">(current)</span></a></li>';
            }
        }
        if(pageInfo.isLastPage){
            content+='<li class="disabled"><a href="#">下一页</a></li>';
        }else {
            content+='<li><a onclick="loadData('+(pageInfo.pageNum+1)+')">下一页</a></li>';
        }

        $(".pagination").html(content);
    }

    //弹出添加页面
    $("#toRoleAdd").click(function () {
        $('#roleModal').modal({
            show:true,//模态框初始化之后就立即显示出来
            backdrop:false,//关闭-->点击空白处按钮消失
            keyboard:false,//键盘上的 esc 键被按下时不关闭模态框。
        })
    })

    $("#addRole").click(function () {
        let name = $("#roleName").val()
        $.post("${applicationScope.appPath}/role/addRole",{"name":name},function (res) {
            if(res===1){//如果插入成功
                layer.msg("插入成功",{time:2000,icon:6},function(){
                    $("#roleModal").modal("hide");//如果插入成功则关闭
                });//卧槽，有多行语句时得加分号，不加竟然不报错，但是不成功有时候
                // loadData(10000000);//跳转到最后一页
            }else{//如果插入失败
                layer.msg("插入失败",{time:3000,icon:5,shift:6})
            }
        })
    })


</script>
</body>
</html>
