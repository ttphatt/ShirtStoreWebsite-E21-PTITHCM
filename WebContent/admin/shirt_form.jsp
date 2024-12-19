<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=yes">
    <title>
        <c:choose>
            <c:when test="${shirt != null}">Edit Shirts</c:when>
            <c:otherwise>Create New Shirts</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="pagehead.jsp"></jsp:include>
    <jsp:include page="pageLoad.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <link rel="stylesheet" type="text/css" href="../css/custom_background_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/custom_row_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/table_list_admin_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/search_button_template.css"/>
    <link rel="stylesheet" type="text/css" href="../css/custom_border_template.css"/>
</head>
<body>
    <jsp:directive.include file="header.jsp"/>

    <div class="background-div-content">
        <div class="container mb-5 mt-5">
            <div class="row justify-content-center">
                <div class="row custom-row-1 text-center" style="width: fit-content">
                    <h1>
                        <c:choose>
                            <c:when test="${shirt != null}">Edit Shirts</c:when>
                            <c:otherwise>Create New Shirts</c:otherwise>
                        </c:choose>
                    </h1>
                </div>
            </div>

            <div class="row justify-content-center text-center">
                <div class="col-md-7 justify-content-center text-center border custom-border" style="border-radius: 20px; padding: 25px">
                    <form action="${shirt == null ? 'create_shirt' : 'update_shirt'}" method="post" id="shirtForm" enctype="multipart/form-data">
                        <c:if test="${shirt != null}">
                            <input type="hidden" name="shirtId" id="shirtId" value="${shirt.shirtId}"/>
                        </c:if>
                        <div class="mb-3 text-start">
                            <label for="type" class="form-label">Type</label>
                            <select name="type" id="type" class="form-select">
                                <c:forEach var="type" items="${listType}">
                                    <option value="${type.typeId}" ${type.typeId == shirt.type.typeId ? 'selected' : ''}>${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3 text-start">
                            <label for="shirtName" class="form-label">Name of the shirts</label>
                            <input type="text" id="shirtName" name="shirtName" class="form-control" value="${shirt.shirtName}" required="required">
                        </div>
                        <div class="mb-3 text-start">
                            <label for="brand" class="form-label">Brand</label>
                            <input type="text" id="brand" name="brand" class="form-control" value="${shirt.brand}" required="required">
                        </div>
                        <div class="mb-3 text-start">
                            <label for="description" class="form-label">Description</label>
                            <textarea rows="5" name="description" id="description" class="form-control" required="required">${shirt.description}</textarea>
                        </div>
                        <div class="mb-3 text-start">
                            <label for="shirtImage" class="form-label">Shirts' image</label>
                            <input type="file" id="shirtImage" name="shirtImage" class="form-control" ${shirt == null ? 'required' : ''}>
                            <c:if test="${shirt != null}">
                                <img alt="Image Preview" id="thumbnail" class="img-thumbnail mt-3" style="max-height: 300px; max-width: 300px" src="${shirt.shirtImage}">
                            </c:if>
                        </div>
                        <div class="mb-3 text-start">
                            <label for="shirtPrice" class="form-label">Price</label>
                            <input type="number" step="0.01" min="0.01" id="shirtPrice" name="shirtPrice" class="form-control" value="${shirt.shirtPrice}" required="required">
                        </div>
                        <div class="mb-3 text-start">
                            <label for="releasedDate" class="form-label">Released Date</label>
                            <input type="date" id="releasedDate" name="releasedDate" class="form-control" value="<f:formatDate pattern='yyyy-MM-dd' value='${shirt.releasedDate}'/>" required="required">
                        </div>
                        <br><br>
                        <div class="d-flex justify-content-center">
                            <button type="submit" class="btn custom-btn-submit me-2">Save</button>
                            <button type="button" class="btn custom-btn-return" onclick="history.go(-1);">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:directive.include file="footer.jsp"/>
    <script>
        $(document).ready(function(){	
            $("#description").richText();
            $("#shirtImage").change(function(){
                showImageThumbnail(this);	
            });
        });
        
        function showImageThumbnail(fileInput){
            var file = fileInput.files[0];
            var reader = new FileReader();
            reader.onload = function(e){
                $("#thumbnail").attr("src", e.target.result);
            };
            reader.readAsDataURL(file);
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
    <script>
        $(document).ready(function () {
            function getMessageContent(messageId, event) {
                fetch('csvdata?id=' + messageId)
                    .then(response => response.json())
                    .then(data => {
                        if (data.message) {
                            let swalOptions = {
                                title: data.message,
                                confirmButtonText: "OK"
                            };

                            // Kiểm tra các loại thông báo
                            if (messageId === "SUCCESS_CREATE_NEW_SHIRT" || messageId === "SUCCESS_UPDATE_SHIRT") {
                                swalOptions.icon = "success";  // Biểu tượng success
                            } else if (messageId === "DUPLICATE_SHIRT_NAME" || messageId === "FAIL_UPDATE_SHIRT") {
                                swalOptions.icon = "error";  // Biểu tượng error
                            } else {
                                swalOptions.icon = "info";   // Biểu tượng mặc định
                            }

                            // Hiển thị thông báo với Swal
                            Swal.fire(swalOptions)
                                .then((result) => {
                                    if (result.isConfirmed) {
                                        // Chuyển hướng hoặc hành động sau khi nhấn OK nếu cần
                                        if (messageId === "SUCCESS_CREATE_NEW_SHIRT" || messageId === "SUCCESS_UPDATE_SHIRT") {
                                            window.location.href = "list_shirts";
                                        }
                                    }
                                });

                            event.preventDefault();
                        } else {
                            Swal.fire("Message not found");
                            event.preventDefault();
                        }
                    })
                    .catch(error => {
                        console.error("Error: ", error);
                    });
            }


            $("#shirtForm").submit(function (event){
                event.preventDefault();

                const shirtId = $("input[name='shirtId']").val();
                let type = document.getElementById("type").value;
                let shirtName = $("#shirtName").val();
                let brand = $("#brand").val();
                let description = $("#description").val();
                let shirtImage = $("#shirtImage")[0].files[0];
                let shirtPrice = $("#shirtPrice").val();
                let releasedDate = $("#releasedDate").val();
                const specialCharRegex = /[^a-zA-Z0-9\s]/;
                const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

                if(shirtName.trim() === ""){
                    getMessageContent("NOT_NULL_SHIRT_NAME", event);
                    $("#shirtName").focus();
                    return;
                }
                else if (shirtName.length > 50) {
                    getMessageContent("OVER_LENGTH_SHIRT_NAME", event);
                    $("#shirtName").focus();
                    return;
                }
                else if (specialCharRegex.test(shirtName)) {
                    getMessageContent("NO_ITALIC-CHARACTER_SHIRT_NAME", event);
                    $("#shirtName").focus();
                    return;
                }
                else if (brand.trim() === ""){
                    getMessageContent("NOT_NULL_SHIRT_BRAND", event);
                    $("#brand").focus();
                    return;
                }
                else if (brand.length > 50) {
                    getMessageContent("OVER_LENGTH_SHIRT_BRAND", event);
                    $("#brand").focus();
                    return;
                }
                else if (specialCharRegex.test(brand)) {
                    getMessageContent("NO_ITALIC-CHARACTER_SHIRT_BRAND", event);
                    $("#brand").focus();
                    return;
                }
                else if(description.trim() === ""){
                    getMessageContent("NOT_NULL_SHIRT_DESCRIPTION", event);
                    $("#description").focus();
                    return;
                }
                else if (description.length > 200) {
                    getMessageContent("OVER_LENGTH_SHIRT_DESCRIPTION", event);
                    $("#description").focus();
                    return;
                }
                else if (specialCharRegex.test(description)) {
                    getMessageContent("NO_ITALIC-CHARACTER_SHIRT_DESCRIPTION", event);
                    $("#description").focus();
                    return;
                }
                else if (!shirtId && $("#shirtImage")[0].files.length === 0) {
                    getMessageContent("NOT_NULL_SHIRT_IMAGE", event);
                    $("#shirtImage").focus();
                    return;
                }
                else if(!allowedExtensions.exec($("#shirtImage").val()) && !($("#shirtImage").val() === "")){
                    getMessageContent("INVALID_FILE_EXTENSION", event);
                    $("#shirtImage").val("").focus();
                    return;
                }
                else if($("#shirtImage")[0].files.length > 0){
                    const shirtImageFile = $("#shirtImage")[0].files[0];
                    const maxFileSize = 1024 * 300; // 300 KB
                    if (shirtImageFile.size > maxFileSize) {
                        getMessageContent("OVER_SIZE_IMAGE", event);
                        $("#shirtImage").focus();
                        return;
                    }
                }
                else if(shirtPrice.trim() === ""){
                    getMessageContent("NOT_NULL_SHIRT_PRICE", event);
                    $("#shirtPrice").focus();
                    return;
                }
                else if (Number(shirtPrice) <= 0) {
                    getMessageContent("ERROR_NUM_FIELD_SHIRT_PRICE", event); // Giá phải lớn hơn 0
                    $("#shirtPrice").focus();
                    return;
                }
                else if (!releasedDate){
                    getMessageContent("NOT_NUT_SHIRT_DATE", event);
                    $("#releasedDate").focus();
                    return;
                }


                console.log("sid " + shirtId);

                const formData = new FormData();
                formData.append("shirtId", shirtId);
                formData.append("type", type);
                formData.append("shirtName", shirtName);
                formData.append("brand", brand);
                formData.append("description", description);
                formData.append("shirtImage", shirtImage);
                formData.append("shirtPrice", shirtPrice);
                formData.append("releasedDate", releasedDate);

                const url = shirtId ? "update_shirt" : "create_shirt";
                $.ajax({
                    url: url,
                    type: "POST",
                    data:formData,
                    contentType: false,
                    processData: false,
                    success: function (response){
                        if(response.valid === false){
                            const message1 = shirtId ? "FAIL_UPDATE_SHIRT" : "DUPLICATE_SHIRT_NAME";
                            getMessageContent(message1, event);
                        }else{
                            const message = shirtId ? "SUCCESS_UPDATE_SHIRT" : "SUCCESS_CREATE_NEW_SHIRT";
                            getMessageContent(message, event);
                        }
                    },
                    error: function (){
                        getMessageContent("PERSIT_ERROR",event);
                    }
                })
            });
            $("#shirtForm").validate({
                rules:{
                    type: "required",
                    shirtName: "required",
                    brand: "required",
                    description: "required",
                    shirtImage: "required",
                    shirtPrice: "required",
                    releasedDate: "required"
                },
                messages:{
                    type: "",
                    shirtName: "",
                    brand: "",
                    description: "",
                    shirtImage: "",
                    shirtPrice: "",
                    releasedDate: ""
                }
            })

        });
    </script>
</body>
</html>
