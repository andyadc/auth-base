<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kindupload</title>

<link href="${staticPath }/plugins/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css" />
<script src="${staticPath }/plugins/kindeditor/kindeditor.js"></script>
<script src="${staticPath }/plugins/kindeditor/lang/zh_CN.js"></script>
</head>
<body>
	<div class="upload">
		<input class="ke-input-text" type="text" id="url" value="" readonly="readonly" /> 
		<input type="button" id="uploadButton" value="上传" />
		<br/>
		
	</div>
</body>
<script>
	KindEditor.ready(function(K) {
		var uploadbutton = K.uploadbutton({
			button : K('#uploadButton')[0],
			fieldName : 'imgFile',
			url : '${ctx}/KindEditorUploadJsp/upload_json.jsp?dir=image',
			afterUpload : function(data) {
				if (data.error === 0) {
					var url = K.formatUrl(data.url, 'absolute');
					K('#url').val(url);
				} else {
					alert(data.message);
				}
			},
			afterError : function(str) {
				alert('自定义错误信息: ' + str);
			}
		});
		uploadbutton.fileBox.change(function(e) {
			uploadbutton.submit();
		});
		
	});
	
	
</script>
</html>