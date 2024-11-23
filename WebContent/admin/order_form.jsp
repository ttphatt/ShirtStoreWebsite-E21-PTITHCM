<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
	<title>Managing Order Details</title>
	<script type="text/javascript" src="../js/jquery-3.7.1.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	<jsp:include page="pageLoad.jsp"/>
	<br><br><br><br>
	
	<div align="center">
		<h1>Details of Order with ID: ${order.orderId}</h1>
		<hr width="70%">	
	</div>
	
	<c:if test="${message != null}">
		<div align="center" style="color: red;">
			<h4>${message}</h4>
		</div>
	</c:if>
		
	<form action="update_order" method="post">
	<div class="container">
		<div class="row justify-content-center">
			<h2>Order Overview</h2>
		</div>
		
		<div class="row justify-content-center">
		<table border="1" cellpadding="10" style="text-align: left; width: 500px" class="table table-bordered">
			<tr>
				<td><b>Ordered by</b></td>
				<td>${order.customer.fullName}</td>
			</tr>
			
			<tr>
				<td><b>Order date</b></td>
				<td>${order.orderDate}</td>
			</tr>
			
			<tr>
				<td><b>Payment method</b></td>
				<td>
					${order.payment}
<%--					<select name="payment" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split">--%>
<%--						<option value="Cash On Delivery" <c:if test="${order.payment eq 'Cash On Delivery'}">selected='selected'</c:if>>Cash On Delivery</option>--%>
<%--						<option value="Paypal"<c:if test="${order.payment eq 'Paypal'}">selected='selected'</c:if>>Paypal or Credit card</option>--%>
<%--					</select>--%>
				</td>
			</tr>
			
			<tr>
				<td><b>Order status</b></td>
				<td>
					${order.status}
<%--					<select name="orderStatus" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split">--%>
<%--						<option value="Processing"<c:if test="${order.status eq 'Processing' }">selected='selected'</c:if>>Processing</option>--%>
<%--						<option value="Shipping"<c:if test="${order.status eq 'Shipping' }">selected='selected'</c:if>>Shipping</option>--%>
<%--						<option value="Delivered"<c:if test="${order.status eq 'Delivered' }">selected='selected'</c:if>>Delivered</option>--%>
<%--						<option value="Completed"<c:if test="${order.status eq 'Completed' }">selected='selected'</c:if>>Completed</option>--%>
<%--						<option value="Cancelled"<c:if test="${order.status eq 'Cancelled' }">selected='selected'</c:if>>Cancelled</option>--%>
<%--					</select>--%>
				</td>
			</tr>
		</table>
		</div>	
			
		<div class="row justify-content-center">	
		<table border="1" cellpadding="10" style="text-align: left; width: 700px" class="table table-bordered">	
			<tr>
				<td><b>Recipient' first name</b></td>
<%--				<td><input type="text" name="firstname" value="${order.firstname}" size="45" required="required" minlength="2" maxlength="50" class="form-control"></td>--%>
				<td>${order.firstname}</td>
			</tr>
			
			<tr>
				<td><b>Recipient' last name</b></td>
<%--				<td><input type="text" name="lastname" value="${order.lastname}" size="45" required="required" minlength="2" maxlength="50" class="form-control"></td>--%>
				<td>${order.lastname}</td>
			</tr>
			
			<tr>
				<td><b>Recipient's phone</b></td>
<%--				<td><input type="text" name="phone" value="${order.phone}" size="45" required="required" minlength="2" maxlength="10" class="form-control"></td>--%>
				<td>${order.phone}</td>
			</tr>
				
			<tr>
				<td><b>Address line 1</b></td>
<%--				<td><input type="text" name="addressLine1" value="${order.addressLine1}" size="45" required="required" minlength="5" maxlength="200" class="form-control"></td>--%>
				<td>${order.addressLine1}</td>
			</tr>
			
			<tr>
				<td><b>Address line 2</b></td>
<%--				<td><input type="text" name="addressLine2" value="${order.addressLine2}" size="45" required="required" minlength="5" maxlength="200" class="form-control"></td>--%>
				<td>${order.addressLine2}</td>
			</tr>
			
			<tr>
				<td><b>City</b></td>
<%--				<td><input type="text" name="city" value="${order.city}" size="45" required="required" minlength="5" maxlength="50" class="form-control"></td>--%>
				<td>${order.city}</td>
			</tr>
			
			<tr>
				<td><b>State</b></td>
<%--				<td><input type="text" name="state" value="${order.state}" size="45" required="required" minlength="5" maxlength="50" class="form-control"></td>--%>
				<td>${order.state}</td>
			</tr>
			
			<tr>
				<td><b>Country</b></td>
				<td>
<%--					<select name="country" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split">--%>
<%--						<c:forEach items="${mapCountries}" var="country">--%>
<%--							<option value="${country.value}" <c:if test='${order.country eq country.value}'>selected='selected'</c:if> >${country.key}</option>--%>
<%--						</c:forEach>--%>
<%--					</select>--%>
					${order.countryName}
				</td>
			</tr>
			
			<tr>
				<td><b>Zip code</b></td>
