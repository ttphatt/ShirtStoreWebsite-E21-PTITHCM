<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders History</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="pageLoad.jsp"/>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <br><br><br><br>
    
    <div class="container">
        <div class="row justify-content-center text-center">
            <h1>My Orders History</h1>
        </div>
        
        <div>
            <hr width="70%"/>
        </div>
    
        <br>
    
        <c:if test="${fnc:length(listOrders) == 0}">
            <div class="row justify-content-center">
                <h2>You don't have any orders yet</h2>
            </div>
        </c:if>
        
        <c:if test="${fnc:length(listOrders) > 0}">
            <div class="row justify-content-center">
                <div class="col-md-10">
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Index</th>
                                <th>Order's ID</th>
                                <th>Quantity</th>
                                <th>Total Price</th>
                                <th>Order Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${listOrders}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${order.orderId}</td>
                                    <td>${order.quantityOfShirts}</td>
                                    <%--Order sum Display Section--%>
                                    <td><fmt:formatNumber type="currency" value="${order.orderSum}"/></td>
                                    <%-----------------------------%>
                                    <td>${order.orderDate}</td>
                                    <td>${order.status}</td>
                                    <td>
                                        <a href="show_order_detail?orderId=${order.orderId}" class="btn btn-outline-dark btn-sm">View Details</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </div>
    
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
</html>
