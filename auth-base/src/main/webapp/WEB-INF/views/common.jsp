<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%
 	String contextPath = request.getContextPath();
 	String staticPath = contextPath + "/static";
 	String filesPath = contextPath + "/files";
%>

<c:set var="ctx" value="<%=contextPath %>"></c:set>
<c:set var="staticPath" value="<%=staticPath %>"></c:set>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Expires" CONTENT="0">

<script type="text/javascript" src="<%=staticPath %>/common/jquery/jquery-1.8.3.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lte IE 9]>
	<script type="text/javascript" src="<%=staticPath %>/common/js/html5shiv.min.js"></script>
	<script type="text/javascript" src="<%=staticPath %>/common/js/respond.min.js"></script>
<![endif]-->