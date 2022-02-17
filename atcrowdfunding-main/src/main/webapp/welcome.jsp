<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/2/16
  Time: 10:40:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${applicationScope.appPath}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${applicationScope.appPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${applicationScope.appPath}/static/css/login.css">
    <style>

    </style>
    <title>登录页面</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="${applicationScope.appPath}/welcome.jsp" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <span style="color:red">${sessionScope.err}</span>
        <div class="form-group has-success has-feedback">
            <label for="inputSuccess1"></label>
            <input name="loginacct" type="text" class="form-control" id="inputSuccess1" placeholder="请输入登录账号" autofocus style="margin-top:10px;">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <label for="inputSuccess2"></label>
            <input name="userpswd" type="text" class="form-control" id="inputSuccess2" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
<%--        <div class="form-group has-success has-feedback">--%>
<%--            <select class="form-control" >--%>
<%--                <option value="member">会员</option>--%>
<%--                <option value="user" selected>管理</option>--%>
<%--            </select>--%>
<%--        </div>--%>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${applicationScope.appPath}/static/jquery/jquery-2.1.1.min.js"></script>
<script src="${applicationScope.appPath}/static/bootstrap/js/bootstrap.min.js"></script>
<script>
    function dologin() {
       $("#loginForm").submit();
    }
</script>
</body>
</html>
