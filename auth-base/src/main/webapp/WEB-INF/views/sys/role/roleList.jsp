<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>角色管理</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}${fns:getAdminPath()}/sys/role">角色列表</a></li>
		<li><a href="${ctx}${fns:getAdminPath()}/sys/role/add">角色添加</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<th>角色名称</th>
			<th>英文名称</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${roleList }" var="role">
			<tr>
				<td><a href="${ctx}${fns:getAdminPath()}/sys/role/edit?id=${role.id}">${role.name}</a></td>
				<td><a href="${ctx}${fns:getAdminPath()}/sys/role/edit?id=${role.id}">${role.role}</a></td>
				<td>
					<a href="${ctx}${fns:getAdminPath()}/sys/role/assign?id=${role.id}">分配</a>
					<a href="${ctx}${fns:getAdminPath()}/sys/role/edit?id=${role.id}">修改</a>
					<a href="${ctx}${fns:getAdminPath()}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td>
				
			</tr>
		</c:forEach>
	</table>
</body>
</html>