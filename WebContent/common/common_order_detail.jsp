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
    <title>Order Overview</title>
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <jsp:include page="pageLoad.jsp"/>

</head>
<body>
    <br>
    <form action="update_order" method="post">
    <div class="container">
        <div class="row justify-content-center">
            <h2>Order Overview</h2>
        </div>
        
        <br>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <table class="table table-striped table-bordered">
                    <tr>
                        <td><b>Ordered by</b></td>
                        <td>${order.customer.fullName}</td>
                    </tr>
                    <tr>
                        <td><b>Order status</b></td>
                        <td>${order.status}</td>
                    </tr>
                    <tr>
                        <td><b>Order date</b></td>
                        <td>${order.orderDate}</td>
                    </tr>
                    <tr>
                        <td><b>Payment method</b></td>
                        <td>${order.payment}</td>
                    </tr>
                    <tr>
                        <td><b>Number of shirts</b></td>
                        <td>${order.quantityOfShirts}</td>
                    </tr>
                    <tr>
                        <td><b>Total price</b></td>
                        <td><fmt:formatNumber type="currency" value="${order.orderSum}"/></td>
                    </tr>
                </table>
            </div>
        </div>

        <br>
        <div class="row justify-content-center">    
            <h2>Recipient Information</h2>
        </div>    
        <br>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <input type="hidden" name="orderId" value="${order.orderId}">
                <c:if test="${order.status == 'Processing'}">
                    <table class="table table-striped table-bordered">
                        <tr>
                            <td><b>Recipient's first name</b></td>
                            <td><input type="text" name="firstname" value="${order.firstname}" size="45" required="required" minlength="2" maxlength="50" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>Recipient's last name</b></td>
                            <td><input type="text" name="lastname" value="${order.lastname}" size="45" required="required" minlength="2" maxlength="50" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>Recipient's phone number</b></td>
                            <td><input type="text" name="phone" value="${order.phone}" size="45" required="required" minlength="2" maxlength="10" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>Address line 1</b></td>
                            <td><input type="text" name="addressLine1" value="${order.addressLine1}" size="45" required="required" minlength="5" maxlength="200" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>Address line 2</b></td>
                            <td><input type="text" name="addressLine2" value="${order.addressLine2}" size="45" required="required" minlength="5" maxlength="200" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>City</b></td>
                            <td><input type="text" name="city" value="${order.city}" size="45" required="required" minlength="5" maxlength="50" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>State</b></td>
                            <td><input type="text" name="state" value="${order.state}" size="45" required="required" minlength="5" maxlength="50" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>Zip code</b></td>
                            <td><input type="text" name="zipcode" value="${order.zipcode}" size="45" required="required" minlength="5" maxlength="9" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><b>Country</b></td>
                            <td>
                                <select name="country" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split">
                                    <c:forEach items="${mapCountries}" var="country">
                                        <option value="${country.value}" <c:if test='${order.country eq country.value}'>selected='selected'</c:if> >${country.key}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${order.status != 'Processing'}">
                    <table class="table table-striped table-bordered">
                        <tr>
                            <td><b>Recipient's first name</b></td>
                            <td>${order.firstname}</td>
                        </tr>
                        <tr>
                            <td><b>Recipient's last name</b></td>
                            <td>${order.lastname}</td>
                        </tr>
                        <tr>
                            <td><b>Recipient's phone number</b></td>
                            <td>${order.phone}</td>
                        </tr>
                        <tr>
                            <td><b>Address line 1</b></td>
                            <td>${order.addressLine1}</td>
                        </tr>
                        <tr>
                            <td><b>Address line 2</b></td>
                            <td>${order.addressLine2}</td>
                        </tr>
                        <tr>
                            <td><b>City</b></td>
                            <td>${order.city}</td>
                        </tr>
                        <tr>
                            <td><b>State</b></td>
                            <td>${order.state}</td>
                        </tr>
                        <tr>
                            <td><b>Zip code</b></td>
                            <td>${order.zipcode}</td>
                        </tr>
                        <tr>
                            <td><b>Country</b></td>
                            <td>${order.countryName}</td>
                        </tr>
                    </table>
                </c:if>
            </div>
        </div>
    
        <br>
        <div class="row justify-content-center">
            <h2>Ordered Shirts</h2>
        </div>
        <br>
        <div class="row justify-content-center">
            <div class="col-md-10">
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th class="text-center">Index</th>
                            <th colspan="2" class="text-center">Shirts' name</th>
                            <th class="text-center">Size</th>
                            <th class="text-center">Brand</th>
                            <th class="text-center">Price</th>
                            <th class="text-center">Quantity</th>
                            <th class="text-center">Sub total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
                            <tr>
                                <td class="text-center">${status.index + 1}</td>
                                <td class="text-center">
                                    <img src="${orderDetail.shirt.shirtImage}" class="img-fluid" style="width: 150px; height: auto;">
                                </td>
                                <td class="text-center">${orderDetail.shirt.shirtName}</td>
                                <td class="text-center">${orderDetail.size}</td>
                                <td class="text-center">${orderDetail.shirt.brand}</td>
                                <td class="text-center"><fmt:formatNumber type="currency" value="${orderDetail.shirt.shirtPrice}"/></td>
                                <td class="text-center">${orderDetail.quantity}</td>
                                <td class="text-center"><fmt:formatNumber type="currency" value="${orderDetail.subTotal}"/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="8" class="text-end">
                                <p><b>Subtotal: </b><fmt:formatNumber type="currency" value="${order.subtotal}"/></p>
                                <p><b>Tax: </b><fmt:formatNumber type="currency" value="${order.tax}"/></p>
                                <p><b>Shipping fee: </b><fmt:formatNumber type="currency" value="${order.shippingFee}"/></p>

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
                                    <p><b>Shipping Discount: </b><fmt:formatNumber type="currency" value="-0"/></p>
                                    <p><b>Order Discount: </b><fmt:formatNumber type="currency" value="-0"/></p>
                                </c:if>

                                <%--Order sum Display Section--%>
                                <p><b>Total: </b><fmt:formatNumber type="currency" value="${order.orderSum}"/></p>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div class="d-flex justify-content-end mt-4">
                    <c:if test="${order.status == 'Delivered'}">
                        <a href="return_order?orderId=${order.orderId}" class="btn btn-outline-warning">Return your order</a>&nbsp;&nbsp;
                    </c:if>

                    <c:if test="${order.status == 'Processing'}">
                        <a href="#" id="cancel_link" class="btn btn-outline-danger">Cancel your order</a>&nbsp;&nbsp;
                    </c:if>

                    <c:if test="${order.status == 'Delivered'}">
                        <a href="complete_order?orderId=${order.orderId}" class="btn btn-outline-success">Completed</a>&nbsp;&nbsp;
                    </c:if>

                    <button type="button" class="btn btn-outline-secondary" onclick="history.go(-1);">Back</button>&nbsp;&nbsp;

                    <c:if test="${order.status == 'Processing'}">
                        <button type="submit" class="btn btn-outline-info">Save</button>
                    </c:if>
                </div>

            </div>
        </div>
    </div>
    </form>
    <br><br><br><br>
</body>
</html>
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
    document.getElementById("cancel_link").addEventListener("click", function (event){
        event.preventDefault()
        Swal.fire({
            title: 'Are you sure?',
            text: "Do you want to cancel your order?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, certainly!',
            cancelButtonText: 'Nah, I think again'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "cancel_order?orderId=${order.orderId}";
            }
        });
    });

</script>
