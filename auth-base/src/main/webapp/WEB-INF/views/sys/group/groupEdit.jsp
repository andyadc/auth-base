<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>用户组添加</title>
<%@ include file="/WEB-INF/views/include/common.jspf" %>
<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
<%@ include file="/WEB-INF/views/include/jquery-validation-plugin.jspf" %>
<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
<%@ include file="/WEB-INF/views/include/jquery-select2-plugin.jspf" %>
<link rel="stylesheet" href="${staticPath }/css/index.css">
<link rel="stylesheet" href="${staticPath }/css/idea.css">

	<script type="text/javascript">
		$(document).ready(function() {
			$("#name1").focus();
			$("#inputForm1").validate({
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
		<li><a href="${ctx}${fns:getAdminPath()}/sys/group/list">用户组列表</a></li>
		<li class="active">
			<a href="${ctx}${fns:getAdminPath()}/sys/group/edit?id=${group.id}">用户组修改</a>
		</li>
	</ul>
	<br/>
	<form id="inputForm" action="save" method="post" class="form-horizontal">
	<input type="hidden" name="id" value="${group.id }"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级用户组:</label>
			<div class="controls">
               <sys:treeselect id="group" name="parent.id" value="${group.parent.id}" labelName="parent.name" labelValue="${group.parent.name}"
					title="用户组" url="/sys/group/treeData" extId="${group.id}" cssClass="required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<input type="text" id="name" name="name" value="${group.name }" maxlength="50" class="input-xlarge" required/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<input type="text" name="sort" value="${group.sort }" maxlength="50" class="digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否可用:</label>
			<div class="controls">
				<span><input id="usable1" name="usable" class="required" type="radio" value="1" <c:if test="${group.usable eq '1' }">checked="checked"</c:if> /><label for="usable1">可用</label></span>
				<span><input id="usable2" name="usable" class="required" type="radio" value="0" <c:if test="${group.usable eq '0' }">checked="checked"</c:if> /><label for="usable2">不可用</label></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea name="remark" rows="3" maxlength="200" class="input-xlarge">${group.remark }</textarea>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	<form>
	
	<script type="text/javascript">
		$(function(){
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				}
			});
		})
	
	</script>
	
</body>
</html>