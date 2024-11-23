<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="pagehead.jsp"></jsp:include>
	<jsp:include page="pageLoad.jsp"/>
	<style>
        .error {
            color: red;
            font-size: 0.875em; /* Adjust font size if needed */
        }
    </style>

	<title>
		<c:if test="${customer != null}">
			Edit Customer
		</c:if>
			
		<c:if test="${customer == null}">
			Create New Customer
		</c:if>
	
	</title>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>
			<c:if test="${customer != null}">
				Edit Customer
			</c:if>
			
			<c:if test="${customer == null}">
				Create New Customer
			</c:if>
		</h1>
	</div>
		
	<br>
	<br>
		
	<div align="center">
		<c:if test="${customer != null}">
			<form action="update_customer" method="post" id="customerForm">
			<input type="hidden" name="customerId" value="${customer.customerId}"/>
			
		</c:if>
		
		<c:if test="${customer == null}">
			<form action="create_customer" method="post" id="customerForm">
			
		</c:if>
		
			<jsp:directive.include file="../common/customer_form.jsp"/>
		
	</div>
	
	
	<br><br><br><br>
	<jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript" src="../js/customer-form.js">
</script>
<script>
	window.addEventListener("load", () => {
		const loader = document.querySelector(".loader_wrapper");

		setTimeout(() => {
			loader.classList.add("loader-hidden");

			loader.addEventListener("transitionend", () => {
				document.body.removeChild(loader);
			});
		}, 500);
	});
</script>
</html>