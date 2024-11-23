
	<div class="row justify-content-center">
		<h2>Your Payment Receipt</h2>
	</div>
	
	<br>
	<br>
	
	<div class="row justify-content-center">
		<h2>Seller's Information:</h2>
	</div>
	
	<br>
	
	<div class="row justify-content-center">
		<table border="1" cellpadding="10" class="table table-striped" style="width: 300px">
			<tr>
				<td><b>Emai:</b></td>
				<td>phkshirtstore@gmail.com</td>
			</tr>
			
			<tr>
				<td><b>Phone number:</b></td>
				<td>+84921890472</td>
			</tr>	
		</table>
	</div>
	
	<br>
	<br>
		
	<div class="row justify-content-center">
		<h2>Buyer's Information</h2>
	</div>
	
	<br>
	
	<div class="row justify-content-center">
		<table border="1" cellpadding="10" class="table table-striped" style="width: 400px">
			<tr>
				<td><b>First name:</b></td>
				<td>${payer.firstName}</td>
			</tr>
			
			<tr>
				<td><b>Last name:</b></td>
				<td>${payer.lastName}</td>
			</tr>
			
			<tr>
				<td><b>Email:</b></td>
				<td>${payer.email}</td>
			</tr>
		</table>
	</div>
	
	<br>
	<br>
	
	<div class="row justify-content-center">
		<h2>Order's detail</h2>
	</div>
	
	<br>
	
	<div class="row justify-content-center">
		<table border="1" cellpadding="10" class="table table-striped" style="width: 600px">
			<tr>
				<td><b>Order's ID: </b></td>
				<td>${orderId}</td>
			</tr>
			
			<tr>
				<td><b>Ordered by: </b></td>
				<td>${loggedCustomer.fullName}</td>
			</tr>
			
			<tr>
				<td><b>Transaction's description: </b></td>
				<td>${transaction.description}</td>
			</tr>
			
			<tr>
				<td colspan="2"><b>Items: </b></td>
			</tr>
			
			<tr>
				<td colspan="2">
				<div class="row justify-content-center">
				<table border="1" cellpadding="10" style="text-align: center" class="table">
					<thead class="thead-dark">
					<tr>
						<th class="align-middle justify-content-center text-center">No.</th>
						<th class="align-middle justify-content-center text-center">Name</th>
						<th class="align-middle justify-content-center text-center">Quantity</th>
						<th class="align-middle justify-content-center text-center">Price</th>
						<th class="align-middle justify-content-center text-center">Subtotal</th>
					</tr>
					</thead>
					
					<tbody>
					<c:forEach items="${transaction.itemList.items}" var="item" varStatus="status">
						<tr>
							<td class="align-middle justify-content-center text-center">${status.index + 1}</td>
							<td class="align-middle justify-content-center text-center">${item.name}</td>
							<td class="align-middle justify-content-center text-center">${item.quantity}</td>
							<td class="align-middle justify-content-center text-center"><fmt:formatNumber value="${item.price}" type="currency"/></td>
							<td class="align-middle justify-content-center text-center"><fmt:formatNumber value="${item.price * item.quantity}" type="currency"/></td>
						</tr>
					</c:forEach>
					
					<tr>
						<td colspan="5" align="right">
							<p><b>Subtotal: </b><fmt:formatNumber value="${transaction.amount.details.subtotal}" type="currency" /></p>
							<p><b>Tax: </b><fmt:formatNumber value="${transaction.amount.details.tax}" type="currency" /></p>
							<p><b>Shipping fee: </b><fmt:formatNumber value="${transaction.amount.details.shipping}" type="currency" /></p>
							<%--Discount Display Section--%>
							<c:if test="${shippingDiscount ne null}">
								<p><b>Shipping discount: </b><fmt:formatNumber value="-${shippingDiscount}" type="currency" /></p>
							</c:if>

							<c:if test="${shippingDiscount eq null}">
								<p><b>Shipping discount: </b><fmt:formatNumber value="0" type="currency" /></p>
							</c:if>

							<c:if test="${orderDiscount ne null}">
								<p><b>Order discount: </b><fmt:formatNumber value="-${orderDiscount}" type="currency" /></p>
							</c:if>

							<c:if test="${orderDiscount eq null}">
								<p><b>Order discount: </b><fmt:formatNumber value="0" type="currency" /></p>
							</c:if>
							<%------------------------------%>
							<p><b>Total price: </b><fmt:formatNumber value="${transaction.amount.total}" type="currency" /></p>
						</td>
					</tr>
					</tbody>
					
				</table>
				</div>
				</td>
			</tr>
		</table>
	</div>