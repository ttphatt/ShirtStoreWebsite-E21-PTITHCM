<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Admin Login</title>		
	<jsp:include page="pagehead.jsp"></jsp:include>

    <link rel="stylesheet" type="text/css" href="../css/custom_background_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/custom_row_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/custom_border_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/search_button_template.css">

	<style>
        .login-container {
            max-width: 500px;
            margin: 100px auto; /* Đưa form vào giữa trang */
            padding: 20px;
            border: none;
            /*border-radius: 8px;*/
            background-color: transparent; /* Màu nền cho form */
        }
        .logo {
            max-width: 200px;
            margin: 0 auto 20px; /* Căn giữa logo và thêm khoảng cách dưới */
            display: block;
        }
    </style>
</head>
<body>
    <div class="background-div-content">
        <div class="container login-container">
            <div class="row justify-content-center mb-5">
                <div class="col border custom-border p-4">
                    <div class="row justify-content-center mb-4">
                        <img src="${pageContext.request.contextPath}/images/shirt-logo-black.png" class="logo" style="max-width: 150px">
                        <h1 class="text-center">Admin Login Page</h1>
                    </div>

                    <div class="mb-4 text-center">
                        <h4>Welcome, hope you have a great day</h4>
                        <p>Please log in to access the admin features.</p>
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
                            <input type="password" name="password" id="password" class="form-control" required>
                        </div>

                        <div class="row justify-content-center text-center mt-5">
                            <button type="submit" class="btn custom-btn-cart btn-lg">Login</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="row justify-content-center text-center mt-5">
                <p class="text-center text-muted">©The copyright belongs to The Shirt Store | Provided by The Shirt Store</p>
            </div>

        </div>

<%--        <div class="container mt-5">--%>
<%--            <p class="text-center text-muted">©The copyright belongs to The Shirt Store | Provided by The Shirt Store</p>--%>
<%--        </div>--%>
    </div>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        function getMessageContent(messageId, event){
            fetch('../csvdata?id=' + messageId)
                .then(response => response.json())
                .then(data =>{
                    if(data.message){
                        Swal.fire(data.message);
                        event.preventDefault();
                    }
                    else{
                        Swal.fire("Message not found");
                        event.preventDefault();
                    }
                })
                .catch(error => console.error("Error: ", error));
        }

        $("#formLogin").submit(function (event){
            var email = $("#email").val();
            var password = $("#password").val();
            var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

            if(email.trim() === ""){
                // Swal.fire("Please fill out the email")
                getMessageContent("NOT_NULL_EMAIL", event);
            }
            else if(!emailRegex.test(email)){
                // Swal.fire("This email is not valid")
                getMessageContent("INVALID_EMAIL_ADDRESS", event);
                event.preventDefault();
            }
            else if (password.trim() === ""){
                // Swal.fire("Please fill out the password")
                getMessageContent("NOT_NULL_PASSWORD", event);
                event.preventDefault();
            }
        });


        $("#formLogin").validate({
            rules: {
                email: "required",
                password: "required"
            },
            messages: {
                email: "",
                password: ""
            }
        });
    });
</script>
</html>