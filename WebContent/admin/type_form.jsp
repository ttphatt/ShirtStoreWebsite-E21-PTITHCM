<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
	<title>
		<c:if test="${type != null}">
			Type Editing
		</c:if>
				
		<c:if test="${type == null}">
			Create a new type
		</c:if>
	</title>

	<jsp:include page="pagehead.jsp"></jsp:include>
	<jsp:include page="pageLoad.jsp"/>
	
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>
			<c:if test="${type != null}">
				Edit Type
			</c:if>
			
			<c:if test="${type == null}">
				Create a New Type
			</c:if>
		</h1>
	</div>
	
	<br>
	
	<div align="center">
		<c:if test="${type != null}">
			<form action="update_type" method="post" id="listForm">
			<input type="hidden" name="typeId" value="${type.typeId}">
			
		</c:if>
		
		<c:if test="${type == null}">
			<form action="create_type" method="post" id="listForm">
			
		</c:if>
		
		<table>
			<tr>
				<td align="left">Type's name:&nbsp;</td>
				<td align="right"><input type="text" id="typeName" name="typeName" size="20" value="${type.typeName}" required="required" class="form-control"/></td>
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