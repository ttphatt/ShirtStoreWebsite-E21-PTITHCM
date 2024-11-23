<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Reset Password</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .error {
            color: red;
            font-size: 0.875em; /* Adjust font size if needed */
        }
    </style>

    <style>
        body {
            padding-top: 70px;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
        }
        .form-heading {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<jsp:directive.include file="header.jsp" />

<div align="center">
    <h2>Reset Your Password</h2>
    <p>
        Please enter your login email, we'll send a new random password to your inbox:
    </p>

    <form id="resetForm" action="reset_password" method="post">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email" id="email" size="20"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="submit">Send me new password</button>
                </td>
            </tr>
        </table>
    </form>
</div>
<br><br><br><br><br>
<jsp:directive.include file="footer.jsp" />

<script type="text/javascript">

    $(document).ready(function() {
        $("#resetForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                }
            },

            messages: {
                email: {
                    required: "Please enter email",
                    email: "Please enter a valid email address"
                }
            }
        });

    });
</script>
</body>
</html>