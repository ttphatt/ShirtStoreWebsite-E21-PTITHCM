<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="pagehead.jsp"></jsp:include>	
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>Rate's Detail</h1>
	</div>
		
	<br>
		
	<div align="center">
		<form action="update_rate" method="post" id="rateForm">
		<input type="hidden" name="rateId" value="${rate.rateId}">
		<table cellpadding="10px">
			<tr>
				<td align="right">Shirts' name:</td>
				<td align="left"><b>${rate.shirt.shirtName}</b></td>
			</tr>
			
			<tr>
				<td align="right">Rating Stars:</td>
				<td align="left"><c:forEach begin="1" end="5" var="i">
                <c:choose>
                    <c:when test="${i <= rate.ratingStars}">
                        <i class="bi bi-star-fill"></i>
                    </c:when>
                    <c:otherwise>
                        <i class="bi bi-star"></i>
                    </c:otherwise>
                </c:choose>
            </c:forEach></td>
			</tr>
			
			<tr>
				<td align="right">Customer's full name:</td>
				<td align="left"><b>${rate.customer.fullName}</b></td>
			</tr>
			
			<tr>
				<td align="right">Rate's headline:</td>
				<td align="left">
					<input type="text" size="60" name="headline" value="${rate.headline}" required="required" minlength="5" maxlength="50" class="form-control" readonly>
				</td>
			</tr>
			
			<tr>
				<td align="right">Rate detail:</td>
				<td align="left">
					<textarea rows="5" cols="70" name="ratingDetail" required="required" minlength="1" maxlength="50" readonly>${rate.ratingDetail}</textarea>
				
				</td>
			</tr>
			
			
			
			
			<tr><td>&nbsp;</td></tr>
				
			<tr>
				<td colspan="2" align="center">
<%--					<button type = "submit" class="btn btn-outline-success">Save</button>--%>
					<button type="button" class="btn btn-outline-info" onclick="history.go(-1);">Cancel</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	
	<br><br><br><br>
	<jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#buttonCancel").click(function(){
			history.go(-1);
		});
	});
</script>
</html>