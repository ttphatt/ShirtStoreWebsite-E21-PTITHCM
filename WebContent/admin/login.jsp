<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Admin Login</title>		
	<jsp:include page="pagehead.jsp"></jsp:include>
	
	<style>
        .login-container {
            max-width: 400px;
            margin: 100px auto; /* Đưa form vào giữa trang */
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: white; /* Màu nền cho form */
        }
        .logo {
            max-width: 200px;
            margin: 0 auto 20px; /* Căn giữa logo và thêm khoảng cách dưới */
            display: block;
        }
    </style>
</head>
<body class="bg-white">
    <div class="container login-container">
        <div class="row justify-content-center mb-4">
            <img src="${pageContext.request.contextPath}/images/sport-shoes-icon-vector-id121242.png" class="logo"> 
            <h1 class="text-center">Admin Login</h1>
        </div>

        <div class="mb-4 text-center">
            <h4>Welcome to PHK's Admin Panel</h4>
            <p>Please sign in to access the admin features.</p>
        </div>
        
        <c:if test="${message != null}">
            <div class="alert alert-danger text-center" role="alert">
                <h4>${message}</h4>
            </div>
        </c:if>

        <form id="formLogin" action="login" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" name="email" id="email" class="form-control" required minlength="5" maxlength="50">
            </div>
            
            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input type="password" name="password" id="password" class="form-control" required minlength="5" maxlength="50">
            </div>
        
            <div class="d-grid">
                <button type="submit" class="btn btn-dark btn-lg">Login</button>
            </div>
        </form>
    </div>

    <div class="container mt-5">
        <p class="text-center text-muted">©The copyright belongs to PHK Shirt Store | Provided by PHK</p>
    </div>
</body>
</html>