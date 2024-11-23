<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .radio-inputs {
            position: relative;
            margin-top: 20px;
            display: flex;
            flex-wrap: wrap;
            border-radius: 0.5rem;
            background-color: #FFFFFF;
            box-sizing: border-box;
            box-shadow: 0 0 0px 1px rgba(0, 0, 0, 0.06);
            padding: 16px;
            width: 100%;
            font-size: 14px;
        }

        .radio-inputs .radio {
            flex: 1 1 auto;
            text-align: center;
        }

        .radio-inputs .radio input {
            display: none;
        }

        .radio-inputs .radio .name {
            display: flex;
            font-weight: 600;
            cursor: pointer;
            align-items: center;
            justify-content: center;
            border-radius: 0.5rem;
            border: none;
            padding: .5rem 0;
            color: #C72464;
            transition: all .15s ease-in-out;
        }

        .radio-inputs .radio input:checked + .name {
            background-color: #371800;
            color: #FF6D00;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <br><br><br><br>

    <div class="container">
        <div class="row justify-content-center">
            <h2>Your cart</h2>
        </div>
        
        <c:if test="${message != null}">
            <div class="row justify-content-center text-danger">
                <h4>${message}</h4>
            </div>
        </c:if>

        <c:set var="cart" value="${sessionScope['cart']}" />
        
        <c:if test="${cart.totalItems == 0}">
            <div class="row justify-content-center">
                <h2>There is nothing in your cart</h2>
            </div>
        </c:if>
        
        <br>
        
        <div class="row justify-content-center text-center">
            <c:if test="${cart.totalItems > 0}">
                <form action="update_cart" method="post" id="cartForm">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead class="table-dark">
                                <tr>
                                    <th>No</th>
                                    <th colspan="2">Product</th>
                                    <th>Size</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Subtotal</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            
                            <tbody>
                                <c:forEach var="item" items="${cart.carts}" varStatus="status">
                                    <tr class="align-middle">
                                        <td>${status.index + 1}</td>
                                        <td>
                                            <img src="${item.shirt.shirtImage}" class="img-fluid" style="width: 150px; height: auto;">
                                        </td>
                                        <td>${item.shirt.shirtName}</td>

                                        <td>
                                            <div class="radio-inputs">
                                                <label class="radio">
                                                    <input type="radio" id="S${status.index + 1}" name="size${status.index + 1}" value="S" <c:if test="${item.size == 'S'}">checked</c:if> />
                                                    <span class="name">S</span>
                                                </label>

                                                <label class="radio">
                                                    <input type="radio" id="M${status.index + 1}" name="size${status.index + 1}" value="M" <c:if test="${item.size == 'M'}">checked</c:if> />
                                                    <span class="name">M</span>
                                                </label>

                                                <label class="radio">
                                                    <input type="radio" id="L${status.index + 1}" name="size${status.index + 1}" value="L" <c:if test="${item.size == 'L'}">checked</c:if> />
                                                    <span class="name">L</span>
                                                </label>

                                                <label class="radio">
                                                    <input type="radio" id="XL${status.index + 1}" name="size${status.index + 1}" value="XL" <c:if test="${item.size == 'XL'}">checked</c:if> />
                                                    <span class="name">XL</span>
                                                </label>

                                                <label class="radio">
                                                    <input type="radio" id="XXL${status.index + 1}" name="size${status.index + 1}" value="XXL" <c:if test="${item.size == 'XXL'}">checked</c:if> />
                                                    <span class="name">XXL</span>
                                                </label>
                                            </div>
                                        </td>


                                        <td>
                                            <input type="hidden" name="shirtId" value="${item.shirt.shirtId}"/>
                                            <input type="text" name="quantity${status.index + 1}" value="${item.quantity}" size="5" class="form-control text-center"/>
                                        </td>
                                        <td><fmt:formatNumber value="${item.shirt.shirtPrice}" type="currency"/></td>
                                        <td><fmt:formatNumber value="${item.quantity * item.shirt.shirtPrice}" type="currency"/></td>
                                        <td>
                                            <button
                                                    type="button"
                                                    class="btn btn-outline-secondary btn-sm btn-remove"
                                                    data-shirt-id="${item.shirt.shirtId}"
                                                    data-index="${status.index + 1}">
                                                Remove
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                
                                <tr>
                                    <td colspan="4"></td>
                                    <td class="align-middle text-center"><b>${cart.totalQuantity} Number of shirts</b></td>
                                    <td class="align-middle text-center"><b>Total</b></td>
                                    <td colspan="2" class="align-middle text-center"><b><fmt:formatNumber value="${cart.totalAmount}" type="currency"/></b></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <br>
                    <div class="row justify-content-right">
                        <div class="col-md-8 d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-info me-md-2">Continue Shopping</a>
                            <button type="submit" class="btn btn-danger me-md-2">Update</button>
                            <button id="clearCart" type="button" class="btn btn-danger me-md-2">Clear Cart</button>
                            <a href="checkout" class="btn btn-outline-success">Check Out</a>
                        </div>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
    
    <br><br><br><br>
    <jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $("#cartForm").validate({
            rules: {
                <c:forEach var="item" items="${cart.carts}" varStatus="status">
                    quantity${status.index + 1}: {
                        required: true,
                        number: true,
                        min: 1
                    },
                </c:forEach>
            },
            messages: {
                <c:forEach var="item" items="${cart.carts}" varStatus="status">
                    quantity${status.index + 1}: {
                        required: "Please enter the quantity",
                        number: "Quantity must be a number",
                        min: "Quantity must be greater than 0"
                    },
                </c:forEach>
            }
        });

        $("#clearCart").click(function(){
            window.location = 'clear_cart';
        });


        $(".btn-remove").click(function(){
            const shirtId = $(this).data("shirt-id");
            const rowIndex = $(this).data("index");

            const radioButtonGroup = document.getElementsByName("size" + rowIndex);
            let checkedRadio = Array.from(radioButtonGroup).find(
                (radio) => radio.checked
            );

            window.location = 'remove_from_cart?shirtId=' + shirtId + '&size=' + checkedRadio.value;
        });

    });
</script>
</html>
