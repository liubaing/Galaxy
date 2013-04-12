<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
    <style type="text/css">
    	header{
    		font-size:16px;
    		color: #2b9fff;
    		margin: 20px auto;
			width: 50%;
    	}
    	section{
    		border: 1px solid #ccc;
    	}
		section p{
    		padding:5px 0 0 5px;
    	}
		span{
			color: #2b9fff;
		}
    </style>
</head>
<body>
	<header>
		${ paymentResult.paymentStatusInfo }
	</header>
	<section>
		<p>课程: ${ paymentResult.orderName }</p>
		<p>总计: ￥ ${ paymentResult.orderPrice }</p>
		<p>状态: <span>${ paymentResult.paymentStatus }<span></p>
	</section>
</body>
</html>
