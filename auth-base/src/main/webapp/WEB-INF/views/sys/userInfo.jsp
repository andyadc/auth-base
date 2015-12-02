<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>个人信息</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-validation-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
	<link rel="stylesheet" href="${staticPath }/css/index.css">
	<link rel="stylesheet" href="${staticPath }/css/idea.css">
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				/* rules: {
					email : email
				}, */
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${adminCtx}/sys/user/info">个人信息</a></li>
		<li><a href="${adminCtx}/sys/user/userModifyPwd">修改密码</a></li>
	</ul>
	<br/>
	<form id="inputForm" action="${adminCtx}/sys/user/info" method="post" class="form-horizontal">
	
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			
		</div>
		
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
				<label class="lbl"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户:</label>
			<div class="controls">
				<input type="text" readonly="readonly" value="${user.account }" id="disabledInput" class="uneditable-input"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<input type="text" name="name" maxlength="50" value="${user.name }"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<input type="text" name="email" maxlength="50" class="email" value="${user.email }"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<input type="text" name="phone" maxlength="20" class="digits" value="${user.phone }"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">拥有角色:</label>
			<div class="controls">
				<c:if test="${not empty user.roleNames}">
					<c:forEach items="${user.roleNames }" var="role">
						<label class="lbl">${role }</label>
					</c:forEach>
				</c:if>
			</div>
		</div>
		
		<shiro:hasPermission name="sys:info:update">
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
		</shiro:hasPermission>
	</form>
</body>
</html>