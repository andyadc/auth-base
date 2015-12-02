<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<%@ include file="/WEB-INF/views/include/common.jspf" %>
<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
<%@ include file="/WEB-INF/views/include/jquery-treeTable-plugin.jspf" %>
<link rel="stylesheet" href="${staticPath }/css/index.css">
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}${fns:getAdminPath()}/sys/menu">菜单列表</a></li>
		<li><a href="${ctx}${fns:getAdminPath()}/sys/menu/add">菜单添加</a></li>
	</ul>
	<sys:message content="${message }"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
		<thead>
			<tr>
				<th>名称</th>
				<th>链接</th>
				<th style="text-align:center;">排序</th>
				<th>可见</th>
				<th>权限标识</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${menuList }" var="menu">
				<tr id="${menu.id}" pId="${menu.parentId ne '1' ? menu.parentId : '0'}">
					<td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i>
						<a href="${ctx}${fns:getAdminPath()}/sys/menu/edit?id=${menu.id}">${menu.name}</a>
					</td>
					<td title="${menu.href}">${menu.href }</td>
					<td style="text-align:center;">
							<input type="hidden" name="ids" value="${menu.id}"/>
							<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
					</td>
					<td>${menu.isShow eq '1' ? '显示':'隐藏'}</td>
					<td title="${menu.permission}">${menu.permission }</td>
					<td nowrap>
						<a href="${ctx}${fns:getAdminPath()}/sys/menu/edit?id=${menu.id}">修改</a>
						<a href="${ctx}${fns:getAdminPath()}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<a href="${ctx}${fns:getAdminPath()}/sys/menu/addSubMenu?parent.id=${menu.id}">添加下级菜单</a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript">
		$(function(){
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
	</script>

</body>
</html>