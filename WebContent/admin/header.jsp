<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div align="left">
	<table>
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/admin/">
					<img src="../images/Privacy_private_security-512.png" style="max-height: 100px; max-width: 100px;">
				</a>
			</td>
			
			<td>
				<h1><a href="${pageContext.request.contextPath}/admin/" class="text-dark" style="text-decoration: none">PHK Store Administration</a></h1>
			</td>			
		</tr>
	</table>
</div>

<div align="right">
	<table>
<tr>
    <td colspan="3" class="d-flex justify-content-end align-items-center pe-3">
        <h3 class="mb-0 me-3">Hi there, ${sessionScope.userFullName}</h3>
        <a href="logout" class="btn btn-outline-dark">Logout</a>
    </td>
</tr>

	</table>
</div>	

<div class="container">
	<br><br>
		
	<div class="row justify-content-center text-center">
		<c:if test="${userRole != 'staff'}">
			<div class="col" >
				<a href = "list_users" class="btn btn-light">
					<img src="../images/profile.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Users
				</a>
			</div>
		</c:if>
		<div class="col">
			<a href = "list_type" class="btn btn-light">
				<img src="../images/app.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Type
			</a>
		</div>
		
		<div class="col">
			<a href = "list_shirts" class="btn btn-light">
				<img src="../images/shirt.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Shirts
			</a>
		</div>
		
		<div class="col">
			<a href = "list_customer" class="btn btn-light">
				<img src="../images/service.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Customer
			</a>
		</div>
		
		<div class="col">
			<a href = "list_rate" class="btn btn-light">
				<img src="../images/Rate.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Rate
			</a>
		</div>
		
		<div class="col">
			<a href = "list_order" class="btn btn-light">
				<img src="../images/shopping-cart.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Order
			</a>
		</div>

		<div class="col">
			<a href = "stock_check" class="btn btn-light">
				<img src="../images/warehouse.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Warehouse
			</a>
		</div>

		<c:if test="${userRole != 'staff'}">
			<div class="col">
				<a href = "list_promotion" class="btn btn-light">
					<img src="../images/promotion.png" id = "logoSize" style="max-width: 70px; max-height: 70px"><br>Promotion
				</a>
			</div>
		</c:if>
	</div>		
</div>