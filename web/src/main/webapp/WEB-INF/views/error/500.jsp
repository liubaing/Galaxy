<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/assets/css/bootstrap.css" type="text/css" rel="stylesheet" />
<title>Error</title>
<link>
</head>

<body>
	<div class="hero-unit center">
		<h1>
			Error <small><font face="Tahoma" color="red">Error
					500</font></small>
		</h1>
		<br>
		<p>
			<b>You could just press this neat little button:</b>
		</p>
		<a href="${ctx }" class="btn btn-large btn-info"><i
			class="icon-home icon-white"></i> Take Me Home</a>
	</div>
</body>
</html>