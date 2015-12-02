<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色管理</title>
	<%@ include file="/WEB-INF/views/include/common.jspf" %>
	<%@ include file="/WEB-INF/views/include/bootstrap-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-validation-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/ztree-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-select2-plugin.jspf" %>
	<%@ include file="/WEB-INF/views/include/jquery-jbox-plugin.jspf" %>
	<link rel="stylesheet" href="${staticPath }/css/index.css">
	<link rel="stylesheet" href="${staticPath }/css/idea.css">

 	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${adminCtx}/sys/role/checkNameExist?oldName=" + encodeURIComponent("${role.name}")},
					role: {remote: "${adminCtx}/sys/role/checkRoleExist?oldRole=" + encodeURIComponent("${role.role}")}
				},
				messages: {
					name: {remote: "角色名已存在"},
					role: {remote: "英文名已存在"}
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					console.log(ids);
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

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">
						{id:"${menu.id}", pId:"${not empty menu.parentId ? menu.parentId : 0}", name:"${not empty menu.parentId ? menu.name : '权限列表'}"},
		            </c:forEach>
			];
			
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			
			// 默认展开全部节点
			tree.expandAll(true);
			
		});
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}${fns:getAdminPath()}/sys/role">角色列表</a></li>
		<li class="active"><a href="${ctx}${fns:getAdminPath()}/sys/role/add">角色添加</a></li>
	</ul>
	<br/>
	<form id="inputForm" action="save" method="post" class="form-horizontal">
		
		<sys:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label">角色名称:</label>
			<div class="controls">
				<input type="text" name="name" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">英文名称:</label>
			<div class="controls">
				<input type="text" name="role" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font>用户标识用户权限角色</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否可用</label>
			<div class="controls">
				<select name="usable">
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">角色授权:</label>
			<div class="controls">
				<input type="hidden" name="menuIds" id="menuIds"/>
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea name="remarks" rows="3" maxlength="200" class="input-xlarge"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
	
</body>
</html>