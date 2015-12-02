<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>修改密码</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-validation-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
	<link rel="stylesheet" href="${staticPath }/css/index.css">
	<link rel="stylesheet" href="${staticPath }/css/idea.css">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
		<li><a href="${adminCtx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${adminCtx}/sys/user/userModifyPwd">修改密码</a></li>
	</ul>
	<br/>
	<form id="inputForm" action="${adminCtx}/sys/user/userModifyPwd" method="post" class="form-horizontal">
		
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="18" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="18" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" minlength="3" maxlength="18" class="required" equalTo="#newPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<shiro:hasPermission name="sys:info:password">
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
		</shiro:hasPermission>
	</form>
</body>
</html>