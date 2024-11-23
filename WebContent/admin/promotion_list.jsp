<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Managing Promotion</title>
    <jsp:include page="pagehead.jsp"></jsp:include>
    <jsp:include page="pageLoad.jsp"/>
    <link href="../css/temp.css" rel="stylesheet" type="text/css" />

    <style>
        .toggle-switch {
            display: inline-block;
            position: relative;
            width: 60px;
            height: 34px;
        }

        .toggle-input {
            display: none;
        }

        .toggle-label {
            position: absolute;
            top: 0;
            left: 0;
            width: 60px;
            height: 34px;
            background-color: #ccc;
            border-radius: 34px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .toggle-label:before {
            content: "";
            position: absolute;
            top: 2px;
            left: 2px;
            width: 30px;
            height: 30px;
            background-color: #fff;
            border-radius: 50%;
            transition: transform 0.3s;
        }

        .toggle-input:checked + .toggle-label {
            background-color: #4fbf26;
        }

        .toggle-input:checked + .toggle-label:before {
            transform: translateX(26px);
        }
    </style>
</head>
<body>
<jsp:directive.include file="header.jsp"/>
<br><br><br><br>

<div align="center">
    <h1>Promotion Management</h1>
    <hr width="70%">
    <h3><a href="promotion_form.jsp" class="btn btn-outline-primary btn-lg">Create new Promotion</a></h3>
</div>

<br>

<c:if test="${message != null}">
    <c:choose>
        <c:when test="${message.contains('successfully')}">
            <div align="center" class="alert alert-info" role="alert">
                <h4>${message}</h4>
            </div>
        </c:when>
        <c:otherwise>
            <div align="center" class="alert alert-danger" role="alert">
                <h4>${message}</h4>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<br>

<div align="center">
    <table  class="table table-bordered" style="width: 500px">
        <thead class="table-dark">
        <tr>
            <th class="align-middle justify-content-center text-center">Index</th>
            <th class="align-middle justify-content-center text-center">Promotion</th>
            <th class="align-middle justify-content-center text-center">Code</th>
            <th class="align-middle justify-content-center text-center">Type</th>
            <th class="align-middle justify-content-center text-center">Showing on customer page</th>
            <th class="align-middle justify-content-center text-center">State</th>
            <th class="align-middle justify-content-center text-center">In use</th>
            <th class="align-middle justify-content-center text-center">Start Date</th>
            <th class="align-middle justify-content-center text-center">End Date</th>
            <th class="align-middle justify-content-center text-center">Actions</th>
        </tr>
        </thead>

        <tbody id="tableBody">
        <c:forEach var="promotion" items="${listPromotion}" varStatus="status">
            <tr>
                <td class="align-middle justify-content-center text-center">${status.index + 1}</td>
                <td class="align-middle justify-content-center text-center">${promotion.description}</td>
                <td class="align-middle justify-content-center text-center">${promotion.promotionId}</td>
                <td class="align-middle justify-content-center text-center">${promotion.type}</td>
                <td class="align-middle justify-content-center text-center">
                    <div class="toggle-switch">
                        <input ${promotion.doDisplay ? 'checked' : ''} class="toggle-input" value="${promotion.promotionId}" id="toggle${status.index}" type="checkbox" onclick="togglePromotionCode(${status.index})"/>
                        <label class="toggle-label" for="toggle${status.index}"></label>
<%--                            <input class="toggle-input" id="toggle${status.index}" type="checkbox" />--%>
<%--                            <label class="toggle-label" for="toggle${status.index}"></label>--%>
                    </div>
                </td>
                <td class="align-middle justify-content-center text-center">${promotion.status}</td>
                <td class="align-middle justify-content-center text-center">${promotion.usedPromotion}/${promotion.quantityInStock}</td>
                <td class="align-middle justify-content-center text-center"><f:formatDate pattern='dd/MM/yyyy' value='${promotion.startDate}'/></td>
                <td class="align-middle justify-content-center text-center"><f:formatDate pattern='dd/MM/yyyy' value='${promotion.endDate}'/></td>
                <td class="align-middle justify-content-center text-center">
                    <a href="edit_promotion?promotionId=${promotion.promotionId}" class="btn btn-outline-primary">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div align="center">
    <div class="pagination-wrapper">
        <a href="#" class="paginationButton is-medium-button w-button" id="prevPage" >Previous</a>
        <a href="#" class="paginationButton is-medium-button w-button" id="nextPage" >Next</a>
    </div>
</div>

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
            products[i].style.display = "table-row";
        }
    }

    function numPages() {
        return Math.ceil(products.length / recordsPerPage);
    }
</script>

<br><br><br><br>
<jsp:directive.include file="footer.jsp"/>
<script>
    $(document).ready(function (){
        function getMessageContent(messageId, event){
            fetch('csvdata?id=' + messageId)
                .then(response => response.json())
                .then(data =>{
                    if(data.message){
                        Swal.fire(data.message);
                        event.preventDefault();
                    }
                    else{
                        Swal.fire("Message not found");
                        event.preventDefault();
                    }
                })
                .catch(error => Swal.fire(error.message));
        }

        $('input[type="checkbox"]').change(function (event){
            var promotionId = $(this).val();
            var isChecked = $(this).is(':checked');

            $.ajax({
                url: "updatePromotionDisplay",
                type: "POST",
                data:{
                    promotionId: promotionId,
                    doDisplay: isChecked
                },
                success: function (response){
                    if(isChecked){
                        Swal.fire("Being displayed")
                    }
                    else{
                        Swal.fire("Not displayed")
                    }
                },
                error: function (){
                    alert('Error updating promotion status');
                }
            });
        });
    });

    function togglePromotionCode(index) {
        var checkbox = document.getElementById("toggle" + index);
        var promotionCodeContainer = document.getElementById("promotionCodeContainer" + index);

        if (checkbox.checked) {
            promotionCodeContainer.style.display = "table-row";
        } else {
            promotionCodeContainer.style.display = "none";
        }
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
</body>
</html>
