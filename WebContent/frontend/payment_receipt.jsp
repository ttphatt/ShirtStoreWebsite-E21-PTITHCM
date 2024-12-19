<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>Payment Receipt</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
	<script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<link rel="stylesheet" type="text/css" href="css/search_button_template.css"/>
	<link rel="stylesheet" type="text/css" href="css/custom_row_template.css"/>
	<link rel="stylesheet" type="text/css" href="css/custom_border_template.css"/>
	<link rel="stylesheet" type="text/css" href="css/custom_background_template.css"/>
</head>
<body>
<jsp:directive.include file="header.jsp"/>

<div class="background-div-content">
	<div class="container">
		<div class="row justify-content-center mt-5 mb-5">
			<div class="row custom-row text-center" style="width: fit-content">
				<h2>You have made payment successfully. Thank you for choosing our product.</h2>
			</div>
		</div>

		<jsp:directive.include file="receipt.jsp"/>

		<br>
		<div class="row justify-content-center mb-5 mt-2">
			<div class="col text-center">
				<button type="button" class="btn custom-btn-details fs-5" onclick="javascript:showPrintReceiptPopup();" >Print your receipt</button>

			</div>
		</div>
	</div>
</div>

<jsp:directive.include file="footer.jsp"/>
</body>
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
</html>