<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/2/16
  Time: 11:03:41
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

<%--  静态包含【编译时形成成一个文件】--%>
  <%@include file="/WEB-INF/common/css.jsp"%>
<%--  动态包含【编译时形成两个文件，这样会慢一点？】--%>
<%--  <jsp:include page="/WEB-INF/common/css.jsp"/>--%>
  <style>
    .tree li {
      list-style-type: none;
      cursor:pointer;
    }
    .tree-closed {
      height : 40px;
    }
    .tree-expanded {
      height : auto;
    }
  </style>
  <title>主页面</title>
</head>

<body>

<%@include file="/WEB-INF/common/nav.jsp"%>
<div class="container-fluid">
  <div class="row">
    <%@include file="/WEB-INF/common/side.jsp"%>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <h1 class="page-header">控制面板</h1>
      <div class="row placeholders">
        <div class="col-xs-6 col-sm-3 placeholder">
          <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
          <h4>Label</h4>
          <span class="text-muted">Something else</span>
        </div>
        <div class="col-xs-6 col-sm-3 placeholder">
          <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
          <h4>Label</h4>
          <span class="text-muted">Something else</span>
        </div>
        <div class="col-xs-6 col-sm-3 placeholder">
          <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
          <h4>Label</h4>
          <span class="text-muted">Something else</span>
        </div>
        <div class="col-xs-6 col-sm-3 placeholder">
          <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
          <h4>Label</h4>
          <span class="text-muted">Something else</span>
        </div>
      </div>
    </div>
  </div>
</div>
<%@include file="/WEB-INF/common/js.jsp"%>
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

