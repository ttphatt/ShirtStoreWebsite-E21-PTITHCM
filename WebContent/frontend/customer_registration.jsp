<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up as a customer</title>
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
    <jsp:directive.include file="header.jsp"/>
    <br><br>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 form-container">
                <h1 class="text-center form-heading">Sign up as a customer</h1>
                <form action="register_customer" method="post" id="customerForm">
                    <jsp:directive.include file="../common/customer_form.jsp"/>
                </form>
            </div>
        </div>
    </div>
    
    <br><br><br><br>
    <jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
$(document).ready(function(){
    function getMessageContent(messageId, event){
        fetch('csvdata?id=' + messageId)
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
	$("#customerForm").validate({
		rules: {
			email:{
				required: true,
				email: true,
			},
			
			firstname:{
				required: true,
			},
			
			lastname:{
				required: true,
			},
			
			password:{
				required: true,
			},
			
			phone:{
				required: true,
				number: true,	
			},
			
			confirmPassword:{
				equalTo: "#password",
			},
			
			address1:{
				required: true,
			},
			
			address2:{
				required: true,
			},
			
			city:{
				required: true,
			},
			
			state:{
				required: true,
			},
			
			zip:{
				required: true,
			},			
		},
		
		messages: {
			email:{
				required: "Please enter your email",
				email: "Your email is not valid",
			},
			
			firstname:{
				required: "Please enter your first name",
			},
			
			lastname:{
				required: "Please enter your last name",
			},
			
			password:{
				required: "Please enter your password",
			},
			
			phone:{
				number: "Phone must contain only number",	
			},
			
			confirmPassword:{
				equalTo: "The password does not match with each other",
			},
			
			address1:{
				required: "Please enter your first address",
			},
			
			address2:{
				required: "Please enter your second address",
			},
			
			city:{
				required: "Please enter your city",
			},
			
			state:{
				required: "Please enter your state",
			},
			
			zip:{
				required: "Please enter your zip code",
			},	
		}
	});
	
	
	$("#buttonCancel").click(function(){
		history.go(-1);
	});
});


</script>
</html>
