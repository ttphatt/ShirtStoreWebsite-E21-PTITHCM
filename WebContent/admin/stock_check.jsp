<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<head>
    <jsp:include page="pagehead.jsp"></jsp:include>
    <jsp:include page="pageLoad.jsp"/>
    <link href="../css/warehouse-style.css" rel="stylesheet" type="text/css" />
</head>

<body class="body">
    <jsp:directive.include file="header.jsp" />
    <br><br><br><br>

    <div class="w-layout-blockcontainer container w-container">

        <div class="nav-wrapper">
            <h1 class="heading-h1">Warehouse</h1>

            <div class="nav-button-wrapper">
                <a href="view_import" class="button w-button">View Import Receipt</a>
                <a href="create_import" class="button w-button">Create Import Receipt</a>
                <a href="stock_check" class="button w-button">Stock Check</a>
            </div>

            <h1 class="heading-h2">Stock Check</h1>
        </div>

        <div class="search-wrapper">
            <form action="stock_check_search" class="search w-form">
                <input class="search-input w-input" maxlength="256"
                       name="query" placeholder="Search…" type="search" id="search" required="" />
                <input type="submit"
                       class="button is-medium-button w-button" value="Search" />
            </form>
        </div>

        <table class="table-wrapper">
            <thead class="table_head">
                <tr class="table_row">
                    <th class="table_header align-middle text-center">Index</th>
                    <th class="table_header align-middle text-center">Product&#x27;s ID</th>
                    <th class="table_header align-middle text-center">Product&#x27;s Name</th>
                    <th class="table_header align-middle text-center">Quantity</th>
                    <th class="table_header align-middle text-center">Created Date</th>
                    <th class="table_header align-middle text-center">Updated Date</th>
                </tr>
            </thead>
            <tbody class="table_body" id="tableBody">
                <c:forEach var="product" items="${listProducts}" varStatus="status">
                    <tr class="table_row">
                        <td class="table_cell align-middle text-center">${status.index + 1}</td>
                        <td class="table_cell align-middle text-center">${product.product_id}</td>
                        <td class="table_cell align-middle text-center">${product.product_name}</td>
                        <td class="table_cell align-middle text-center">
                            <select class="list">
                                <option>${product.realQuantity} / ${product.quantity}</option>
                                <c:forEach var="size" items="${product.listSizes}">
                                    <option>${size.type}: ${size.quantity}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="table_cell align-middle text-center">${product.created_date}</td>
                        <td class="table_cell align-middle text-center">${product.updated_date}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination-wrapper">
            <a href="#" class="button is-medium-button w-button" id="prevPage" >Previous</a>
            <a href="#" class="button is-medium-button w-button" id="nextPage" >Next</a>
        </div>
    </div>

    <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=671a39728a62cdca4bd8ee35"
            type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous">
    </script>

    <script src="../js/warehouse-js.js" type="text/javascript"></script>

    <script>
        let curPage = 1
        let recordsPerPage = 10;
        let products = onload();
        document.getElementById("prevPage").addEventListener("click", prevPage);
        document.getElementById("nextPage").addEventListener("click", nextPage);
        changePage(1);

        function onload() {
            return document.getElementById("tableBody").getElementsByTagName("tr");
        }

        function prevPage() {
            if (curPage > 1) {
                curPage--;
                changePage(curPage);
            }
        }

        function nextPage() {
            if (curPage < numPages()) {
                curPage++;
                changePage(curPage);
            }
        }

        function changePage(page) {
            for (let i = 0; i < products.length; i++) {
                products[i].style.display = "none";
            }

            for (let i = (page - 1) * recordsPerPage; i < products.length && i < (page * recordsPerPage); i++) {
                products[i].style.display = "flex";
            }
        }

        function numPages() {
            return Math.ceil(products.length / recordsPerPage);
        }
    </script>
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