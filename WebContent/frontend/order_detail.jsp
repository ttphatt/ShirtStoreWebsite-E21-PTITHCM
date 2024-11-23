<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Order Details</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<jsp:include page="pagehead.jsp"></jsp:include>
	
    <style>
        .order-details {
            margin-top: 30px;
        }
        .order-header {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <br><br><br><br>
    
    <div class="container order-details">
        <c:if test="${order == null}">
            <div class="row justify-content-center">
                <div class="alert alert-danger" role="alert">
                    Sorry, you are not authorized to view this order.
                </div>
            </div>
        </c:if>
        
        <c:if test="${order != null}">
            <div class="row justify-content-center">
                <h1 class="order-header">Your Order's ID: ${order.orderId}</h1>
            </div>
            <jsp:directive.include file="../common/common_order_detail.jsp"/>
        </c:if>
    </div>
    
    <br><br><br><br>
    <jsp:directive.include file="footer.jsp"/>
</body>
</html>
