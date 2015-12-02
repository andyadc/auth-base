<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common.jsp"%>
<title>jquery fileupload</title>

<link href="${staticPath }/plugins/bootstrap2/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${staticPath }/plugins/jQuery-File-Upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
<script src="${staticPath }/plugins/bootstrap2/js/bootstrap.min.js"></script>
<script src="${staticPath }/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${staticPath }/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="${staticPath }/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>

</head>
<body>

	<div class="row-fluid">

		<!-- start of main -->
		<section id="m-main" class="span10"> 
			<article class="m-widget">

		<div class="content content-inner">

			<form method="post" action="avatar-crop" class="form-horizontal">
				<input type="hidden" name="id" value="" id="cropId" />
				<div class="control-group">
					<label class="control-label" for="userBase_avatar">头像</label>
					<div class="controls">
						<div id="avatarImage" style="margin-bottom: 10px;">
							<c:if test="${not empty avatar}">
								<img src="avatar-view.do?id=${avatar.id}" style="width: 512px;">
							</c:if>
						</div>
						<div id="avatarButton" class="btn btn-success fileinput-button">
							<span>上传一个新图片</span> 
							<input type="file" name="avatar" class="fileupload" data-no-uniform="true" data-url="avatar-upload"
								data-form-data='{"id":"1","imgId":"avatarImage","btnId":"avatarButton","viewUrl":"avatar-view.do?id="}'>
						</div>
						<div id="progress" class="progress"
							style="width: 200px; margin-top: 5px; height: 20px; margin-bottom: 0px;">
							<div class="bar bar-success"></div>
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
		</article> 
	</section>
		<!-- end of main -->
	</div>

	<script type="text/javascript">
	
	$(function(){
		generateFileupload(1024 * 1024);
	});
	
	function generateFileupload(maxLimitedSize) {
	    $('.fileupload').fileupload({
	        dataType: 'json',
	        add: function (e, data) {
				var file = data.files[0];
				if (file.size > maxLimitedSize) {
					alert("图片过大");
				} else {
					data.submit();
				}
	        },
			submit: function (e, data) {
				var $this = $(this);
				data.jqXHR = $this.fileupload('send', data);
				$(this).parent('.btn').attr('disabled', true);
				$(this).parent('.btn').removeClass('btn-success');
				return false;
			},
			done: function (e, data) {
				var id = data.result.id;

				var imgId = data.formData.imgId;
				var btnId = data.formData.btnId;
				var viewUrl = data.formData.viewUrl + id + '&_=' + new Date().getTime();

				$("#" + imgId).html('<img src="' + viewUrl + '" style="width:512px;margin-top:20px;">');
				$('#' + btnId).attr('disabled', false);
				$('#' + btnId).addClass('btn-success');
				
				$("#cropId").val(id);
	        },
	        progressall: function (e, data) {
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            $('#progress .bar').css(
	                'width',
	                progress + '%'
	            ).html(progress + '%');
	        }
	    });
	}
	</script>
</body>
</html>