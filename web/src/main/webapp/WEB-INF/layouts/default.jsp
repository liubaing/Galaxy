<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
	<head>
        <title>Galaxy</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<link href="${ctx}/assets/css/bootstrap.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/assets/js/jquery.js" type="text/javascript"></script>
		<script src="${ctx}/assets/js/bootstrap.js" type="text/javascript"></script>
	</head>

	<body>
	</body>
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	  ga('create', 'UA-45776928-1', 'aso.io');
	  ga('send', 'pageview');
	  $(function(){
		 $('ul.sidebar-menu li.current').append('<div class="nav-after"></div>');
	  });
	</script>
</html>