<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Shirt Store Administration</title>
	<jsp:include page="pagehead.jsp"></jsp:include>
	<jsp:include page="pageLoad.jsp"/>

</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>
			<c:if test="${user != null}">
				Edit User
			</c:if>
			
			<c:if test="${user == null}">
				Create New User
			</c:if>
		</h1>
	</div>
		
	<br><br>	
		
	<div align="center">
		<c:if test="${user != null}">
			<form action="update_user" method="post" id="userForm">
			<input type="hidden" name="userId" value="${user.userId}">
			
		</c:if>
		
		<c:if test="${user == null}">
			<form action="create_user" method="post" id="userForm">
			
		</c:if>
		
		<table>
			<tr>
				<td align="left">Email&nbsp;</td>
				<td align="right"><input type="email" name="email" size="20" value="${user.email}" class="form-control" required="required"/></td>
			</tr>
			
			<tr>
				<td align="left">Full Name&nbsp;</td>
				<td align="right"><input type="text" name="fullname" size="20" value="${user.fullName}" class="form-control" required="required"/></td>
			</tr>
			
			<tr>
				<td align="left">Password&nbsp;</td>
				<td align="right"><input type="password" name="password" size="20" value="${user.password}" class="form-control" required="required" minlength="6" maxlength="100"/></td>
			</tr>
			
			<tr><td>&nbsp;</td></tr>
				
			<tr>
				<td colspan="2" align="center">
					<button type = "submit" class="btn btn-outline-success">Save</button>
					<button type="button" class="btn btn-outline-info" onclick="history.go(-1);">Cancel</button>
				</td>
			</tr>
		</table>
	</div>
	
	
	<br><br><br><br>
	<jsp:directive.include file="footer.jsp"/>
</body>
<script>
	window.addEventListener("load", () => {
		const loader = document.querySelector(".loader_wrapper");

		setTimeout(() => {
			loader.classList.add("loader-hidden");

			loader.addEventListener("transitionend", () => {
				document.body.removeChild(loader);
			});
		}, 500);
	});
</script>
</html>