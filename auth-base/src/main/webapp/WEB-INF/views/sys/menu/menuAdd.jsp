<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单添加</title>
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
		<li><a href="${ctx}${fns:getAdminPath()}/sys/menu">菜单列表</a></li>
		<li class="active">
			<a href="${ctx}${fns:getAdminPath()}/sys/menu/add">菜单添加</a>
		</li>
	</ul>
	<br/>
	<form id="inputForm" action="save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级菜单:</label>
			<div class="controls">
               <sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
					title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<input type="text" id="name" name="name" maxlength="50" class="input-xlarge" required/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接:</label>
			<div class="controls">
				<input type="text" name="href" maxlength="2000" class="input-xxlarge"/>
				<span class="help-inline">点击菜单跳转的页面</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目标:</label>
			<div class="controls">
				<input type="text" name="target" maxlength="10" class="input-small"/>
				<span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图标:</label>
			<div class="controls">
				<sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<input type="text" name="sort" maxlength="50" class="digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可见:</label>
			<div class="controls">
				<span><input id="isShow1" name="isShow" class="required" type="radio" value="1" checked="checked"/><label for="isShow1">显示</label></span>
				<span><input id="isShow2" name="isShow" class="required" type="radio" value="0"/><label for="isShow2">隐藏</label></span>
				<span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜单类型:</label>
			<div class="controls">
				<select name="type">
					<option value="1">导航菜单</option>
					<option value="2">页面按钮</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权限标识:</label>
			<div class="controls">
				<input type="text" name="permission" class="input-xxlarge"/>
				<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
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