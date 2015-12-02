<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>在线用户管理</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:;">当前在线用户人数: ${sessionCount }</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<th>会话ID</th>
			<th>访问时间</th>
			<th>最后访问时间</th>
			<th>主机地址</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${sessions }" var="session">
			<tr>
				<td>${session.id }</td>
				<td><fmt:formatDate value="${session.startTimestamp }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${session.lastAccessTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${session.host }</td>
				<td>
					<a href="#">强制退出</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>