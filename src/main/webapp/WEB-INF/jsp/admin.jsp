<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/apps.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/apps.js"></script>
    <script type="text/javascript" src="http://www.longmanenglish.cn:8080/swfupload.js"></script>
    <script type="text/javascript" src="http://www.longmanenglish.cn:8080/fileprogress.js"></script>
    <script type="text/javascript" src="http://www.longmanenglish.cn:8080/handlers.js"></script>
    <script type="text/javascript" src="http://www.longmanenglish.cn:8080/swfupload.queue.js"></script>
    <link rel="stylesheet" href="http://www.longmanenglish.cn:8080/swfupload.css">
    <title>交互英语 | 手机版</title>
</head>

<body>
	<div class="header">
		<div class="apps_nav">
			<ul>
			</ul>
		</div>
    </div>
    <div class="body">
       	<div class="admin_wrap">
       		<div class="tools_tab"><a herf="#" id="version" class="cur">版本升级</a><a herf="#" id="reset">重置缓存</a></div>
       		<div class="tools_tip"><span>请按照表单顺序填写，暂时无验证。</span></div>
            <div class="version"> 
                <form action="${pageContext.request.contextPath}/admin/version/update" method="post" onsubmit="checkForm()">
                	<label for="versionName">版本名称：</label><input name="versionName" id="versionName" type="text" /><span>例如：ie_Android_1.0</span><br />
                	<label for="versionCode">版本编码：</label><input name="versionCode" id="versionCode" type="number" /><span>整型：1</span><br />
                    <label for="Description">版本描述：</label><textarea name="Description" id="Description"></textarea><br />
                   	<div id="filePlace">
						<span id="spanButtonPlaceHolder"></span>
						<div id="upProgress"></div>
					</div>
					<input type="hidden" name="url" value=""/>
                    <button></button>
                </form>
            </div>
            <div class="reset hide">
                <form action="${pageContext.request.contextPath}/admin/resetCache" method="post" onsubmit="checkForm()">
					<label for="loginName">用户名：</label><input name="loginName" id="loginName" type="text" /><br />
                    <button></button>
                </form>
            </div>
        </div>
    </div>
    <script type="text/javascript">
	var rtx = "http://www.longmanenglish.cn:8080";
	var swfu,fileName = "ie_Android_1.0";
	$(function() {
		$("#versionName").blur(function(){
			fileName = $(this).val();
		});
		$(".tools_tab a").click(function(){
			var current = $(this).attr("id");
			$(this).addClass("cur").siblings().removeClass("cur");
			$("."+current).show().siblings("div:has(form)").hide();
		});
		var settings = {
			upload_url: rtx+"/object",
			post_params:{"path" : "apk","fileName" : fileName},
			file_size_limit : "10MB",//单个文件大小限制，0为无限
			file_types : "*.apk",//文件选择浏览框里显示的格式
			file_types_description : "apk安装包",//推荐具体
			file_upload_limit : 1,//一次上传多个文件,0为无限
			file_queue_limit : 1,//一次选择多个文件，0为无限
			custom_settings : {
				progressTarget : "upProgress"
			},
			debug : false
		};
		swfu = new SWFUpload(settings);
    });
    function callback(type, url, name, size){
	   if (type == "upload") {
			$("[name='url']").val(url);
		} else if (type == "delete") {
			$("[name='url']").val(url);
		}
    }
    function checkForm(){
    	var errNames = [];
		$("input").each(function(){
			if($.trim($(this).val()) == ""){
				errNames.push($(this));
			}
		});
		if (errNames.length) {
			$(errNames[0]).focus();
			return false;
		}else {
	        return true;
	    }
    }
	</script>
</body>
</html>
