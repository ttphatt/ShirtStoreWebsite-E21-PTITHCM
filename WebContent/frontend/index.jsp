<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>PHK Shirts Store</title>
    <jsp:include page="pagehead.jsp"></jsp:include>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <br><br>

    <!-- Main Container -->
    <div class="container">

        <!-- New Shoes Section -->
        <div class="row mb-5">
            <div class="col text-center">
                <h2 class="display-4"><b>New Shirts</b></h2>
            </div>
        </div>
        <div class="row justify-content-center mb-5">
            <c:forEach var="shirt" items="${listNewShirts}">
                <div class="col-md-4 mb-4">
                    <jsp:directive.include file="shirt_group.jsp"/>
                </div>
            </c:forEach>
        </div>

        <!-- Top-Selling Shirts Section -->
        <div class="row mb-5">
            <div class="col text-center">
                <h2 class="display-4"><b>Top-Selling Shirts</b></h2>
            </div>
        </div>
        <div class="row justify-content-center mb-5">
            <c:forEach var="shirt" items="${listBestSellingShirts}">
                <div class="col-md-4 mb-4">
                    <jsp:directive.include file="shirt_group.jsp"/>
                </div>
            </c:forEach>
        </div>

        <!-- Most-Favorite Shirts Section -->
        <div class="row mb-5">
            <div class="col text-center">
                <h2 class="display-4"><b>Most-Favorite Shirts</b></h2>
            </div>
        </div>
        <div class="row justify-content-center mb-5">
            <c:forEach var="shirt" items="${listMostFavoredShirts}">
                <div class="col-md-4 mb-4">
                    <jsp:directive.include file="shirt_group.jsp"/>
                </div>
            </c:forEach>
        </div>
    </div>

    <br><br><br><br>
    <jsp:directive.include file="footer.jsp"/>
</body>
</html>
