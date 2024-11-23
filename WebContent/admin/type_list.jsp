<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
		<title>Managing Types</title>
		<jsp:include page="pagehead.jsp"></jsp:include>
		<jsp:include page="pageLoad.jsp"/>
		<link href="../css/temp.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>Types Management</h1>
		<hr width="70%">
		<h3><a href="type_form.jsp" class="btn btn-outline-primary btn-lg">Create new Type</a></h3>
	</div>
	
	<br>
	
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
		
	<br>
	
	<div align="center">
	<table  class="table table-bordered" style="width: 500px">
		<thead class="table-dark">
			<tr>
				<th class="align-middle justify-content-center text-center">Index</th>
				<th class="align-middle justify-content-center text-center">ID</th>
				<th class="align-middle justify-content-center text-center">Type Name</th>
				<th class="align-middle justify-content-center text-center">Actions</th>
			</tr>
			</thead>

			<tbody id="tableBody">
			<c:forEach var="type" items="${listType}" varStatus="status">
			<tr>
				<td class="align-middle justify-content-center text-center">${status.index + 1}</td>
				<td class="align-middle justify-content-center text-center">${type.typeId}</td>
				<td class="align-middle justify-content-center text-center">${type.typeName}</td>
				<td class="align-middle justify-content-center text-center">
					<a href="edit_type?id=${type.typeId}" class="btn btn-outline-primary">Edit</a>	&nbsp;
					<a href="javascript:void(0)" class="deleteLink btn btn-outline-primary" id="${type.typeId}">Delete</a> &nbsp;
				</td>
			</tr>
			</c:forEach>
			</tbody>
			
		</table>
	</div>

	<div align="center">
		<div class="pagination-wrapper">
			<a href="#" class="paginationButton is-medium-button w-button" id="prevPage" >Previous</a>
			<a href="#" class="paginationButton is-medium-button w-button" id="nextPage" >Next</a>
		</div>
	</div>

	<script>
		let curPage = 1
		let recordsPerPage = 5;
		let products = onload();
		document.getElementById("prevPage").addEventListener("click", prevPage);
		document.getElementById("nextPage").addEventListener("click", nextPage);
		changePage(1);

		function onload() {
			return document.getElementById("tableBody").getElementsByTagName("tr");
		}

		function prevPage() {
			if (curPage > 1) {
				curPage--;
				changePage(curPage);
			}
		}

		function nextPage() {
			if (curPage < numPages()) {
				curPage++;
				changePage(curPage);
			}
		}

		function changePage(page) {
			for (let i = 0; i < products.length; i++) {
				products[i].style.display = "none";
			}

			for (let i = (page - 1) * recordsPerPage; i < products.length && i < (page * recordsPerPage); i++) {
				products[i].style.display = "table-row";
			}
		}

		function numPages() {
			return Math.ceil(products.length / recordsPerPage);
		}
	</script>
	
	<br><br><br><br>
	<jsp:directive.include file="footer.jsp"/>
</body>
<script>
	$(document).ready(function(){
		$(".deleteLink").each(function(){
			$(this).on("click", function(){
				typeId = $(this).attr("id");
				if(confirm("Are you sure you want to delete the type with type's id: " + typeId + " ?")){
					window.location = "delete_type?id=" + typeId;
				}
			})
		});
	});
</script>
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