<%--				<td><input type="text" name="zipcode" value="${order.zipcode}" size="45" required="required" minlength="5" maxlength="30" class="form-control"></td>--%>
				<td>${order.zipcode}</td>
			</tr>
		</table>
		</div>
	</div>
	
	<div align="center">
		<h2>Ordered Shirts</h2>
		<table border="1" cellpadding="10" style="text-align: center; width: 1200px" class="table">
			<thead class="thead-dark">
			<tr>
				<th class="align-middle justify-content-center text-center">Index</th>
				<th colspan="2" class="align-middle justify-content-center text-center">Shirts' name</th>
				<th class="align-middle justify-content-center text-center">Brand</th>
				<th class="align-middle justify-content-center text-center">Price</th>
				<th class="align-middle justify-content-center text-center">Quantity</th>
				<th class="align-middle justify-content-center text-center">Sub total</th>
<%--				<th class="align-middle justify-content-center text-center">Action</th>--%>
			</tr>
			</thead>
			
			<tbody>
			<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
			<tr>
				<td class="align-middle justify-content-center text-center">${status.index + 1}</td>
				<td class="align-middle justify-content-center text-center">
					<img src="${orderDetail.shirt.shirtImage}" width="150" height="140"/>
				</td>
				
				<td class="align-middle justify-content-center text-center">
					${orderDetail.shirt.shirtName}
				</td>
				<td class="align-middle justify-content-center text-center">${orderDetail.shirt.brand}</td>
				<td class="align-middle justify-content-center text-center">
					<input type="hidden" name="shirtPrice" value="${orderDetail.shirt.shirtPrice}"/>
					<fmt:formatNumber type="currency" value="${orderDetail.shirt.shirtPrice}"/>
				</td>
				<td class="align-middle justify-content-center text-center">
					<input type="hidden" name="shirtId" value="${orderDetail.shirt.shirtId}"/>
						${orderDetail.quantity}
						<%--					<input type="number" name="quantity${status.index + 1}" value="${orderDetail.quantity}" size="3" step="1" min="1" required="required" class="form-control">--%>
				</td>
				<td class="align-middle justify-content-center text-center"><fmt:formatNumber type="currency" value="${orderDetail.subTotal}"/></td>
<%--				<td class="align-middle justify-content-center text-center"><a href="remove_shirt_from_order?shirtId=${orderDetail.shirt.shirtId}" class="btn btn-outline-primary">Remove</a></td>--%>
			</tr>
			</c:forEach>
			
			<tr >
				<td colspan="8" align="right">
					<p>Subtotal: <fmt:formatNumber type="currency" value="${order.subtotal}"/></p>
<%--					<p>Tax: <input type="number" name="tax" size="5" value="${order.tax}" step="0.1" min="0.0" style="text-align: center; width: 120px" required class="form-control d-inline"></p>--%>
<%--	                <p>Shipping fee: <input type="number" name="shippingFee" size="5" value="${order.shippingFee}" step="0.1" min="0.0" style="text-align: center; width: 120px" required class="form-control d-inline"></p>--%>
					<p>Tax: <fmt:formatNumber type="currency" value="${order.tax}"/></p>
					<p>Shipping fee: <fmt:formatNumber type="currency" value="${order.shippingFee}"/></p>

					<%--Promotion Display Section--%>
					<c:if test="${fn:length(order.orderPromotions) > 0}">
						<c:forEach items="${order.orderPromotions}" var="orderPromotion">
							<c:choose>
								<c:when test="${orderPromotion.promotion.type eq 'Shipping Discount'}">
									<p><b>Shipping Discount: </b><fmt:formatNumber type="currency" value="-${orderPromotion.discountPrice}"/></p>
									<c:if test="${fn:length(order.orderPromotions) == 1}">
										<p><b>Order Discount: </b><fmt:formatNumber type="currency" value="0"/></p>
									</c:if>
								</c:when>

								<c:otherwise>
									<c:if test="${fn:length(order.orderPromotions) == 1}">
										<p><b>Shipping Discount: </b><fmt:formatNumber type="currency" value="0"/></p>
									</c:if>
									<p><b>Order Discount: </b><fmt:formatNumber type="currency" value="-${orderPromotion.discountPrice}"/></p>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>

					<c:if test="${fn:length(order.orderPromotions) == 0}">
						<p><b>Shipping Discount: </b><fmt:formatNumber type="currency" value="0"/></p>
						<p><b>Order Discount: </b><fmt:formatNumber type="currency" value="0"/></p>
					</c:if>

					<%--Order sum display section--%>
					<p>Total: <fmt:formatNumber type="currency" value="${order.orderSum}"/></p>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	
	<br><br>
	<div align="center">
<%--		<a href="javascript:showAddShirtPopup()" class="btn btn-outline-primary"><b>Add shirts</b></a>--%>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--		<button type="submit" class="btn btn-outline-primary">Save</button>--%>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;--%>
		<button type="button" class="btn btn-outline-primary" onclick="javascript:window.location.href='list_order';">Back</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${order.status == 'Processing'}">
			<a href="shipping_order?orderId=${order.orderId}" class="btn btn-outline-primary">Shipping</a>
		</c:if>
		<c:if test="${order.status == 'Shipping'}">
			<a href="delivered_order?orderId=${order.orderId}" class="btn btn-outline-primary">Delivered</a>
		</c:if>
	</div>
	</form>
	
	
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
<script>
	// function showAddShirtPopup(){
	// 	var width = 700;
	// 	var height = 300;
	// 	var left = (screen.width - width) / 2;
	// 	var top = (screen.height - height) / 2;
	//
	// 	window.open('add_shirt_form', '_blank', 'width=' + width + ', height=' + height + ', top=' + top + ', left=' + left);
	// }
</script>

</html>