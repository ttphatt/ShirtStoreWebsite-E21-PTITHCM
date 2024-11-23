<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
		<title>Managing Orders</title>
		<jsp:include page="pagehead.jsp"></jsp:include>
		<jsp:include page="pageLoad.jsp"/>
		<link href="../css/temp.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>Orders Management</h1>
		<hr width="70%">	
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
	<table  class="table table-bordered" style="width: 1400px">
		<thead class="table-dark">
			<tr>
				<th class="align-middle justify-content-center text-center">Index</th>
				<th class="align-middle justify-content-center text-center">Order's ID</th>
				<th class="align-middle justify-content-center text-center">Ordered by</th>
				<th class="align-middle justify-content-center text-center">Shirts</th>
				<th class="align-middle justify-content-center text-center">Total</th>
				<th class="align-middle justify-content-center text-center">Payment Method</th>
				<th class="align-middle justify-content-center text-center">Status</th>
				<th class="align-middle justify-content-center text-center">Order Date</th>
				<th class="align-middle justify-content-center text-center">Actions</th>
			</tr>
		</thead>

			<tbody id="tableBody">
			<c:forEach var="order" items="${listOrder}" varStatus="status">
			<tr>
				<td class="align-middle justify-content-center text-center">${status.index + 1}</td>
				<td class="align-middle justify-content-center text-center">${order.orderId}</td>
				<td class="align-middle justify-content-center text-center">${order.customer.fullName}</td>
				<td class="align-middle justify-content-center text-center">${order.quantityOfShirts}</td>
				<td class="align-middle justify-content-center text-center">$${order.orderSum}</td>
				<td class="align-middle justify-content-center text-center">${order.payment}</td>
				<td class="align-middle justify-content-center text-center">
		            <c:choose>
		                <c:when test="${order.status == 'Processing'}">
		                    <span class="badge bg-warning text-dark">${order.status}</span>
		                </c:when>
		                <c:when test="${order.status == 'Shipping'}">
		                    <span class="badge bg-info text-dark">${order.status}</span>
		                </c:when>
		                <c:when test="${order.status == 'Delivered'}">
		                    <span class="badge bg-primary">${order.status}</span>
		                </c:when>
		                <c:when test="${order.status == 'Completed'}">
		                    <span class="badge bg-success">${order.status}</span>
		                </c:when>
		                <c:when test="${order.status == 'Cancelled'}">
		                    <span class="badge bg-danger">${order.status}</span>
		                </c:when>
		                <c:otherwise>
		                    <span class="badge bg-secondary">${order.status}</span>
		                </c:otherwise>
		            </c:choose>
        		</td>				
        		<td class="align-middle justify-content-center text-center">${order.orderDate}</td>
				
				<td class="align-middle justify-content-center text-center">
<%--					<a href="view_order?orderId=${order.orderId}" class="btn btn-outline-primary">Details</a>	&nbsp;--%>
					<a href="edit_order?orderId=${order.orderId}" class="btn btn-outline-primary">
						<c:choose>
							<c:when test="${order.status == 'Cancelled'}">Detail</c:when>
							<c:when test="${order.status == 'Completed'}">Detail</c:when>
							<c:when test="${order.status == 'Returned'}">Detail</c:when>
							<c:when test="${order.status == 'Delivered'}">Detail</c:when>
							<c:otherwise>Edit</c:otherwise>
						</c:choose>
					</a>	&nbsp;
<%--					<a href="javascript:void(0)" class="deleteLink btn btn-outline-primary" id="${order.orderId}">Delete</a> &nbsp;--%>
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
		let recordsPerPage = 10;
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
				orderId = $(this).attr("id");
				// if(confirm("Are you sure you want to delete the order with order's id: " + orderId + " ?")){
				// 	window.location = "delete_order?orderId=" + orderId;
				// }

				Swal.fire({
					title: "Are your sure?",
					text: "Do you want to"
				})
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