<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common.jsp"%>
<title>image crop</title>

<link href="${staticPath }/plugins/bootstrap2/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${staticPath }/plugins/jQuery-Jcrop/css/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<script src="${staticPath }/plugins/jQuery-Jcrop/js/jquery.Jcrop.min.js"></script>
<script src="${staticPath }/plugins/jQuery-Jcrop/js/jquery.color.js"></script>
</head>
<body>

	<div class="row-fluid">

		<!-- start of main -->
		<section id="m-main" class="span10"> 
			<article class="m-widget"> 
			<header class="header">
			
			</header>
		<div class="content content-inner">

			<form method="post" action="avatar-save" class="form-horizontal">
				<input id="storeId" type="hidden" name="id" value="${id}"> 
				<input id="x1" type="hidden" name="x1" value=""> 
				<input id="y1" type="hidden" name="y1" value=""> 
				<input id="x2" type="hidden" name="x2" value="">
				<input id="y2" type="hidden" name="y2" value=""> 
				<input id="w" type="hidden" name="w" value=""> 
				<input id="h" type="hidden" name="h" value="">

				<div class="control-group">
					<label class="control-label" for="userBase_avatar">头像</label>
					<div class="controls" style="margin-top: 10px;">
						<div id="avatarImage">
							<img id="target" src="avatar-view?id=${id}" style="width:${w}px;height:${h}px;">
						</div>
					</div>
				</div>

				<div class="control-group">
					<div class="controls">
						<button id="submitButton" class="btn a-submit">确认</button>
					</div>
				</div>
			</form>
		</div>
		</article> </section>

	</div>

	<script type="text/javascript">
		$(function () {
			function cropOnSelect(c) {
				$('#x1').val(c.x);
				$('#y1').val(c.y);
				$('#x2').val(c.x2);
				$('#y2').val(c.y2);
				$('#w').val(c.w);
				$('#h').val(c.h);
			}
	
		    $('#target').Jcrop({
				aspectRatio: 1,
				onSelect: cropOnSelect
			},function(){
		      jcrop_api = this;
		    });
		    
		    jcrop_api.setSelect([0, 0, '${min}', '${min}']);
		});
	</script>
	
</body>
</html>