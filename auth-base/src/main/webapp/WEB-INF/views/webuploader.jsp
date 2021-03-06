<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>webuploader</title>
<%@include file="common.jsp"%>
<!-- webuploader -->
<link href="${staticPath }/plugins/webuploader/webuploader.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${staticPath }/plugins/webuploader/webuploader.js"></script>
</head>
<body>
	<div id="uploader" class="wu-example">
		<!--用来存放文件信息-->
		<div id="thelist" class="uploader-list"></div>
		<div class="btns">
			<div id="picker">选择文件</div>
			<button id="ctlBtn" class="btn btn-default">开始上传</button>
		</div>
	</div>

	<script type="text/javascript">
	var uploader = WebUploader.create({

	    // swf文件路径
	    swf:  '../static/plugins/webuploader/Uploader.swf',

	    // 文件接收服务端。
	    server: 'http://webuploader.duapp.com/server/fileupload.php',

	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker',

	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false
	});
	
	// 当有文件被添加进队列的时候
	uploader.on( 'fileQueued', function( file ) {
	    $list.append( '<div id="' + file.id + '" class="item">' +
	        '<h4 class="info">' + file.name + '</h4>' +
	        '<p class="state">等待上传...</p>' +
	    '</div>' );
	});


	</script>
</body>
</html>