<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="common.jsp" %>
<title>umeditor</title>
	<!-- umeditor -->
	<link href="${staticPath }/plugins/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="${staticPath }/plugins/umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${staticPath }/plugins/umeditor/umeditor.min.js"></script>
    <script type="text/javascript" src="${staticPath }/plugins/umeditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
		<!--style给定宽度可以影响编辑器的最终宽度-->
		<script type="text/plain" id="myEditor" style="width:1000px;height:240px;">
    	<p>这里我可以写一些输入提示</p>
		</script>
		
	
	<script type="text/javascript">
		//实例化编辑器
	    var um = UM.getEditor('myEditor');
	</script>
	
</body>
</html>