<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
	<title>Payment Receipt</title>
	<script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div class="container">
		<div class="row justify-content-center">
			<h2>You have made payment successfully. Thank you for choosing our product.</h2>
		</div>
			
		
		<jsp:directive.include file="receipt.jsp"/>
		
		<br>
		<div class="row justify-content-center">
			<button type="button" class="btn btn-outline-info" onclick="javascript:showPrintReceiptPopup();" >Print your receipt</button>
		</div>	
	</div>
	
	<br><br><br><br>
	<jsp:directive.include file="footer.jsp"/>

	<script>
		function showPrintReceiptPopup(){
			var width = 600;
			var height = 250;
			var left = (screen.width - width) / 2;
			var top = (screen/height - height) / 2;
			
			window.open('frontend/print_receipt.jsp', '_blank',
						'width=' + width + ', height=' + height
						+ ', top=' + top + ', left=' + left)
		}
	</script>
</body>
</html>