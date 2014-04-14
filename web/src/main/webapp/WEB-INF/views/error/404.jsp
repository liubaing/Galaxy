<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/assets/css/bootstrap.css" type="text/css" rel="stylesheet" />
<title>Page Not Found</title>
<link>
</head>

<body>
	<div class="hero-unit center">
		<h1>
			Page Not Found <small><font face="Tahoma" color="red">Error
					404</font></small>
		</h1>
		<br>
		<p>
			The page you requested could not be found, either contact your
			webmaster or try again. Use your browsers <b>Back</b> button to
			navigate to the page you have prevously come from
		</p>
		<p>
			<b>Or you could just press this neat little button:</b>
		</p>
		<a href="${ctx }" class="btn btn-large btn-info"><i
			class="icon-home icon-white"></i> Take Me Home</a>
	</div>
</body>
</html>