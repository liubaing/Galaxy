<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/apps.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/apps.js"></script>
    <title>交互英语 | 手机版</title>
</head>

<body style="display: none;">
	<div class="header">
		<div class="apps_nav">
			<ul>
				<li><div id="apps_home">首页</div></li>
				<li><div id="apps_download">下载</div></li>
				<li><div id="apps_faq">常见问题</div></li>
				<li><div id="apps_contact">联系我们</div></li>
			</ul>
		</div>
    </div>
    <div class="body">
       	<div class="apps_home" id="apps_content">
        	<div class="middle_wrap">
            	<div class="intro_wrap">
                	<h4>畅享随时随地的学习!</h4>
                    <ul>
                    	<li><p>交互式课程</p></li>
                        <li><p>学习过程跟踪</p></li>
                        <li><p>课后练习自测</p></li>
                        <li><p>互动学习社区</p></li>
                    </ul>
                    <button id="apps_download"></button>
                    <div class="ios" id="apps_download">
                    	<a href="https://itunes.apple.com/us/app/tong-ji-wang-luo-xue-yuan/id543097895" title="立即下载 in App Store" target="_blank">iPhone版</a>
                    </div>
                    <div class="android">
                    	<a href="http://www.statsedu.com/upload/statsapp.apk" title="立即下载" target="_blank">Android版 </a>
                    </div>
                </div>
            </div>
            <div class="feature_wrap">
            	<table><tr>
                    <td>
                    	<div>
                        	<h3 class="hd_1">一次购买，多终端学习</h3>
                            <p>从统计网络学院网站购买的课程可在PC、手机和平板电脑上进行学习，手机上能支持下载后离线学习。各种终端的学习均可获得最终认可的继续教育学时。</p>
                        </div>
                    </td>
                    <td>
                    	<div>
                        	<h3 class="hd_2">利用碎片时间学习</h3>
                            <p>如今，最容易访问的学习工具应该是您随身携带的手机，下载我们的学习应用，您可充分利用等车、等人、约会等各类碎片时间，完成学习要求。手机上可提供更流畅的操作体验。</p>
                        </div>
                    </td>
                    <td>
                    	<div>
                        	<h3 class="hd_3">互动学习社区，增进交流</h3>
                            <p>通过观看其他学员的对课程内容的评论并参与讨论，您可以快速的获得其他学员的学习体会和他们对于课程内容的见解，这能促进您对内容的深入理解。</p>
                        </div>
                    </td>
                </tr></table>
            </div>
        </div>
       	<div class="apps_download div_invisible" id="apps_content">
        	<div class="middle_wrap">
                <div class="download_wrap">
                    <div class="ios_download">
                        <div class="title">iPhone版</div>
                        <a href="https://itunes.apple.com/us/app/tong-ji-wang-luo-xue-yuan/id543097895" title="立即下载 in App Store" target="_blank">
                        	<div class="dl"></div>
                        </a> 
                    </div>
                    <div class="line"></div>
                    <div class="android_download">
                    	<div class="title">Android版</div>
                    	<a href="http://www.statsedu.com/upload/statsapp.apk" class="dlandroid" title="立即下载" target="_blank">
                    		<div class="dl"></div>
                    	</a>
                    </div>
                </div>
            </div>
            <div class="state_wrap">
            	<div class="download_state">
                    <div class="state_title">下载说明</div>
                    <ol>
                        <li>统计网络学院For iPhone版支持iOS 4.0及以上版本，包括：iPhone 3GS/ 4/ 4S，iPod touch 第三代/ 第四代和iPad的所有机型。</li>
                        <li>统计网络学院For Android版支持v2.2及以上版本，使用屏幕尺寸大于3.5英寸，分辨率大于800*480的设备访问可获得更佳体验。</li>
                        <li>应用下载和安装后，使用您的网站用户名和密码登录，您所购买的课程会出现在“我的课程”中，根据说明进行操作可下载学习。</li>
                    </ol>
                </div>
            </div>
        </div>
       	<div class="apps_faq div_invisible" id="apps_content">
            <div class="faq_wrap">
                <div class="download_qns qns_field">
                    <dl>
                        <dt><span>1. </span>账号问题？ </dt>
                        <dd>统计网络学院的账号为通用账号，App、Web均使用同一个帐号。</dd>
                        <dt><span>2. </span>忘记用户名？</dt>
                        <dd>您可以通过Web访问 <a href="http://www.statsedu.com">http://www.statsedu.com</a> 首页点击“找回用户名”按钮，按提示操作找回用户名。</dd>
                        <dt><span>3. </span>如何找回密码？</dt>
                        <dd>您可以通过Web访问 <a href="http://www.statsedu.com">http://www.statsedu.com</a> 首页点击“忘记密码”按钮，按提示操作找回密码。</dd>
                        
                        <dt><span>4. </span>播放视频时为什么没有声音？</dt>
                        <dd>请将您设备的音量设置调置成非静音状态。</dd>
                        <dt><span>5. </span>仅用WIFI下载有什么用？</dt>
                        <dd>开启“仅用WIFI下载”，下载只会在使用WIFI网络时进行，可以减少来自运营商的流量。</dd>
                        <dt><span>6. </span>如何同步学习记录 </dt>
                        <dd>当您在手机学习完课程之后，通过点击设置里“同步学习记录”按钮，即可将学习记录同步到服务器上。</dd>
                        <dt><span>7. </span>同步学习记录有什么作用？ </dt>
                        <dd>可以将您在移动设备上的学习记录、笔记和书签与服务器保持一致，防止在您更换设备时数据丢失。</dd>
                        
                        <dt><span>8. </span>什么是术语？</dt>
                        <dd>各门学科中的专门用语。术语可以是词，也可以是词组，用来正确标记生产技术、科学、艺术、社会生活等各个专门领域中的事物、现象、特性、关系和过程。</dd>
                        <dt><span>9. </span>为什么知识检查题做过之后，在目录当中查看本单元还是显示没有完成？</dt>
                        <dd>因为知识检查的题必须全部回答正确，本单元才会显示已完成。</dd>
                         <dt><span>10. </span>我怎么样可以和其他用户交流呢？</dt>
                        <dd>对已有评论进行回复，并且您也可以对本课程进行评论，分享您的学习经验。</dd>
                        <dt><span>11. </span>请问一门课程学习完成后，还可以再次学习吗？</dt>
                        <dd>可以的，当您学习完一门课程之后，在“学习记录”当中会显示该门课程，您只需点击“回顾”按钮就可以重新学习了。</dd>
                         <dt><span> </span>这里找不到我想要的答案……</dt>
                        <dd>您可以访问 <a href="http://www.statsedu.com/staedu/welcome/service.do">http://www.statsedu.com</a> 进入客服中心，通过客服QQ联系我们，或者您直接和我们联系，拨打客服热线：400 610 7808。</dd>
                    </dl>
                </div>
            </div>
        </div>
       	<div class="apps_contact div_invisible" id="apps_content">
        	<div class="middle_wrap">
                <div class="contact_wrap">
                	<div class="contact_title">联系我们</div>
                    <address>
                        北京文华在线科技发展有限公司<br />
                        北京市朝阳区光华路4号东方梅地亚中心A座606室<br />
                        100026<br />
                        Tel: 010-65258968 <br />
                        Fax: 010-85597808<br />
                        Email: <a href="mailto:services@ulearning.cn">services@ulearning.cn</a><br />
                        <a href="http://www.ulearning.cn">http://www.ulearning.cn</a>
                    </address>
                </div>
                <!--<h5>如果你对我们的应用有话要说，请在下面留下您宝贵的意见。</h5> -->
            </div>
            <div class="feedback_wrap"> 
            	<!--
                <form action="">
                	<label for="email">您的邮箱：</label><input name="email" id="email" type="email" /><span>用于回复你反馈的意见和建议</span><br />
                    <label for="feedback">您的建议：</label><textarea name="feedback" id="feedback"></textarea><br />
                    <button></button>
                </form>
                -->
            </div>
        </div>
    </div>
    <div class="footer">
    	<div class="footer_wrap">
            <table><tr>
                <td><div><a href="http://www.ulearning.cn/about/about7.html" target="_blank">关于我们</a></div></td>
                <td><div id="apps_faq">常见问题</div></td>
                <td><div id="apps_contact">联系我们</div></td>
            </tr></table>
            <div>版权所有：文华在线</div>
            <div class="hotline">热线电话：400 610 7806</div>
        </div>
    </div>
</body>
</html>
