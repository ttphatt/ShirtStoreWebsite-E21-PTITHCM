<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Review Payment</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" type="text/css" href="css/custom_background_template.css"/>
    <link rel="stylesheet" type="text/css" href="css/custom_row_template.css"/>
    <link rel="stylesheet" type="text/css" href="css/custom_border_template.css"/>

    <link rel="stylesheet" type="text/css" href="css/table_template.css"/>
    <link rel="stylesheet" type="text/css" href="css/table_product_template.css"/>

</head>
<body>
<jsp:directive.include file="header.jsp"/>

<div class="background-div-content">
    <div class="container">
        <div class="row justify-content-center mt-3">
            <div class="row custom-row" style="background-color: #FB8479">
                <h2 class="text-center">Please check your information carefully before making payment</h2>
            </div>
        </div>

        <br><br>

        <div class="row justify-content-center">
            <div class="row custom-row w-25 text-center">
                <h2>Payer Information</h2>
            </div>
        </div>

        <br>

        <div class="row justify-content-center">
            <div class="col-md-4">
                <table class="table table-striped table-bordered table-hover table-custom">
                    <tr>
                        <td><b>First Name:</b></td>
                        <td>${payer.firstName}</td>
                    </tr>
                    <tr>
                        <td><b>Last Name:</b></td>
                        <td>${payer.lastName}</td>
                    </tr>
                    <tr>
                        <td><b>Email:</b></td>
                        <td>${payer.email}</td>
                    </tr>
                </table>
            </div>
        </div>

        <br><br>

        <div class="row justify-content-center">
            <div class="row custom-row-1 text-center" style="width: fit-content">
                <h2>Recipient Information</h2>
            </div>
        </div>

        <br>

        <div class="row justify-content-center">
            <div class="col-md-6">
                <table class="table table-striped table-bordered table-hover table-custom">
                    <tr>
                        <td><b>Recipient's Name:</b></td>
                        <td>${recipient.recipientName}</td>
                    </tr>
                    <tr>
                        <td><b>Address Line 1:</b></td>
                        <td>${recipient.line1}</td>
                    </tr>
                    <tr>
                        <td><b>Address Line 2:</b></td>
                        <td>${recipient.line2}</td>
                    </tr>
                    <tr>
                        <td><b>City:</b></td>
                        <td>${recipient.city}</td>
                    </tr>
                    <tr>
                        <td><b>State:</b></td>
                        <td>${recipient.state}</td>
                    </tr>
                    <tr>
                        <td><b>Country Code:</b></td>
                        <td>${recipient.countryCode}</td>
                    </tr>
                    <tr>
                        <td><b>Postal Code:</b></td>
                        <td>${recipient.postalCode}</td>
                    </tr>
                </table>
            </div>
        </div>

        <br><br>

        <div class="row justify-content-center">
            <div class="row custom-row text-center" style="width: fit-content">
                <h2>Transaction Details</h2>
            </div>
        </div>

        <br>

        <div class="row justify-content-center">
            <div class="col-md-10 text-center">
                <table class="table table-bordered table-hover table-product-custom">
                    <tr>
                        <td><b>Description:</b></td>
                        <td>${transaction.description}</td>
                    </tr>
                    <tr>
                        <td colspan="2"><b>Item List:</b></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table class="table table-bordered table-hover table-product-custom">
                                <thead class="thead-dark">
                                <tr>
                                    <th class="text-center">No.</th>
                                    <th class="text-center">Name</th>
                                    <th class="text-center">Quantity</th>
                                    <th class="text-center">Price</th>
                                    <th class="text-center">Subtotal</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${transaction.itemList.items}" var="item" varStatus="status">
                                    <tr>
                                        <td class="text-center">${status.index + 1}</td>
                                        <td class="text-center">${item.name}</td>
                                        <td class="text-center">${item.quantity}</td>
                                        <td class="text-center"><fmt:formatNumber value="${item.price}" type="currency"/></td>
                                        <td class="text-center"><fmt:formatNumber value="${item.price * item.quantity}" type="currency"/></td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="5" class="text-end">
                                        <p><b>Subtotal: </b><fmt:formatNumber value="${transaction.amount.details.subtotal}" type="currency" /></p>
                                        <p><b>Tax: </b><fmt:formatNumber value="${transaction.amount.details.tax}" type="currency" /></p>
                                        <p><b>Shipping fee: </b><fmt:formatNumber value="${transaction.amount.details.shipping}" type="currency" /></p>
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
                                        <p><b>Total price: </b><fmt:formatNumber value="${transaction.amount.total}" type="currency" /></p>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="row justify-content-center mb-5 mt-2">
            <div class="col text-center">
                <form class="justify-content-center" action="execute_payment" method="post">
                    <input type="hidden" name="paymentId" value="${param.paymentId}" />
                    <input type="hidden" name="PayerID" value="${param.PayerID}" />
                    <button type="submit" class="btn custom-btn-cart fs-5">Pay now</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:directive.include file="footer.jsp"/>
</body>
</html>
