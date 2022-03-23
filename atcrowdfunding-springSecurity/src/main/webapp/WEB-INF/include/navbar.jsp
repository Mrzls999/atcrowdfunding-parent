<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<div class="layui-header">
	<div class="layui-logo" onclick="location.href='${PATH }/main.html'">武林秘籍管理系统</div>
	<!-- 头部区域（可配合layui已有的水平导航） -->
	<ul class="layui-nav layui-layout-left">
		<li class="layui-nav-item"><a href="">控制台</a></li>
		<li class="layui-nav-item"><a href="">商品管理</a></li>
		<li class="layui-nav-item"><a href="">用户</a></li>
		<li class="layui-nav-item"><a href="javascript:;">其它系统</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="">邮件管理</a>
				</dd>
				<dd>
					<a href="">消息管理</a>
				</dd>
				<dd>
					<a href="">授权管理</a>
				</dd>
			</dl></li>
	</ul>
	<ul class="layui-nav layui-layout-right">
		<li class="layui-nav-item"><a href="javascript:;"> <img
				src="http://t.cn/RCzsdCq" class="layui-nav-img"> xx
		</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="">基本资料</a>
				</dd>
				<dd>
					<a href="">安全设置</a>
				</dd>
			</dl></li>
<%--		<li class="layui-nav-item"><a href="${PATH}/logout">退出</a></li>使用超链接方式退出，这样需要禁用csrf--%>
		<li class="layui-nav-item"><a href="javascript:;">退出</a></li><%--不禁用csrf来退出[利用下边的form表单]--%>
		<form class="userLogOut" action="${PATH}/logout" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</ul>
</div>
<script src="${PATH}/layui/jquery.min.js"></script>
<script>
	$(".layui-nav-item").click(function (){//点击退出，提交form表单，进行退出
		$(".userLogOut").submit();
	})
</script>