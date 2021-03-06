<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/2/18
  Time: 15:17:47
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

<%@ include file="/WEB-INF/common/css.jsp"%>
  <link rel="stylesheet" href="css/doc.min.css">
  <style>
    .tree li {
      list-style-type: none;
      cursor:pointer;
    }
  </style>
</head>

<body>

<%@ include file="/WEB-INF/common/nav.jsp"%>

<div class="container-fluid">
  <div class="row">
    <%@ include file="/WEB-INF/common/side.jsp"%>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <ol class="breadcrumb">
        <li><a href="#">首页</a></li>
        <li><a href="#">数据列表</a></li>
        <li class="active">新增</li>
      </ol>
      <div class="panel panel-default">
        <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
        <div class="panel-body">
          <form action="${applicationScope.appPath}/admin/saveAdmin" method="post" role="form">
            <div class="form-group">
              <label for="exampleInputPassword1">登陆账号</label>
              <input type="text" name="loginacct" class="form-control" id="exampleInputPassword1" placeholder="请输入登陆账号">
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">用户名称</label>
              <input type="text" name="username" class="form-control" id="exampleInputPassword1" placeholder="请输入用户名称">
            </div>
            <div class="form-group">
              <label for="exampleInputEmail1">邮箱地址</label>
              <input type="email" name="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址">
              <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
            <button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">帮助</h4>
      </div>
      <div class="modal-body">
        <div class="bs-callout bs-callout-info">
          <h4>测试标题1</h4>
          <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
        </div>
        <div class="bs-callout bs-callout-info">
          <h4>测试标题2</h4>
          <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
        </div>
      </div>
      <!--
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
      -->
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/common/js.jsp"%>
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
  });
</script>
</body>
</html>
