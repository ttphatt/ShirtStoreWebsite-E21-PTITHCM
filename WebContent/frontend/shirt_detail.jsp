<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${shirt.shirtName} - PHK shirts store</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">

    <script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link href="../css/radio_button.css" rel="stylesheet" type="text/css" />

    <style>
        body {
            padding-top: 70px;
        }
        .img-fluid {
            max-height: 240px;
            object-fit: cover;
        }
        .card-img-top {
            max-height: 240px;
            object-fit: cover;
        }
        .centered-button {
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <div class="container mt-5">
        <!-- Shirt Details Section -->
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card mb-4" style="border:none">
                    <div class="row g-0">
                        <div class="col-md-4 d-flex justify-content-center align-items-center">
                            <img src="${shirt.shirtImage}" class="img-fluid card-img-top" alt="${shirt.shirtName}">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body" align="center">
                                <h2 class="card-title">${shirt.shirtName}</h2>
                                <p class="card-text">From: ${shirt.brand}</p>
                                <div class="mb-2"><jsp:directive.include file="shirt_rating.jsp"/></div>
                                <h3 class="text-success">Price: $${shirt.shirtPrice}</h3>
                                <div class="radio-inputs">
                                    <label class="radio">
                                        <input type="radio" value="S" checked name="size" />
                                        <span class="name">S</span>
                                    </label>

                                    <label class="radio">
                                        <input type="radio" value="M" name="size" />
                                        <span class="name">M</span>
                                    </label>

                                    <label class="radio">
                                        <input type="radio" value="L" name="size" />
                                        <span class="name">L</span>
                                    </label>

                                    <label class="radio">
                                        <input type="radio" value="XL" name="size" />
                                        <span class="name">XL</span>
                                    </label>

                                    <label class="radio">
                                        <input type="radio" value="XXL" name="size" />
                                        <span class="name">XXL</span>
                                    </label>
                                </div>

                                <div class="centered-button">
                                    <button id="buttonAddToCart" class="btn btn-success mt-3">Add to your cart</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<br><br>
        <!-- Shirt Description Section -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title" align="center">Shirts' Description</h2>
                        <p class="card-text">${shirt.description}</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Customer Rates Section -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title text-center"><a id="rates">Customer's Rate</a></h2>
                        <div class="centered-button">
                            <button id="buttonWriteRate" class="btn btn-danger mb-3">Rate our shirts</button>
                        </div>
                        <div class="table-responsive">
                            <table class="table">
                                <tbody>
                                    <c:forEach items="${shirt.rates}" var="rate">
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <c:forTokens items="${rate.stars}" delims="," var="star">
                                                        <c:if test="${star eq 'on'}">
                                                            <img src="images/OnStarV1.png" class="me-1">
                                                        </c:if>
                                                        <c:if test="${star eq 'off'}">
                                                            <img src="images/OffStarV1.png" class="me-1">
                                                        </c:if>
                                                    </c:forTokens>
                                                    <b>${rate.headline}</b>
                                                </div>
                                                <div>by ${rate.customer.fullName} on ${rate.rateTime}</div>
                                                <div>${rate.ratingDetail}</div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:directive.include file="footer.jsp"/>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $("#buttonWriteRate").click(function(){
            window.location = 'write_rate?shirtId=' + ${shirt.shirtId};
        });

        $("#buttonAddToCart").click(function(){
            let radioButtonGroup = document.getElementsByName("size");
            let checkedRadio = Array.from(radioButtonGroup).find(
                (radio) => radio.checked
            );

            window.location = 'add_to_cart?shirtId=' + ${shirt.shirtId} + '&size=' + checkedRadio.value;
            console.log(checkedRadio.value);
        });
    });
</script>
</html>


