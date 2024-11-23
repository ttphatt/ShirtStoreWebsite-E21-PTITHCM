<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Complete Your Registration</title>
	<jsp:include page="pagehead.jsp"></jsp:include>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js"></script>
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
            <h1>Complete Your Registration</h1>
        </div>
    
        <div class="row justify-content-center">
            <div class="col-md-8">
                <form action="${pageContext.request.contextPath}/complete_registration" method="post" id="customerForm">
                    <input type="hidden" name="customerId" value="${customer.customerId}" />
                    
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${customer.email}" disabled>
                    </div>
                    
                    <div class="mb-3">
                        <label for="firstname" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstname" name="firstname" value="${customer.firstname}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="lastname" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastname" name="lastname" value="${customer.lastname}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phone" name="phone">
                    </div>
                    
                    <div class="mb-3">
                        <label for="address1" class="form-label">Address Line 1</label>
                        <input type="text" class="form-control" id="address1" name="address1">
                    </div>
                    
                    <div class="mb-3">
                        <label for="address2" class="form-label">Address Line 2</label>
                        <input type="text" class="form-control" id="address2" name="address2">
                    </div>
                    
                    <div class="mb-3">
                        <label for="city" class="form-label">City</label>
                        <input type="text" class="form-control" id="city" name="city">
                    </div>
                    
                    <div class="mb-3">
                        <label for="state" class="form-label">State</label>
                        <input type="text" class="form-control" id="state" name="state">
                    </div>
                    
                    <div class="mb-3">
                        <label for="zip" class="form-label">Zip Code</label>
                        <input type="text" class="form-control" id="zip" name="zip">
                    </div>
                    
                    <div class="mb-3">
                        <label for="country" class="form-label">Country</label>
                        <select class="form-select" id="country" name="country">
                            <c:forEach items="${mapCountries}" var="country">
                                <option value="${country.value}" <c:if test='${customer.country eq country.value}'>selected="selected"</c:if>>${country.key}</option>
                            </c:forEach>
                        </select>
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
                password: "required",
                confirmPassword:{
                    equalTo: "#password"
                },
                phone: "required",
                address1: "required",
                address2: "required",
                city: "required",
                state: "required",
                zip: "required",
                country: "required",
            },
            messages: {
                firstname: "Please enter first name",
                lastname: "Please enter last name",
                password: "Please enter password",
                confirmPassword:{
                    equalTo: "The password does not match"
                },
                phone: "Please enter a phone number",
                address1: "Please enter address 1",
                address2: "Please enter address 2",
                city: "Please enter city",
                state: "Please enter state",
                zip: "Please enter the zip code",
                country: "Please select the country",
            }
        });
    });
</script>
</html>
