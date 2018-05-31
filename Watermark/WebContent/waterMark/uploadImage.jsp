<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap/fileinput.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/fileinput.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/zh.js"></script>
<title>upload image</title>

</head>
<body>
	<form action="${pageContext.request.contextPath}/SavePicFromWeb"
		method="post" enctype="multipart/form-data">
		description: <input type="text" name="description" /><br> <input
			type="file" name="file" id="img_upload" /><br> <input
			type="hidden" id="imageWidth" name="imageWidth"> <input
			type="hidden" id="imageHeight" name="imageHeight"> <input
			type="submit" />
	</form>

	<script type="text/javascript">
		var _URL = window.URL || window.webkitURL;
		$("#img_upload").change(function(e) {
			var file, img;
			if ((file = this.files[0])) {
				img = new Image();
				img.onload = function() {
					alert(this.width + " " + this.height);
					$("#imageWidth").val(this.width);
					$("#imageHeight").val(this.height);

				};
				img.src = _URL.createObjectURL(file);
			}
		});
	</script>



	<div class="container-fluid">
		<form id="form" action="" method="post" enctype="multipart/form-data">
			<div class="row form-group">
				<div class="panel panel-primary">
					<div class="panel-heading" align="center">
						<label style="text-align: center; font-size: 18px;">文 件 上
							传</label>
					</div>
					<div class="panel-body">
						<div class="col-sm-6">
							<input id="input-id" name="file" multiple type="file"
								data-show-caption="true" class="file-loading">
						</div>

						<input type="hidden" id="imageWidth" name="imageWidth" value=400>
						<input type="hidden" id="imageHeight" name="imageHeight" value=400>

					</div>
				</div>
			</div>
		</form>
	</div>
	<script>
		var batch;
		$(function() {
			initFileInput("input-id");
		})

		function uploadExtraData() {
			return batch;
		};

		function initFileInput(ctrlName) {
			var control = $('#' + ctrlName);
			control.fileinput({
				language : 'zh', //设置语言  
				uploadUrl : "/Upload/getPic", //上传的地址  
				allowedFileExtensions : [ 'jpg', 'gif', 'png', 'doc', 'docx',
						'pdf', 'ppt', 'pptx', 'txt' ],//接收的文件后缀  
				maxFilesNum : 5,//上传最大的文件数量  
				//uploadExtraData:{"id": 1, "fileName":'123.mp3'},  
				uploadAsync : true, //默认异步上传  
				showUpload : true, //是否显示上传按钮  
				showRemove : true, //显示移除按钮  
				showPreview : true, //是否显示预览  
				showCaption : false,//是否显示标题  
				browseClass : "btn btn-primary", //按钮样式  
				dropZoneEnabled : true,//是否显示拖拽区域  
				//minImageWidth: 50, //图片的最小宽度  
				//minImageHeight: 50,//图片的最小高度  
				//maxImageWidth: 1000,//图片的最大宽度  
				//maxImageHeight: 1000,//图片的最大高度  
				maxFileSize : 0,//单位为kb，如果为0表示不限制文件大小  
				//minFileCount: 0,  
				//maxFileCount: 10, //表示允许同时上传的最大文件个数  
				enctype : 'multipart/form-data',
				validateInitialCount : true,
				previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
				msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

				uploadExtraData : function() {//向后台传递参数
					var data = {
						imageWidth : $("#imageWidth").val(),
						imageHeight : $("#imageHeight").val(),
					};
					return data;
				},

			})
		}
	</script>
</body>
</html>