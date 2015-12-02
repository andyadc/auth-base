<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common.jsp"%>
<title>Image save</title>
</head>
<body>

	<div class="row-fluid">

		<!-- start of main -->
		<section id="m-main" class="span10"> <article
			class="m-widget"> <header class="header">
		
		</header>
		<div class="content content-inner">

			<div class="control-group">
				<label class="control-label" for="userBase_avatar">头像</label>
				<div class="controls">
					<div id="avatarImage">
						<img id="target" src="avatar-view.do?id=${id}">
					</div>
				</div>
			</div>

		</div>
		</article> </section>
		<!-- end of main -->
	</div>

</body>
</html>