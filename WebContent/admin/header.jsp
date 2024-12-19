<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../css/search_button_template.css"/>
<link rel="stylesheet" type="text/css" href="../css/custom_background_template.css"/>
<div class="background-div-header">
	<div class="container">
		<div class="row">
			<div class="col">
				<table>
					<tr>
						<td>
							<a href="${pageContext.request.contextPath}/admin/">
								<img src="../images/Privacy_private_security-512.png" style="max-height: 100px; max-width: 100px;">
							</a>
						</td>

						<td>
							<h1><a href="${pageContext.request.contextPath}/admin/" class="text-dark" style="text-decoration: none">The Shirt Store Administration</a></h1>
						</td>
					</tr>
				</table>
			</div>

			<div class="col justify-content-end align-self-center text-end">
				<h3 class="mb-0 me-3">Hi there, ${sessionScope.userFullName} <a href="logout" class="btn custom-btn-cart fs-5 ms-2">Logout</a></h3>
<%--				<table>--%>
<%--					<tr>--%>
<%--						<td colspan="3" class="d-flex justify-content-end align-items-center text-end pe-3">--%>
<%--							<h3 class="mb-0 me-3">Hi there, ${sessionScope.userFullName}</h3>--%>
<%--							<a href="logout" class="btn btn-outline-dark">Logout</a>--%>
<%--						</td>--%>
<%--					</tr>--%>

<%--				</table>--%>
			</div>
		</div>

		<div class="container">
			<br><br>

			<div class="row justify-content-center text-center">
				<c:if test="${userRole != 'staff'}">
					<div class="col" >
						<a href = "list_users" class="btn custom-btn-admin-header">
							<img src="../images/profile.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
							<div class="mt-2">
								Staffs
							</div>

						</a>
					</div>
				</c:if>
				<div class="col">
					<a href = "list_type" class="btn custom-btn-admin-header">
						<img src="../images/app.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
						<div class="mt-2">
							Type
						</div>
					</a>
				</div>

				<div class="col">
					<a href = "list_shirts" class="btn custom-btn-admin-header">
						<img src="../images/shirt.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
						<div class="mt-2">
							Shirts
						</div>
					</a>
				</div>

				<div class="col">
					<a href = "list_customer" class="btn custom-btn-admin-header">
						<img src="../images/service.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
						<div class="mt-2">
							Customer
						</div>
					</a>
				</div>

				<div class="col">
					<a href = "list_rate" class="btn custom-btn-admin-header">
						<img src="../images/Rate.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
						<div class="mt-2">
							Rate
						</div>
					</a>
				</div>

				<div class="col">
					<a href = "list_order" class="btn custom-btn-admin-header">
						<img src="../images/shopping-cart.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
						<div class="mt-2">
							Order
						</div>
					</a>
				</div>

				<div class="col">
					<a href = "stock_check" class="btn custom-btn-admin-header">
						<img src="../images/warehouse.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
						<div class="mt-2">
							Warehouse
						</div>
					</a>
				</div>

				<c:if test="${userRole != 'staff'}">
					<div class="col">
						<a href = "list_promotion" class="btn custom-btn-admin-header">
							<img src="../images/promotion.png" id = "logoSize" style="max-width: 70px; max-height: 70px">
							<div class="mt-2">
								Promotion
							</div>
						</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>