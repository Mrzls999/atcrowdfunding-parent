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

<%@include file="/WEB-INF/common/nav.jsp"%>

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
                                <th style="width: 30px"><input id="checkAll" type="checkbox"></th>
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
                        <input type="text" name="" class="form-control" id="roleName" placeholder="请输入角色名称">
                    </div>
<%--                    <button id="addRole" type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>--%>
<%--                    <button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>--%>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-danger" data-dismiss="modal">取消</button>
                <button id="addRole" type="button" class="btn btn-primary btn-success">保存</button>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/common/js.jsp"%>
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
                    function (res) {//因为res是json格式的，所以不能用===来判断
                        if(res=="403"){
                            layer.msg("没有权限访问",{time:2000,icon:5,shift:6});
                        }else{
                            $("#checkAll").prop("checked",false);//查询数据后将复选框取消掉
                            showRoles(res.list);
                            showPages(res);
                        }
                    }
        )
    }

    //显示角色信息[处理完成后，并为其添加单击事件deleteRole]
    function showRoles(roles){
        let content="";
        if(roles){
            for(let i=0;i<roles.length;i++){
                content+='<tr>';
                content+='	<td>'+(i+1)+'</td>';
                content+='	<td><input class="checkOne" type="checkbox" name="'+(roles[i].id)+'"></td>';
                content+='	<td>'+(roles[i].name)+'</td>';
                content+='	<td>';
                content+='		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                content+='		<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                content+='		<button type="button" class="btn btn-danger btn-xs" name="'+(roles[i].id)+'"><i class=" glyphicon glyphicon-remove"></i></button>';
                content+='	</td>';
                content+='</tr>';
            }
        }
        $("tbody").html(content);
        deleteRole();
        addEditRoleEvent();
    }

    //显示分页信息
    function showPages(pageInfo) {
        let content="";
        if(pageInfo&&pageInfo.navigatepageNums){
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
        }

        $(".pagination").html(content);
    }

    //弹出添加页面
    $("#toRoleAdd").click(function () {
        $('#roleModal').modal({
            show:true,//模态框初始化之后就立即显示出来
            backdrop:false,//关闭-->点击空白处按钮消失
            keyboard:false,//键盘上的 esc 键被按下时不关闭模态框。
        });
        $("#myModalLabel").prop("innerText","角色添加");
        $("#roleName").prop({"name":"","value":""});
    })

    //添加角色
    $("#addRole").click(function () {
        let roleName$ = $("#roleName");
        let idV = roleName$.attr("name");
        let nameV = roleName$.val();
        if(idV==null||idV===""){//如果没有id，则
            $.post("${applicationScope.appPath}/role/addRole",{"name":nameV},function (res) {
                if(res==="yes"){//如果插入成功
                    layer.msg('插入成功', {icon: 6,time: 1000}, function(){
                        $("#roleModal").modal("hide");
                        loadData(100000000);//重新查询数据
                    });
                }else {//如果插入失败
                    layer.msg('插入失败', {icon: 5,time: 1000},function () {
                        $("#roleModal").modal("show");
                    });
                }
            });
        }else {
            editRole(idV,nameV);
        }
    })

    //删除单个角色
    function deleteRole() {
        // $(".btn.btn-danger.btn-xs").on("click",function () {//获取多个拥有多个class的方式1
            $("[class='btn btn-danger btn-xs']").click(function () {//获取多个拥有多个class的方式2；最好是使用第二种，因为方式1会将其他的也添加了单击事件
                let nameV = $(this).attr("name");
                layer.confirm("是否删除此角色信息",{btr:["确定","取消"]},
                function () {//点击确定时调用的方法
                    $.post("${applicationScope.appPath}/role/deleteOneRole",{"id":nameV},function (res) {
                        if(res===1){//如果删除成功
                            layer.msg('删除成功', {icon: 6,time: 1000}, function(){
                                loadData(1);//重新查询数据
                            });
                        }else {
                            layer.msg('删除失败', {icon: 5,time: 1000});//不做任何处理
                        }
                    });
                },
                function () {
                    layer.msg("取消成功",{time:1000});
                })

        })
    }

    //给修改按钮添加单击事件
    function addEditRoleEvent() {
        $("[class='btn btn-primary btn-xs']").click(function () {
            let currentId = $(this).next().attr("name");//获取当前id值
            let currentName = $(this).parent().prev().prop("innerText");//获取当前name值
            // let currentName = $(this).parent().prev().prop("outerText");
            // innerText和outerText在获取文本值时没有区别，但在其他的时候，比如赋值时outerText会将外部的开始和结束标签也包含在里边，导致 要替换的文本值 把一整个标签给替换掉了
            $('#roleModal').modal({
                show:true,//模态框初始化之后就立即显示出来
                backdrop:false,//关闭-->点击空白处按钮消失
                keyboard:false,//键盘上的 esc 键被按下时不关闭模态框。
            });
            $("#myModalLabel").prop("innerText","角色修改");
            $("#roleName").prop({"name":currentId,"value":currentName});
        })
    }

    //修改角色
    function editRole(idV,nameV) {
        $.post("${applicationScope.appPath}/role/editRole",{"id":idV,"name":nameV},function (res) {
            if(res===1){//如果数据库影响的行数为1，则成功
                layer.msg("修改成功",{icon:6,time:1000},function () {
                    $("#roleModal").modal("hide");
                    loadData(1);//重新查询数据
                });
            }else {
                layer.msg("修改失败",{icon:5,time:1000,shift:6});
            }
        })
    }

    //复选框选中全部
    $("#checkAll").click(function () {
        let checkAllFlag = $("#checkAll").prop("checked");
        $(".checkOne").prop("checked",checkAllFlag);
    })

    //删除多个角色
    $("[class='btn btn-danger']").click(function deleteRoles() {
        let Ids=[];
        layer.confirm("是否删除",{btr:["确定","取消"]},
        function () {//确定时执行的方法
            $("[class='checkOne']").each(function (i,checkBox) {//i 下标，checkOne每个dom对象，所以下边的if判断可以直接使用dom的属性值
                if(checkBox.checked){
                    Ids.push($("[class='btn btn-danger btn-xs']")[i].name);//将选中的放进Ids
                }
            });
            $.post("${applicationScope.appPath}/role/deleteOnePageRoles",{"ids":Ids.toString()},function (res){//异步删除
                <%--JSON.stringify(Ids)传到后台的值是："["43","44"]"，需要去除 "、[、]三个符号--%>
                if(res===Ids.length){
                    layer.msg("删除成功",{icon:6,time:1000});
                    loadData(1);
                }else {
                    layer.msg("删除失败",{icon:5,time:1000,shift:6});//shift:6==>晃动
                }
            });
            // 如果像本例中使用的是Ids.toString()/JSON.stringify(Ids)，那么后台，只需要使用String ids来接收即可
            <%--$.ajax({--%>
            <%--    url:"${applicationScope.appPath}/role/deleteOnePageRoles",--%>
            <%--    type:"post",--%>
            <%--    data:{--%>
            <%--        ids:Ids.toString()--%>
            <%--    },--%>
            <%--    success:function (res) {--%>
            <%--        if(res==Ids.length){//因为返回的是字符串，所以只判断值是否相等即可--%>
            <%--            layer.msg("删除成功",{icon:6,time:1000});--%>
            <%--            loadData(1);--%>
            <%--        }else {--%>
            <%--            layer.msg("删除失败",{icon:5,time:1000,shift:6});//shift:6==>晃动--%>
            <%--        }--%>
            <%--    },--%>
            <%--    error:function () {--%>
            <%--        alert("error")//出错跳转的页面--%>
            <%--    },--%>
            <%--    dataType:"text"--%>
            <%--});--%>
        },
        function () {//取消时执行的方法
            layer.msg("取消成功",{time:1000});
        })
    });

</script>
</body>
</html>
