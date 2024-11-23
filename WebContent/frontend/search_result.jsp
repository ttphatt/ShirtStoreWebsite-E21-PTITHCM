<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search results for ${keyword} - PHK Shirt Store</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <div class="container mt-5">
        <div class="row justify-content-center text-center">
            <c:if test="${fnc:length(result) == 0}">
                <h2>We are sorry but there is nothing for the keyword ${keyword}</h2>
            </c:if>
            
            <c:if test="${fnc:length(result) > 0}">
                <div>
                    <h2>Results for "${keyword}"</h2>
                    <br><br>
                    <div class="row justify-content-center mb-5 pd-5">
                        <c:forEach var="shirt" items="${result}">
                            <div class="col-md-4 mb-4">
                                <div class="card shirt-card h-100">
                                    <a href="view_shirt?id=${shirt.shirtId}">
                                        <img src="${shirt.shirtImage}" width="250" height="240" alt="${shirt.shirtName}">
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
            </c:if>
        </div>
    </div>
    
    <jsp:directive.include file="footer.jsp"/>
</body>
</html>
