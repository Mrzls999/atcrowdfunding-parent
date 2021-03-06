<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/2/16
  Time: 18:44:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 控制面板</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
<%--                            <i class="glyphicon glyphicon-user"></i> ${sessionScope.admin.username} <span class="caret"></span>--%>
                            <i class="glyphicon glyphicon-user"></i> <sec:authentication property="name"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                            <li class="divider"></li>
<%--                            <li><a href="${applicationScope.appPath}/admin/logOut"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>--%>
                            <li><a class="userLogOut" href="javascript:"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                            <form class="userLogOut" action="${pageContext.request.contextPath}/logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </ul>
                    </div>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="查询">
            </form>
        </div>
    </div>
</nav>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.1.min.js"></script>
<script>
    $(".userLogOut").click(function () {
        $(".userLogOut").submit();
    })
</script>