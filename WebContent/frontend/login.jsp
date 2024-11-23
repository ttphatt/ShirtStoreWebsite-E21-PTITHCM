<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://js.hcaptcha.com/1/api.js" async defer></script>

    <style>
        .error {
            color: red;
            font-size: 0.875em; /* Adjust font size if needed */
        }
    </style>

    <style>
        .btn-custom {
            width: 100%;
            font-size: 1rem;
            padding: .5rem 1rem;
        }
    </style>
</head>
<body>
<jsp:directive.include file="header.jsp"/>
<br><br><br><br>

<div class="container">
    <div class="row justify-content-center text-center">
        <h2>Customer Login Page</h2>
    </div>

    <br>

    <c:if test="${message != null}">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="alert alert-danger">
                        ${message}
                </div>
            </div>
        </div>
    </c:if>

    <div class="row justify-content-center text-center">
        <div class="col-md-6">
            <form id="formLogin" action="login" method="post">
                <div class="mb-3" align="left">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" name="email" id="email" class="form-control">
                </div>

                <div class="mb-3" align="left">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" name="password" id="password" class="form-control">
                </div>
                <div class="mb-3" align="left">
                    <a href="reset_password">Forgot Password</a>
                </div>
                <br>

                <div class="h-captcha" data-sitekey="ca61f481-d935-460c-aa2f-61f113c2fa54"></div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-dark btn-custom">Login</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row justify-content-center text-center mt-3">
        <div class="col-md-6">
            <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/ShirtStoreWebsite/login&response_type=code&client_id=734161680008-sol644a9hji09b12fe6hiev3p4f7cg74.apps.googleusercontent.com&approval_prompt=force&state=provider=google" class="btn btn-lg btn-danger btn-custom" style="width: 100%;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                    <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
                </svg>
                <span class="ms-2 fs-6">Sign in with Google</span>
            </a>
        </div>
    </div>
    <div class="row justify-content-center text-center mt-3">
        <div class="col-md-6">
            <a href="https://www.facebook.com/v21.0/dialog/oauth?client_id=8898839156821098&redirect_uri=http://localhost:9999/ShirtStoreWebsite/login&state=provider=facebook&scope=email" class="btn btn-lg btn-primary btn-custom" style="width: 100%;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-facebook" viewBox="0 0 16 16">
                    <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z" />
                </svg>
                <span class="ms-2 fs-6">Sign in with Facebook</span>
            </a>
        </div>
    </div>
</div>

<br><br><br><br>
<jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $("#formLogin").validate({
            rules: {
                email: {
                    required: true,
                    email: true,
                },
                password: {
                    required: true,
                },
            },
            messages: {
                email: {
                    required: "Please enter your email",
                    email: "Please enter a valid email address",
                },
                password: {
                    required: "Please enter your password",
                },
            }
        });
    });
</script>
</html>
