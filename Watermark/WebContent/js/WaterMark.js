var pathName = window.document.location.pathname;
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);
		var imgExt = new Array(".png",".jpg",".jpeg",".bmp",".gif");//图片文件的后缀名
		
		//获取后缀
		String.prototype.extension = function(){
		    var ext = null;
		    var name = this.toLowerCase();
		    var i = name.lastIndexOf(".");
		    if(i > -1){
		    var ext = name.substring(i);
		    }
		    return ext;
		}
		//判断Array中是否包含某个值
		Array.prototype.contain = function(obj){
		    for(var i=0; i<this.length; i++){
		        if(this[i] === obj)
		            return true;
		    }
		    return false;
		};
		
		function typeMatch(type, fielname){
		    var ext = filename.extension();
		    if(type.contain(ext)){        
		        return true;
		    }
		    return false;
		}

		/* 第一个上传 */
		$("#pictureUploadBtn").click(
				function() {
					var picform = new FormData(document
							.querySelector("#PicUploadFrom"));

					$.ajax({
								url : projectName + "/SavePicFromWeb",//servlet文件的名称  
								type : "POST",
								data : picform,
								async : false,
								processData : false, // 不处理数据  
								contentType : false, // 不设置内容类型  
								success : function(e) {
									$("#ShowUploadPicMessage").html(
											"<h2>succeed</h2>");
									$("#picUplaod").attr("src", "");
									$("#img_upload").val("");
									$("#pictureUploadBtn").attr('disabled',true);
								},
								error : function(e) {
									$("#ShowUploadPicMessage").html(
											"<h2>fail try again</h2>");
								}
							});
					$('#ShowUploadPicMessage').hide(3000, function() {
						$(this).remove()
					});
				});

		/* 第二个上传 */
		$("#WaterMarkUploadBtn").click(
				function() {
					var waterform = new FormData(document
							.querySelector("#WaterMarkUploadFrom"));
					$.ajax({
						url : projectName + "/SaveWaterPicFromWeb",//servlet文件的名称  
						type : "POST",
						data : waterform,
						async : false,
						processData : false, // 不处理数据  
						contentType : false, // 不设置内容类型  
						success : function() {
							$("#ShowUploadWaterMarkMessage").html(
									"<h2>succeed</h2>");

							$("#WaterMarkPicUplaod").attr("src", "");
							$("#WaterMark_upload").val("");
							$("#WaterMarkUploadBtn").attr('disabled',true); 

						},
						error : function() {
							$("#ShowUploadWaterMarkMessage").html(
									"<h2>fail try again</h2>");

						}
					});
					$('#ShowUploadWaterMarkMessage').hide(3000, function() {
						$(this).remove()
					});
				});

		function IN(mode) {
			$("#AfterWaterMarkId").attr("src", "");
			var description = $("#composeDescription").val();
			var pid = $("#pictureSelect").val();
			var wid = $("#watermarkSelect").val();
			$("#AfterWaterMarkId").attr(
					"src",
					projectName + "/getComposeInfo?pid=" + pid + "&wid=" + wid
							+ "&description=" + description + "&mode=" + mode);
		};

		$("#composeBtnDCT").click(function() {
			IN("dct");
		});

		$("#composeBtnDWT").click(function() {
			IN("dwt");
		});

		$("#composeBtnFFT").click(function() {
			IN("fft");

		});

		function ATTACK(mode) {
			
			$("#afterAttackID").attr("src", "");
			$("#wmarkAfterAttackID").attr("src","");
			var wpid = $("#attackSelect").val();
			console.log(wpid);
			$("#afterAttackID").attr(
					"src",
					projectName + "/WateredAttack?wpid=" + wpid + "&mode="
							+ mode);
		};

		$("#attack_whitenoise").click(function() {
			ATTACK("whitenoise");
			$("#attackMode").val("whitenoise");

		});
		$("#attack_gaussian").click(function() {
			ATTACK("gaussian");
			$("#attackMode").val("gaussian");

		});
		$("#attack_cut").click(function() {
			$("#attackMode").val("cut");
			ATTACK("cut");

		});
		
		$("#attack_mosaic").click(function() {
			ATTACK("mosaic");
			$("#attackMode").val("mosaic");

		});

		$("#getwmark").click(function() {
			var pid = $("#AterWawteredSelect").val();
			$.ajax({
				url : projectName + "/MakeUNWaterMark?pid=" + pid,//servlet文件的名称  
				type : "GET",
				/* dataType : "json", */
				success : function(data) {
					console.log(data);
					$("#wmarkID").attr("src", "/temp" + data);
				},
			});

		});

		$("#getAttackedwmark").click(
				function() {
					var pid = $("#attackSelect").val();
					var attackMode = $("#attackMode").val();
					$("#wmarkAfterAttackID").attr("src",
							projectName + "/getWaterFromAttacked?pid=" + pid+"&attackMode="+attackMode);
				});

		$(function() {
			$("#pictureSelect").on("change", showpic);
			$("#watermarkSelect").on("change", showwaterpic);
			
			$("#AterWawteredSelect").on("change", showAfterWateredpic)
			$("#attackSelect").on("change", showBeAttackpic)
		});

		function showpic() {
			var pid = $("#pictureSelect").val();
			$("#aID").attr(
					"href",
					projectName + "/AjaxShowDatabasePic?id=" + pid
							+ "&table=picture")
			$("#pID").attr(
					"src",
					projectName + "/AjaxShowDatabasePic?id=" + pid
							+ "&table=picture");
		};

		function showwaterpic() {
			wid = $("#watermarkSelect").val();
			$("#AwaterMarkId").attr(
					"href",
					projectName + "/AjaxShowDatabasePic?id=" + wid
							+ "&table=watermark")
			$("#waterMarkId").attr(
					"src",
					projectName + "/AjaxShowDatabasePic?id=" + wid
							+ "&table=watermark");
		};
		function showAfterWateredpic() {
			var pid = $("#AterWawteredSelect").val();
			$("#AterWawteredAID").attr(
					"href",
					projectName + "/AjaxShowDatabasePic?id=" + pid
							+ "&table=afterwatermark")
			$("#AterWawteredID").attr(
					"src",
					projectName + "/AjaxShowDatabasePic?id=" + pid
							+ "&table=afterwatermark");

			$("#wmarkID").attr("src", "");

			$.ajax({
						url : projectName + "/GetValueFromDatabase?table=afterwatermark&id="
								+ pid + "&value=message",//servlet文件的名称  
						type : "GET",
						/* dataType : "json", */
						success : function(data) {
							console.log(data);
							$("#WaterMarkedDescription").text(data);
						},
					});
		};

		function showBeAttackpic() {
			var pid = $("#attackSelect").val();
			$("#attackAID").attr(
					"href",
					projectName + "/AjaxShowDatabasePic?id=" + pid
							+ "&table=afterwatermark")
			$("#attackID").attr(
					"src",
					projectName + "/AjaxShowDatabasePic?id=" + pid
							+ "&table=afterwatermark");

			$("#afterAttackID").attr("src", "");
			$("#wmarkAfterAttackID").attr("src", "");

		};

		var _URL = window.URL || window.webkitURL;

		$("#WaterMark_upload").change(function() {
			$("#WaterMarkUploadBtn").attr('disabled',true);
			var file, img;
			var value = $(this).val();
			value = value.split("\\")[2];
			filename = value;
			console.log(filename);
			if(!typeMatch(imgExt, filename)){
		           alert("不是图片文件");
		           $("#WaterMarkPicUplaod").attr("src", "");
		           return;
		        }
			
			if ((file = this.files[0])) {
								img = new Image();
				img.onload = function() {
					
					$("#WaterMarkWidth").val(this.width);
					$("#WaterMarkHeight").val(this.height);
				}
				img.src = _URL.createObjectURL(file);
			}
			var objUrl = getObjectURL(this.files[0]);

			if (objUrl) {
				$("#WaterMarkPicUplaod").attr("src", objUrl);
			}
		    $("#WaterMarkUploadBtn").attr('disabled',false);  
		});

		$("#img_upload").change(function() {
			$("#pictureUploadBtn").attr('disabled',true);
			var file, img;
			var value = $(this).val();
            value = value.split("\\")[2];
            filename = value;
			if(!typeMatch(imgExt, filename)){
                alert("不是图片文件");
                $("#img_upload").attr("src", "");
                return;
             }
			if ((file = this.files[0])) {
				img = new Image();
				img.onload = function() {
					
					$("#imageWidth").val(this.width);
					$("#imageHeight").val(this.height);
				}
				img.src = _URL.createObjectURL(file);
			}
			var objUrl = getObjectURL(this.files[0]);
			console.log("objUrl = " + objUrl);
			if (objUrl) {
				$("#picUplaod").attr("src", objUrl);
			}
			$("#pictureUploadBtn").attr('disabled',false);
		});
		//建立一個可存取到該file的url
		function getObjectURL(file) {
			var url = null;
			if (window.createObjectURL != undefined) { // basic
				url = window.createObjectURL(file);
			} else if (window.URL != undefined) { // mozilla(firefox)
				url = window.URL.createObjectURL(file);
			} else if (window.webkitURL != undefined) { // webkit or chrome
				url = window.webkitURL.createObjectURL(file);
			}
			return url;
		};

		function getinfo() {
			$
					.ajax({
						url : projectName + "/GetPictureInfo?table=picture",//servlet文件的名称  
						type : "GET",
						dataType : "json",
						success : function(data) {
							$("#pictureSelect").empty();
							for ( var key in data) {
								$("#pictureSelect").append(
										"<option value='"+key+"'>" + data[key]
												+ "</option>");
							}
							$("#pictureSelect option:first").prop("selected",
									'selected');
							showpic();

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							// 状态码
							console.log(XMLHttpRequest.status);
							// 状态
							console.log(XMLHttpRequest.readyState);
							// 错误信息   
							console.log(textStatus);
						}
					});

			$.ajax({
						url : projectName + "/GetPictureInfo?table=watermark",//servlet文件的名称  
						type : "GET",
						dataType : "json",
						success : function(data) {
							$("#watermarkSelect").empty();
							for ( var key in data) {
								$("#watermarkSelect").append(
										"<option value='"+key+"'>" + data[key]
												+ "</option>");
							}
							$("#watermarkSelect option:first").prop("selected",
									'selected');
							showwaterpic();
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							// 状态码
							console.log(XMLHttpRequest.status);
							// 状态
							console.log(XMLHttpRequest.readyState);
							// 错误信息   
							console.log(textStatus);
						}
					});

			$.ajax({
						url : projectName + "/GetPictureInfo?table=afterwatermark",//servlet文件的名称  
						type : "GET",
						dataType : "json",
						success : function(data) {
							$("#AterWawteredSelect").empty();
							for ( var key in data) {
								$("#AterWawteredSelect").append(
										"<option value='"+key+"'>" + data[key]
												+ "</option>");
							}
							$("#AterWawteredSelect option:first").prop(
									"selected", 'selected');
							showAfterWateredpic();

							$("#attackSelect").empty();
							for ( var key in data) {
								$("#attackSelect").append(
										"<option value='"+key+"'>" + data[key]
												+ "</option>");
							}
							$("#attackSelect option:first").prop("selected",
									'selected');
							showBeAttackpic();
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							// 状态码
							console.log(XMLHttpRequest.status);
							// 状态
							console.log(XMLHttpRequest.readyState);
							// 错误信息   
							console.log(textStatus);
						}
					});

			$.ajax({
				url : projectName + "/CleanTemp",//删除本地的temp文件夹
				type : "GET"
			}); 
		};

		window.onload = getinfo();