<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Shirt Store Administration</title>
	<jsp:include page="pagehead.jsp"></jsp:include>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div class="container">
		<div class="row justify-content-center text-center">
			<c:if test="${message != null}">
			    <c:choose>
			        <c:when test="${message.contains('successfully')}">
			            <div align="center" class="alert alert-info" role="alert">
			                <h4>${message}</h4>
			            </div>
			        </c:when>
			        <c:otherwise>
			            <div align="center" class="alert alert-danger" role="alert">
			                <h4>${message}</h4>
			            </div>
			        </c:otherwise>
			    </c:choose>
			</c:if>
		</div>
	</div>
	
	<br><br><br><br>
	<jsp:directive.include file="footer.jsp"/>
</body>
</html>