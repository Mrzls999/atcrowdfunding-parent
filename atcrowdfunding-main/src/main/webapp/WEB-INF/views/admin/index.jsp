<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/2/17
  Time: 23:47:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  </style>
  <title>用户维护</title>
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
          <form action="${applicationScope.appPath}/admin/index" method="post" id="adminQueryByKeyWord" class="form-inline" role="form" style="float:left;">
            <div class="form-group has-feedback">
              <div class="input-group">
                <div class="input-group-addon">查询条件</div>
                <input class="form-control has-success" name="keyWord" value="${param.keyWord}" type="text" placeholder="请输入查询条件">
              </div>
            </div>
            <button type="button" class="btn btn-warning" onclick="$('#adminQueryByKeyWord').submit()"><i class="glyphicon glyphicon-search"></i> 查询</button>
          </form>
          <button type="button" class="btn btn-danger deleteSelectedAdmins" onclick="deleteAdmins()" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
          <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${applicationScope.appPath}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
          <br>
          <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
              <tr >
                <th style="width: 30px">#</th>
                <th style="width: 30px"><input type="checkbox" class="adminCheckAll"/></th>
                <th>账号</th>
                <th>名称</th>
                <th>邮箱地址</th>
                <th style="width: 100px" >操作</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${pageInfo.list}" var="admin" varStatus="status">
                <tr>
                  <td>${status.count}</td>
                  <td><input type="checkbox" id="${admin.id}" class="adminCheck"></td>
                  <td>${admin.loginacct}</td>
                  <td>${admin.username}</td>
                  <td>${admin.email}</td>
                  <td>
                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                    <button type="button" class="btn btn-primary btn-xs"
                            onclick="window.location.href = '${applicationScope.appPath}/admin/toEdit?id=${admin.id}&pageNum=${pageInfo.pageNum}'" >
                      <i class=" glyphicon glyphicon-pencil"></i>
                    </button>
                    <button type="button" class="btn btn-danger btn-xs" onclick="deleteAdminById(${admin.id})">
                      <i class=" glyphicon glyphicon-remove"></i>
                    </button>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
              <tfoot>
              <tr >
                <td colspan="6" align="center">
                  <ul class="pagination">

                    <c:if test="${pageInfo.isFirstPage || pageInfo.list.size()==0}">
                      <li class="disabled"><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=1">上一页</a></li>
                    </c:if>

                    <c:if test="${not pageInfo.isFirstPage && pageInfo.list.size()!=0}">
                      <li ><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum-1}">上一页</a></li>
                    </c:if>

                    <c:forEach items="${pageInfo.navigatepageNums}" var="i">
                      <c:if test="${pageInfo.pageNum==i}">
                        <li class="active"><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${i}">${i} <span class="sr-only">(current)</span></a></li>
                      </c:if>

                      <c:if test="${pageInfo.pageNum!=i}">
                        <li><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${i}">${i} <span class="sr-only">(current)</span></a></li>
                      </c:if>
                    </c:forEach>

                    <c:if test="${pageInfo.isLastPage}">
                      <li class="disabled"><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pages}">下一页</a></li>
                    </c:if>

                    <c:if test="${not pageInfo.isLastPage}">
                      <li ><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum+1}">下一页</a></li>
                    </c:if>

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

<%@include file="/WEB-INF/common/js.jsp"%>
<script src="${applicationScope.appPath}/static/layer/layer.js"></script>
<script type="text/javascript">
  $(function () {
    //侧边栏的打开关闭
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
    //用户全选效果
    let adminCheckAll = $(".adminCheckAll");
    adminCheckAll.click(function () {//方式1
      //$(".adminCheck").attr("checked",adminCheckAll[0].checked);//这种为啥只能用一次，第二次就不行了呢？在动过property的情况下 attribute不会同步property
      $(".adminCheck").prop("checked",adminCheckAll[0].checked);
    })

    // $(".adminCheckAll").click(function () {
    // $.each($(".adminCheck"),function (i, checkBox) {方式2
    //   现在需要获取复选框的checked属性(true,false)，checked属性属于dom对象
    //   jQuery对象转换为dom对象的方式 $(".adminCheckAll")[0] / $(".adminCheckAll").get(0)
    //   $(".adminCheckAll")[0].checked
    //   checkBox.checked=$(".adminCheckAll")[0].checked;
    // });
    //   $(".adminCheck").each(function (i,checkBox) {方式3
    //       checkBox.checked=$(".adminCheckAll")[0].checked;
    //   })
    // })
  });

  function deleteAdminById(id){//删除单个用户
    layer.confirm("是否删除", {btr:['确定','取消']},
            function (){
              window.location.href = "${applicationScope.appPath}/admin/deleteAdmin?id="+id;
              layer.alert("删除成功");
            },
            function (){
              layer.alert("取消成功");
            }
    )
  }

  function deleteAdmins(){//删除选中的用户
      let Ids = [];
      $(".adminCheck").each(function (i,checkBox) {
        if(checkBox.checked){
          Ids.push($(".adminCheck")[i].id);
        }
      })
      if(Ids.length===0){
        layer.msg("未选中任何用户",{time:1500,icon:5,shift:6});
      }else{
        layer.confirm("是否删除", {btr:['确定','取消']},
                function (){
                  window.location.href="${applicationScope.appPath}/admin/deleteSelectedAdmins?ids="+Ids;
                  layer.alert("删除成功");
                },
                function (){
                  layer.alert("取消成功");
                }
        )
      }
  }


</script>
</body>
</html>

