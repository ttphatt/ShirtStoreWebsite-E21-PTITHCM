<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Check out</title>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
<%--    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<style>
        .error {
            color: red;
            font-size: 0.875em; /* Adjust font size if needed */
        }
    </style>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <br><br><br><br>

    <div class="container">
        <c:if test="${message != null}">
            <div class="row justify-content-center">
                <div class="alert alert-danger" role="alert">
                    ${message}
                </div>
            </div>
        </c:if>
        
        <c:set var="cart" value="${sessionScope['cart']}" />
        
        <c:if test="${cart.totalItems == 0}">
            <div class="row justify-content-center">
                <h2>There is nothing in your cart</h2>
            </div>
        </c:if>
        
        <c:if test="${cart.totalItems > 0}">
            <div class="row justify-content-center">
                <h2>Review your order details <a href="view_cart" class="btn btn-outline-primary btn-lg">Edit</a></h2>
            </div>
            
            <br>
            
            <div class="row justify-content-center">
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th class="text-center">No</th>
                            <th colspan="2" class="text-center">Product</th>
                            <th class="text-center">Size</th>
                            <th class="text-center">Brand</th>
                            <th class="text-center">Price</th>
                            <th class="text-center">Quantity</th>
                            <th class="text-center">Subtotal</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        <c:forEach var="item" items="${cart.carts}" varStatus="status">
                            <tr align="center">
                                <td class="text-center">${status.index + 1}</td>
                                <td class="text-center">
                                    <img src="${item.shirt.shirtImage}" class="img-fluid" style="width: 150px; height: auto;" />
                                </td>
                                
                                <td class="text-center">
                                    ${item.shirt.shirtName}
                                </td>

                                <td class="text-center">
                                    ${item.size}
                                </td>
                                
                                <td class="text-center">${item.shirt.brand}</td>
                                
                                <td class="text-center"><fmt:formatNumber value="${item.shirt.shirtPrice}" type="currency"></fmt:formatNumber></td>
                                
                                <td class="text-center">
                                    <input type="text" name="quantity${status.index + 1}" value="${item.quantity}" class="form-control text-center" size="5" readonly="readonly"/>
                                </td>
                                <td class="text-center"><fmt:formatNumber value="${item.quantity * item.shirt.shirtPrice}" type="currency"></fmt:formatNumber></td>
                            </tr>
                        </c:forEach>
                        
                        <tr align="right">
                            <td colspan="8">
                                <p><b>Number of shirts:</b> ${cart.totalQuantity}</p>
                                <p><b>Subtotal: </b><fmt:formatNumber value="${cart.totalAmount}" type="currency" /></p>
                                <p><b>Tax: </b><fmt:formatNumber value="${tax}" type="currency" /></p>
                                <p><b>Shipping fee: </b><fmt:formatNumber value="${shippingFee}" type="currency" /></p>

                                    <%--Discount Display Section--%>
                                <p><b>Shipping Discount: </b>
                                    <span id="shippingDiscountPrice"><fmt:formatNumber value="0" type="currency"/></span>
                                </p>

                                <p><b>Order's Discount: </b>
                                    <span id="orderDiscountPrice"><fmt:formatNumber value="0" type="currency"/></span>
                                </p>

                                <%--Order sum or total price display section--%>
                                <p><b>Total price: </b>
                                    <span id="spanTotalPrice"><fmt:formatNumber value="${totalPrice}" type="currency" /></span>
                                </p>

                                <form id="orderPromoForm">
                                    <div class="input-group w-50">
                                            <%--                                        <input type="hidden" id="totalPrice" value="${totalPrice}">--%>
                                            <%--                                        <input type="hidden" id="subTotal" value="${cart.totalAmount}">--%>
                                        <c:if test="${orderPromotionId eq null}">
                                            <input id="orderPromotionId" type="text" class="form-control" placeholder="Enter your order promotion code here">
                                            <button id="orderPromoSubmit" type="submit" class="btn btn-outline-dark">Enter</button>

                                            <!--Order Promotion Dropdown list-->
                                            <div class="btn-group">
                                                <button id="dropdownOrderPromotions" type="button" class="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    Show promotion code
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <c:forEach items="${orderPromotions}" var="orderPromotion">
                                                        <li><a class="orderPromoId dropdown-item" id="${orderPromotion.key}">${orderPromotion.value}</a></li>
                                                        <li><hr class="dropdown-divider"></li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                            <%---------------------------------%>

                                        </c:if>

                                        <c:if test="${orderPromotionId ne null}">
                                            <input id="orderPromotionId" type="text" class="form-control" value="${orderPromotionId}" readonly>
                                            <button type="submit" class="btn btn-outline-dark" disabled>Enter</button>
                                        </c:if>
                                    </div>
                                    <c:if test="${message_order_promotion ne null}">
                                        <div class="input-group w-25" style="margin-right: 325px">
                                                ${message_order_promotion}
                                        </div>
                                    </c:if>
                                </form>

                                <br>

                                <form id="shippingPromoForm">
                                    <div class="input-group w-50">
                                        <c:if test="${shippingPromotionId eq null}">
                                            <input id="shippingPromotionId" type="text" class="form-control" placeholder="Enter your shipping promotion code here">
                                            <button id="shippingPromoSubmit" type="submit" class="btn btn-outline-dark">Enter</button>

                                            <!-- Example single danger button -->
                                            <div class="btn-group">
                                                <button id="dropdownShippingPromotions" type="button" class="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    Show shipping promotion code
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <c:forEach items="${shippingPromotions}" var="shippingPromotion">
                                                        <li><a class="shippingPromoId dropdown-item" id="${shippingPromotion.key}">${shippingPromotion.value}</a></li>
                                                        <li><hr class="dropdown-divider"></li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:if>

                                        <c:if test="${shippingPromotionId ne null}">
                                            <input id="shippingPromotionId" type="text" class="form-control" value="${shippingPromotionId}" readonly>
                                            <button type="submit" class="btn btn-outline-dark" disabled>Enter</button>
                                        </c:if>
                                    </div>

                                    <c:if test="${message_shipping_promotion ne null}">
                                        <div class="input-group w-25">
                                                ${message_shipping_promotion}
                                        </div>
                                    </c:if>
                                </form>


                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <br><br>
            
            <div class="row justify-content-center">
                <h2>Recipient's information</h2>
            </div>
            
            <br>
    
            <form id="orderForm" action="place_order" method="post">
                <c:set var="idPromotion" value="${promotionId}" scope="session"/>
                <div class="row justify-content-center">    
                    <div class="col-md-8">
                        <div class="mb-3">
                            <label for="firstname" class="form-label">Recipient's first name</label>
                            <input type="text" class="form-control" id="firstname" name="firstname" value="${loggedCustomer.firstname}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="lastname" class="form-label">Recipient's last name</label>
                            <input type="text" class="form-control" id="lastname" name="lastname" value="${loggedCustomer.lastname}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="phoneNumber" class="form-label">Recipient's phone</label>
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${loggedCustomer.phoneNumber}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="addressLine1" class="form-label">Address 1</label>
                            <input type="text" class="form-control" id="addressLine1" name="addressLine1" value="${loggedCustomer.addressLine1}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="addressLine2" class="form-label">Address 2</label>
                            <input type="text" class="form-control" id="addressLine2" name="addressLine2" value="${loggedCustomer.addressLine2}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="city" class="form-label">City</label>
                            <input type="text" class="form-control" id="city" name="city" value="${loggedCustomer.city}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="state" class="form-label">State</label>
                            <input type="text" class="form-control" id="state" name="state" value="${loggedCustomer.state}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="zip" class="form-label">Zip Code</label>
                            <input type="text" class="form-control" id="zip" name="zip" value="${loggedCustomer.zipcode}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="country" class="form-label">Country</label>
                            <select class="form-select" id="country" name="country">
                                <c:forEach items="${mapCountries}" var="country">
                                    <option value="${country.value}" <c:if test='${loggedCustomer.country eq country.value}'>selected='selected'</c:if>>${country.key}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                
                <br><br>
                <div class="row justify-content-center">
                    <h2>Payment method</h2>
                </div>
                
                <br>
                
                <div class="row justify-content-center mb-3">
                    <div class="col-md-8">
                        <label for="payment" class="form-label">Choose your payment method</label>
                        <select class="form-select" name="payment" id="payment">
                            <option value="Cash On Delivery">Cash On Delivery</option>
                            <option value="Paypal">Paypal or Credit card</option>
                        </select>
                    </div>
                </div>
                
                <div class="row justify-content-center mb-3">
                    <div class="col-md-8 d-grid gap-2 d-md-flex justify-content-md-end">
                        <button type="submit" class="btn btn-outline-success">Place Your Order</button>
                        <a href="${pageContext.request.contextPath}/" class="btn btn-outline-info">Continue Shopping</a>
                    </div>
                </div>
            </form>
        </c:if>
    </div>
    
    <br><br><br><br>
    <jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        <%--$("#enter_promotion").click(function(event){--%>
        <%--    event.preventDefault();--%>
        <%--    var input_promotion = document.getElementById("input_promotion").value--%>
        <%--    window.location.href = "check_promotion?promotionId=" + input_promotion + "&totalPrice=${totalPrice}";--%>
        <%--});--%>

        $(".orderPromoId").each(function (){
            $(this).on("click",function (event){
                event.preventDefault();
                $("#orderPromotionId").val($(this).attr("id"));
            })
        });

        $('.shippingPromoId').each(function (){
            $(this).on("click", function (event){
                event.preventDefault();
                $("#shippingPromotionId").val($(this).attr("id"));
            })
        });

        $("#orderPromoForm").submit(function (event){
            event.preventDefault();
            var orderPromotionId = $("#orderPromotionId").val();
            var subTotal = "${cart.totalAmount}";
            <%--var totalPrice = "${totalPrice}";--%>

            $.ajax({
                url: "check_order_promotion",
                type: "POST",
                data:{
                    orderPromotionId: orderPromotionId,
                    subTotal: subTotal
                    // totalPrice: totalPrice
                },
                success: function (response){
                    if(response.valid){
                        Swal.fire({
                            title: 'Are you sure you want to apply this promotion? Please remind yourself that you cannot change this',
                            text: "You will get " + response.discountPrice + " discount of your order",
                            icon: 'question',
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Yes, certainly!',
                            cancelButtonText: 'No, I change my mind'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                $("#orderDiscountPrice").text(formatCurrency(response.discountPrice));
                                $("#orderPromotionId").prop('readonly', true);
                                $("#orderPromoSubmit").prop('disabled', true)
                                $("#dropdownOrderPromotions").prop('disabled', true);
                                $("#spanTotalPrice").text(formatCurrency(response.newTotalPrice));

                                $.ajax({
                                    url: "update_order_sum_after_promotion",
                                    type: "POST",
                                    data:{
                                        updateTotalPrice: response.newTotalPrice
                                    },
                                    success: function (){
                                        Swal.fire("Apply promotion successfully!");
                                    },
                                    error: function (){
                                        Swal.fire("Errors appear while applying promotion");
                                    }
                                })
                            }
                        });
                    }
                    else{
                        Swal.fire("Invalid: " + response.message);
                        $("#orderDiscountPrice").text(formatCurrency(0));
                    }
                },
                error: function (){
                    Swal.fire("Error !!!");
                }
            });
        });

        $("#shippingPromoForm").submit(function (event){
            event.preventDefault();
            var shippingPromotionId = $("#shippingPromotionId").val();
            var subTotal = "${cart.totalAmount}";
            <%--var totalPrice = "${totalPrice}";--%>

            $.ajax({
                url: "check_shipping_promotion",
                type: "POST",
                data: {
                    shippingPromotionId: shippingPromotionId,
                    subTotal: subTotal
                },
                success: function(response){
                    if(response.valid){
                        Swal.fire({
                            title: 'Are you sure you want to apply this promotion? Please remind yourself that you cannot change this',
                            text: "You will get " + response.discountPrice + " discount of your order",
                            icon: 'question',
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Yes, certainly!',
                            cancelButtonText: 'No, I change my mind'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                $("#shippingDiscountPrice").text(formatCurrency(response.discountPrice));
                                $("#shippingPromotionId").prop('readonly', true);
                                $("#shippingPromoSubmit").prop('disabled', true)
                                $("#dropdownShippingPromotions").prop('disabled', true);
                                $("#spanTotalPrice").text(formatCurrency(response.newTotalPrice));

                                $.ajax({
                                    url: "update_order_sum_after_promotion",
                                    type: "POST",
                                    data:{
                                        updateTotalPrice: response.newTotalPrice
                                    },
                                    success: function (){
                                        Swal.fire("Apply promotion successfully!");
                                    },
                                    error: function (){
                                        Swal.fire("Errors appear while applying promotion");
                                    }
                                })
                            }
                        });
                    }
                    else{
                        Swal.fire("Invalid: " + response.message);
                    }
                },
                error: function (){
                    Swal.fire("Error!!!!!");
                }
            })

        });

        $("#testButton").on("click", function (event){
            var promoId = $("#orderPromotionId").val();
            if(promoId.trim() === ""){
                Swal.fire("Empty promo id");
            }
            else{
                Swal.fire("Invalid: " + response.message);
            }
        });

        function formatCurrency(amount){
            return new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(amount);
        }

        $("#orderForm").validate({
            rules:{
                firstname: "required",
                lastname: "required",
                phoneNumber: "required",
                addressLine1: "required",
                addressLine2: "required",
                city: "required",
                state: "required",
                zip: "required",
                country: "required",
            },
            
            messages:{
                firstname: "Please enter recipient's first name",
                lastname: "Please enter recipient's last name",
                phoneNumber: "Please enter recipient's phone number",
                addressLine1: "Please enter recipient's first address",
                addressLine2: "Please enter recipient's second address",
                city: "Please enter recipient's city",
                state: "Please enter recipient's state",
                zip: "Please enter recipient's zip code",
                country: "Please select recipient's country",
            }
        });
    });
</script>
</html>
