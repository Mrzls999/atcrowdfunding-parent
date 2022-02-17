<%--
  Created by IntelliJ IDEA.
  User: zls
  Date: 2022/2/16
  Time: 18:45:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-3 col-md-2 sidebar">
  <div class="tree">
    <ul style="padding-left:0;" class="list-group">

      <!--没有子节点的父节点-->
      <c:forEach items="${sessionScope.pMenus}" var="parent">

        <c:if test="${parent.childMenus.size()==0}">
          <li class="list-group-item tree-closed" >
            <a href="${applicationScope.appPath}/${parent.url}"><i class="${parent.icon}"></i> ${parent.name}</a>
          </li>
        </c:if>

        <!--有子节点的父节点-->
        <c:if test="${parent.childMenus.size()!=0}">

          <li class="list-group-item tree-closed">
            <span><i class="${parent.icon}"></i> ${parent.name} <span class="badge" style="float:right">${parent.childMenus.size()}</span></span>
            <ul style="margin-top:10px;display:none;">
              <c:forEach items="${parent.childMenus}" var="child">
                <li style="height:30px;">
                  <a href="${applicationScope.appPath}/${child.url}"><i class="${child.icon}"></i> ${child.name}</a>
                </li>
              </c:forEach>
            </ul>
          </li>
        </c:if>
      </c:forEach>
    </ul>
  </div>
</div>