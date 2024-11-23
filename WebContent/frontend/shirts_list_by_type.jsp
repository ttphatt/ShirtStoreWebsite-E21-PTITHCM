<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shirts of ${type.typeName} - PSK Shirt Store</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .shirt-card img {
            max-height: 240px;
            max-weidth: 250px;
            object-fit: cover;
        }
        .shirt-card {
            transition: transform 0.2s;
        }
        .shirt-card:hover {
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <h2 class="text-center">${type.typeName}</h2>
        </div>
        
        <div class="row justify-content-center text-center mb-5">
            <c:forEach var="shirt" items="${listShirts}">
                <div class="col-md-4 mb-4">
                    <div class="card shirt-card h-100">
                        <a href="view_shirt?id=${shirt.shirtId}">
                            <img src="${shirt.shirtImage}" width="250" height="240" >
                        </a>
                        <div class="card-body">
                            <h5 class="card-title">
                                <a href="view_shirt?id=${shirt.shirtId}" class="text-dark">
                                    <b>${shirt.shirtName}</b>
                                </a>
                            </h5>
                            <div>
                                <jsp:directive.include file="shirt_rating.jsp"/>
                            </div>
                            <p class="card-text">From: ${shirt.brand}</p>
                            <p class="card-text"><b>Price: $${shirt.shirtPrice}</b></p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <jsp:directive.include file="footer.jsp"/>
</body>
</html>
