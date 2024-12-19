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
    <link rel="stylesheet" type="text/css" href="css/heart_rating.css"/>
    <link rel="stylesheet" type="text/css" href="css/for_product.css"/>
    <link rel="stylesheet" type="text/css" href="css/search_button_template.css">

    <link rel="stylesheet" type="text/css" href="css/custom_background_template.css"/>
    <link rel="stylesheet" type="text/css" href="css/custom_row_template.css"/>
    <link rel="stylesheet" type="text/css" href="css/custom_border_template.css"/>
    <link rel="stylesheet" type="text/css" href="css/table_template.css"/>

    <style>
        body {
            padding-top: 0;
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

<div class="background-div-content">
    <div class="container mt-5">
        <!-- Shirt Details Section -->
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="mb-4" style="border:none">
                    <div class="row g-3">
                        <div class="col-md-4 d-flex justify-content-center align-items-center border custom-border">
                            <img src="${shirt.shirtImage}" class="image-product" alt="${shirt.shirtName}">
                        </div>

                        <div class="col-md-4 d-flex justify-content-center align-items-center">
                            <div align="center">
                                <h2 class="card-title">${shirt.shirtName}</h2>
                                <br>
                                <p class="card-text">From: ${shirt.brand}</p>
                                <div class="mb-2">
                                    <jsp:directive.include file="shirt_rating.jsp"/>
                                </div>
                                <h3 class="text-success">Price: $${shirt.shirtPrice}</h3>
                            </div>
                        </div>

                        <div class="col-md-4 d-flex justify-content-center align-items-center">
                            <div class="row justify-content-center">
                                <div class="row custom-row" style="padding: 10px; width: fit-content">
                                    <div class="form-check">
                                        <label class="form-check-label me-1">
                                            <input type="radio" class="btn-check" id="sizeS" value="S" checked name="size">
                                            <label class="btn custom-btn-size-S" style="padding: 10px 10px" for="sizeS">S</label>

                                            <%--                                            <input class="form-check-input" type="radio" value="S" checked name="size" />--%>
                                            <%--                                            <h1>S</h1>--%>
                                        </label>

                                        <label class="form-check-label me-1">
                                            <input type="radio" class="btn-check" id="sizeM" value="M" name="size">
                                            <label class="btn custom-btn-size-M" style="padding: 10px 10px" for="sizeM">M</label>

                                            <%--                                            <input class="form-check-input" type="radio" value="M" name="size" />--%>
                                            <%--                                            <span class="name">M</span>--%>
                                        </label>

                                        <label class="form-check-label me-1">
                                            <input type="radio" class="btn-check" id="sizeL" value="L" name="size">
                                            <label class="btn custom-btn-size-L" style="padding: 10px 10px" for="sizeL">L</label>

                                            <%--                                            <input class="form-check-input" type="radio" value="L" name="size" />--%>
                                            <%--                                            <span class="name">L</span>--%>
                                        </label>

                                        <label class="form-check-label me-1">
                                            <input type="radio" class="btn-check" id="sizeXL" value="XL" name="size">
                                            <label class="btn custom-btn-size-XL" style="padding: 10px 10px" for="sizeXL">XL</label>

                                            <%--                                            <input class="form-check-input" type="radio" value="XL" name="size" />--%>
                                            <%--                                            <span class="name">XL</span>--%>
                                        </label>

                                        <label class="form-check-label me-1">
                                            <input type="radio" class="btn-check" id="sizeXXL" value="XXL" name="size">
                                            <label class="btn custom-btn-size-XXL" style="padding: 10px 10px" for="sizeXXL">XXL</label>

                                            <%--                                            <input class="form-check-input" type="radio" value="XXL" name="size" />--%>
                                            <%--                                            <span class="name">XXL</span>--%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row justify-content-center">
                                    <button id="buttonAddToCart" class="btn custom-btn-cart mt-3 fs-5">Add to your cart</button>
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
                <div class="card border custom-border">
                    <div class="card-body text-center p-2">
                        <h2 class="card-title fs-2">Shirts' Description</h2>
                        <p class="card-text fs-5">${shirt.description}</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Customer Rates Section -->
        <div class="row justify-content-center mb-5">
            <div class="col-lg-8">
                <div class="card border custom-border">
                    <div class="card-body">
                        <h2 class="card-title text-center"><a id="rates">Customer's Rate</a></h2>
                        <div class="centered-button mb-3 mt-3">
                            <button id="buttonWriteRate" class="btn custom-btn-rate mb-3 fs-5">Rate our shirts</button>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover table-custom">
                                <tbody>
                                <c:forEach items="${shirt.rates}" var="rate">
                                    <tr>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <c:forTokens items="${rate.stars}" delims="," var="star">
                                                    <c:if test="${star eq 'on'}">
                                                        <img src="images/NewWholeStar.png" class="me-1" style="max-width: 15px">
                                                    </c:if>
                                                    <c:if test="${star eq 'off'}">
                                                        <img src="images/NewHollowStar.png" class="me-1" style="max-width: 15px">
                                                    </c:if>
                                                </c:forTokens>

                                            </div>
                                            <div class="mt-2 fs-5">
                                                <b>${rate.headline}: </b>${rate.ratingDetail}
                                            </div>
                                            <div>(Rated by ${rate.customer.fullName} on ${rate.rateTime})</div>
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


