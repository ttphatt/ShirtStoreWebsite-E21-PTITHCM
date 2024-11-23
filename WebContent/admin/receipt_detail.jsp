<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<head>
    <jsp:include page="pagehead.jsp"></jsp:include>
    <jsp:include page="pageLoad.jsp"/>

    <link href="../css/warehouse-style.css" rel="stylesheet" type="text/css" />
</head>

<body class="body-2">

    <jsp:directive.include file="header.jsp" />
    <br><br><br><br>

    <div class="w-layout-blockcontainer w-container">
        <div class="nav-wrapper">
            <h1 class="heading-h1">Warehouse</h1>

            <div class="nav-button-wrapper">
                <a href="view_import" class="button w-button">View Import Receipt</a>
                <a href="create_import" class="button w-button">Create Import Receipt</a>
                <a href="stock_check" class="button w-button">Stock Check</a>
            </div>

            <h1 class="heading-h2">Import Receipt Detail</h1>
        </div>

        <div class="form-wrapper w-form">
            <form id="email-form" name="email-form" data-name="Email Form" method="get" class="form" action="#">
                <div class="fields-wrapper">
                    <label for="name" class="field-label">Staff&#x27;s Name</label>
                    <input class="text-field w-input" maxlength="256" name="name" data-name="Name" placeholder=""
                        type="text" id="name" value="${import_info.import_id}" readonly />
                </div>

                <div class="fields-wrapper">
                    <label for="name-2" class="field-label">Import&#x27;s ID</label>
                    <input class="text-field w-input" maxlength="256" name="name-2" data-name="Name 2" placeholder=""
                        type="text" id="name-2" value="${import_info.import_id}" readonly/>
                </div>

                <div class="fields-wrapper">
                    <label for="name-2" class="field-label">Total price</label>
                    <input class="text-field w-input" maxlength="256" name="name-2" data-name="Name 2" placeholder=""
                        type="text" id="name-2" value="${import_info.total_price}" readonly />
                </div>

                <div class="fields-wrapper is-contains-datefield">
                    <label for="name-3" class="field-label">Created Date</label>
                    <input type="text" class="input" value="${import_info.import_date}" readonly/>
                </div>
            </form>
        </div>

        <table class="table-wrapper">
            <thead class="table_head">
                <tr class="table_row">
                    <th class="table_header align-middle text-center">Index</th>
                    <th class="table_header align-middle text-center">Product&#x27;s Name</th>
                    <th class="table_header align-middle text-center">Size</th>
                    <th class="table_header align-middle text-center">Quantity</th>
                    <th class="table_header align-middle text-center">Price</th>
                    <th class="table_header align-middle text-center">Total</th>
                </tr>
            </thead>
            <tbody class="table_body">
                <c:forEach var="import_detail" items="${listImportDetails}" varStatus="status">
                    <tr class="table_row">
                        <td class="table_cell align-middle text-center">${status.index + 1}</td>
                        <td class="table_cell align-middle text-center">${import_detail.product_name}</td>
                        <td class="table_cell align-middle text-center">${import_detail.size}</td>
                        <td class="table_cell align-middle text-center">${import_detail.quantity}</td>
                        <td class="table_cell align-middle text-center">${import_detail.price}</td>
                        <td class="table_cell align-middle text-center">${import_detail.price * import_detail.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script
            src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=671ef903248a1b1d233e0e01"
            type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous">
    </script>

    <script src="../js/warehouse-js.js" type="text/javascript"></script>
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

    <br><br><br><br>
    <jsp:directive.include file="footer.jsp" />

</body>

</html>