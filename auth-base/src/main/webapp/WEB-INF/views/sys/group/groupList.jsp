<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户组管理</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
	<link rel="stylesheet" href="${staticPath }/css/index.css">
	<link rel="stylesheet" href="${staticPath }/css/idea.css">
	
	<script type="text/javascript">
		
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${adminCtx}/sys/group/list");
			$("#searchForm").submit();
	    	return false;
	    }
		
	</script>
	
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${adminCtx}/sys/group/list">用户组列表</a></li>
		<li><a href="${adminCtx}/sys/group/add">用户组添加</a></li>
	</ul>
	
	<form id="searchForm" action="${ctx}/sys/group/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input id="parentId" name="parentId" type="hidden" value="${params.parentId}" />
		
		<ul class="ul-form">
			
			<li>
				<label>名称：</label><input type="text" name="name" value="${params.name }" maxlength="50" class="input-medium"/>
			</li>
			
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sort-column name">名称</th>
				<th>是否可用</th>
				<th>排序</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty page.list}">
				<tr>
					<td colspan="5" style="text-align: center;">暂无数据</td>
				</tr>
			</c:if>
			<c:if test="${not empty page.list}">
				<c:forEach items="${page.list}" var="group">
					<tr>
						<td><a href="${adminCtx}/sys/group/edit?id=${group.id}">${group.name}</a></td>
						<td>
							<c:if test="${group.usable eq '1' }">可用</c:if>
							<c:if test="${group.usable eq '0' }">不可用</c:if>
						</td>
						<td>${group.sort}</td>
						<td>${group.remark}</td>
						<td>
		    				<a href="${adminCtx}/sys/group/edit?id=${group.id}">修改</a>
							<a href="${adminCtx}/sys/group/delete?id=${group.id}" onclick="return confirmx('确认要删除该用户组吗？', this.href)">删除</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	
	<c:set value="${page }" var="page"/>
	<c:set value="searchForm" var="searchForm"/>
	<%@include file="/WEB-INF/views/include/page.jspf" %>
		
</body>
</html>