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
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <br><br><br><br>
    
    <div class="container">
        <div class="row justify-content-center">
            <h1>Profile Editing</h1>
        </div>
    
        <div class="row justify-content-center">
            <div class="col-md-8">
                <form action="update_profile" method="post" id="customerForm">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${loggedCustomer.email}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="firstname" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstname" name="firstname" value="${loggedCustomer.firstname}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="lastname" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastname" name="lastname" value="${loggedCustomer.lastname}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phone" name="phone" value="${loggedCustomer.phoneNumber}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="address1" class="form-label">Address Line 1</label>
                        <input type="text" class="form-control" id="address1" name="address1" value="${loggedCustomer.addressLine1}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="address2" class="form-label">Address Line 2</label>
                        <input type="text" class="form-control" id="address2" name="address2" value="${loggedCustomer.addressLine2}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="city" class="form-label">City</label>
                        <input type="text" class="form-control" id="city" name="city" value="${loggedCustomer.city}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="state" class="form-label">State</label>
                        <input type="text" class="form-control" id="state" name="state" value="${loggedCustomer.state}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="zip" class="form-label">Zip Code</label>
                        <input type="text" class="form-control" id="zip" name="zip" value="${loggedCustomer.zipcode}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="country" class="form-label">Country</label>
                        <select class="form-select" id="country" name="country">
                            <c:forEach items="${mapCountries}" var="country">
                                <option value="${country.value}" <c:if test="${loggedCustomer.country eq country.value}">selected="selected"</c:if>>${country.key}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <i><b class="text-danger">(Please leave the password field blank if you do not want to change your current password)</b></i>
                    </div>
                    
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password">
                    </div>
                    
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                    </div>
                    
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button type="submit" class="btn btn-outline-success me-md-2">Save</button>
                        <button id="buttonCancel" class="btn btn-outline-info">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    
    </div>
    
    <br><br><br><br>
    <jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $("#customerForm").validate({
            rules: {
                firstname: "required",
                lastname: "required",
                confirmPassword: {
                    equalTo: "#password"
                },
                phone: "required",
                address1: "required",
                city: "required",
                state: "required",
                zip: "required",
                country: "required",
            },
            messages: {
                firstname: "Please enter your first name",
                lastname: "Please enter your last name",
                confirmPassword: {
                    equalTo: "The passwords do not match"
                },
                phone: "Please enter your phone number",
                address1: "Please enter your address",
                city: "Please enter your city",
                state: "Please enter your state",
                zip: "Please enter your zip code",
                country: "Please select your country",
            }
        });
        
        $("#buttonCancel").click(function(){
            history.go(-1);
        });
    });
</script>
</html>
