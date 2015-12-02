<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
	<link rel="stylesheet" href="${staticPath }/css/index.css">
	<link rel="stylesheet" href="${staticPath }/css/idea.css">
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${adminCtx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${adminCtx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${adminCtx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${adminCtx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${adminCtx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:add"><li><a href="${adminCtx}/sys/user/add">用户添加</a></li></shiro:hasPermission>
	</ul>
	
	<form id="searchForm" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		
		<%-- <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<%-- <li><label>归属公司：</label><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li> --%>
			<li>
				<label>登录名：</label><input type="text" name="account" value="${params.account }" maxlength="50" class="input-medium"/>
			</li>
			<li class="clearfix"></li>
			<%-- <li>
				<label>归属部门：</label>
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li> --%>
			<li>
				<label>姓&nbsp;&nbsp;&nbsp;名：</label><input type="text" name="name" value="${params.name }" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type	="submit" value="查询" onclick="return page();"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sort-column account">登录名</th>
				<th class="sort-column name">姓名</th>
				<th>联系方式</th>
				<th>邮箱</th>
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
				<c:forEach items="${page.list}" var="user">
					<tr>
						<td><a href="${adminCtx}/sys/user/edit?id=${user.id}">${user.account}</a></td>
						<td>${user.name}</td>
						<td>${user.phone}</td>
						<td>${user.email}</td>
						<td>
		    				<a href="${adminCtx}/sys/user/edit?id=${user.id}">修改</a>
							<a href="${adminCtx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	
	<c:set value="${page }" var="page"/>
	<c:set value="searchForm" var="searchForm"/>
	<%@include file="/WEB-INF/views/include/page.jspf" %>
	
		<%-- <div class="pagination">
			
			<c:if test="${page.totalPage eq 1 }">
				<ul>
					<li class="disabled"><a href="javascript:">&#171; 上一页</a></li>
					<li class="active"><a href="javascript:">1</a></li>
					<li class="disabled"><a href="javascript:">下一页 &#187;</a></li>
					<li class="disabled controls">
						<a>当前 ${page.pageNo } / ${page.totalPage } 页，共 ${page.totalRecord } 条</a>
					</li>
				</ul>
			</c:if>
			
			<c:if test="${page.totalPage ne 1 }">
				
				<ul>
					<c:if test="${page.pageNo eq 1 }">
						<li class="disabled"><a href="javascript:">&#171; 上一页</a></li>
					</c:if>
					<c:if test="${page.pageNo ne 1 }">
						<li><a href="javascript:" onclick="jump(${page.pageNo - 1 }, 10)" >&#171; 上一页</a></li>
					</c:if>
					
					<c:forEach items="${page.navigatePageNums }" var="nav">
						<li <c:if test="${page.pageNo eq nav }">class="active"</c:if> ><a href="javascript:" <c:if test="${page.pageNo ne nav }">onclick="jump(${nav }, 2)"</c:if> >${nav }</a></li>
					</c:forEach>
					
					<c:if test="${page.pageNo eq page.totalPage }">
						<li class="disabled"><a href="javascript:">下一页 &#187;</a></li>
					</c:if>
					<c:if test="${page.pageNo ne page.totalPage }">
						<li><a href="javascript:" onclick="jump(${page.pageNo + 1 }, 10)" >下一页 &#187;</a></li>
					</c:if>
					
					<li class="disabled controls">
						<a>当前 <input type="text" class="input-medium" style="width: 50px;" value="${page.pageNo }" onchange="go(this.value)" onkeyup="value=value.replace(/[^0-9]/g,'')" /> / ${page.totalPage } 页，共 ${page.totalRecord } 条</a>
					</li>
				</ul>
				
			</c:if>
			
			<div style="clear:both;"></div>
		</div>
		
		<script type="text/javascript">
			function jump(p, s){
				if(p) $("#pageNo").val(p);
				if(s) $("#pageSize").val(s);
				$("#searchForm").attr("action","${adminCtx}/sys/user/list");
				$("#searchForm").submit();
		    	return false;
			}
			
			function go(v){
				var e = '${page.totalPage}'
				e = parseInt(e);
				var n = parseInt(v);
				if(isNaN(n)) n = 1
				if(n < 1 ) n = 1
				if(n > e) n =1
				
				$("#pageNo").val(n);
				$("#searchForm").attr("action","${adminCtx}/sys/user/list");
				$("#searchForm").submit();
		    	return false;
			}
		</script> --%>
		
</body>
</html>