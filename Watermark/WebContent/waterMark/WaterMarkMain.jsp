<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水印系统</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/waterMark.css" />
</head>
<body>
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#insert" data-toggle="tab"
			onclick="getinfo()"> 水印嵌入 </a></li>
		<li><a href="#extract" data-toggle="tab" onclick="getinfo()">水印提取</a></li>
		<li><a href="#upload" data-toggle="tab" onclick="getinfo()">图片与水印上传</a></li>
		<li><a href="#cut" data-toggle="tab" onclick="getinfo()">水印攻击</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">

		<div class="tab-pane fade in active" id="insert">

			<div class="showPic panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">选择底图</h3>
				</div>
				<div class="panel-body">
					<select id="pictureSelect" class="form-control">
					</select> <br>
					<center>
						<a href="" download="under.png" id="aID"> <img src=""
							alt="点击下载" id="pID">
						</a>
					</center>
					<br>
				</div>
			</div>
			<div class="showPic panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">选择水印图</h3>
				</div>
				<div class="panel-body">
					<select id="watermarkSelect" class="form-control">
						<%-- <c:forEach var="item" items="${watermarkInfo}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach> --%>
					</select> <br>
					<center>
						<a href="" download="water.png" id="AwaterMarkId" name="wateredA">
							<img src="" alt="点击下载" id="waterMarkId" name="wateredPic">
						</a>
					</center>
				</div>
			</div>

			<div class="showPic panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">添加描述</h3>
				</div>
				<div class="panel-body">
					<textarea rows="6" class="form-control" id="composeDescription"></textarea>
					<br> <br>
					<center>
						<button id="composeBtnDWT" class="btn btn-primary">DWT嵌入</button>
						<button id="composeBtnDCT" class="btn btn-primary">DCT嵌入</button>
						<button id="composeBtnFFT" class="btn btn-primary">FFT嵌入</button>
					</center>
				</div>
			</div>

			<div class="showPic panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">嵌入结果</h3>
				</div>
				<div class="panel-body" id="waterMarkResult">
					<a href="" download="afterWatermark.png" id="AfterWaterMarkAId">
						<img src="" id="AfterWaterMarkId">
					</a>
				</div>
			</div>
		</div>
		<!-- 图片的上传模块 1-->
		<div class="tab-pane fade" id="upload">

			<div class="showPic panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">上传底图</h3>
				</div>
				<div class="panel-body">
					<center>
						<button id="pictureUploadBtn" class="btn btn-primary"
							disabled="disabled">上传</button>
					</center>
					<form action="${pageContext.request.contextPath}/SavePicFromWeb"
						method="post" enctype="multipart/form-data" id="PicUploadFrom">

						<input type="file" name="img_upload" id="img_upload"
							accept=".jpg,.png,.gif,.jpeg,.bmp" /><br> <img src=""
							id="picUplaod"> <input type="hidden" id="imageWidth"
							name="imageWidth"> <input type="hidden" id="imageHeight"
							name="imageHeight">
					</form>
				</div>
				<div id="ShowUploadPicMessage"></div>
			</div>
			<!-- 图片的上传模块2 -->
			<div class="showPic panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">上传水印图</h3>
				</div>
				<div class="panel-body">
					<div id="ShowUploadWaterMarkMessage"></div>
					<center>
						<button id="WaterMarkUploadBtn" class="btn btn-primary"
							disabled="disabled">上传</button>
					</center>
					<form method="post" enctype="multipart/form-data"
						id="WaterMarkUploadFrom">
						<input type="file" name="WaterMark_upload" id="WaterMark_upload"
							accept=".jpg,.png,.gif,.jpeg,.bmp" /><br> <img src=""
							id="WaterMarkPicUplaod"> <input type="hidden"
							id="WaterMarkWidth" name="WaterMarkWidth"> <input
							type="hidden" id="WaterMarkHeight" name="WaterMarkHeight">
					</form>
				</div>
			</div>

		</div>
		<div class="tab-pane fade" id="extract">

			<div class=" showPic panel panel-primary ">
				<div class="panel-heading">
					<h3 class="panel-title">选择含水印图</h3>
				</div>
				<div class="panel-body">

					<select id="AterWawteredSelect" class="form-control" name="watered">
					</select> <br>
					<center>
						<a href="" download="water.png" id="AterWawteredAID"> <img
							src="" alt="点击下载" id="AterWawteredID">
						</a>
					</center>
				</div>

			</div>
			<div class="showPic panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">水印描述</h3>
				</div>
				<div class="panel-body">
					<center>

						<h2 id="WaterMarkedDescription"></h2>
					</center>
				</div>
			</div>

			<div class="showPic panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">提取水印</h3>
				</div>
				<div class="panel-body">
					<center>
						<button id="getwmark" class="btn btn-primary">提取水印</button>
					</center>
					<center>
						<a href="" download="wmark.png" id="wmarkAID"> <img src=""
							id="wmarkID">
						</a>
					</center>
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="cut">
			<div class=" showPic panel panel-primary ">
				<div class="panel-heading">
					<h3 class="panel-title">选择含水印图</h3>
				</div>
				<div class="panel-body">
					<select id="attackSelect" class="form-control">
					</select> <br>
					<center>
						<a href="" download="water.png" id="attackAID"> <img src=""
							alt="点击下载" id="attackID">
						</a>
					</center>
				</div>
			</div>
			<div class="showPic panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">水印攻击</h3>
				</div>
				<div class="panel-body">
					<center>
						<button id="attack_cut" class="btn btn-success btn-lg">剪切</button>
						&nbsp; &nbsp; 
						<button id="attack_whitenoise" class="btn btn-default btn-lg">白噪声</button>
						<br> <br>
						<button id="attack_mosaic" class="btn btn-warning btn-lg">马赛克</button>
						&nbsp;&nbsp;&nbsp;
						<button id="attack_gaussian" class="btn btn-primary btn-lg">高斯低频滤波</button>
					</center>

				</div>
			</div>
			<div class="showPic panel panel-danger">
				<div class="panel-heading">
					<h3 class="panel-title">攻击结果</h3>
				</div>
				<div class="panel-body">

					<center>
						<a href="" download="afterAttack.png" id="afterAttackAID"> <img
							src="" id="afterAttackID">
						</a>
					</center>
				</div>
			</div>
			<div class="showPic panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">提取水印</h3>
				</div>
				<div class="panel-body">
					<center>
						<button id="getAttackedwmark" class="btn btn-primary">提取水印</button>
					</center>
					<center>
						<input type="hidden" id="attackMode"> <a href=""
							download="wmarkAfterAttack.png" id="wmarkAfterAttackAID"> <img
							src="" id="wmarkAfterAttackID">
						</a>
					</center>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/WaterMark.js"></script>
</body>
</html>
