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
</head>
<body>
    <jsp:directive.include file="header.jsp"/>
    <div class="container mt-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1>
                    <c:choose>
                        <c:when test="${shirt != null}">Edit Shirts</c:when>
                        <c:otherwise>Create New Shirts</c:otherwise>
                    </c:choose>
                </h1>
            </div>
        </div>
        <div class="row justify-content-center mt-4">
            <div class="col-md-8">
                <form action="${shirt == null ? 'create_shirt' : 'update_shirt'}" method="post" id="shirtForm" enctype="multipart/form-data">
                    <c:if test="${shirt != null}">
                        <input type="hidden" name="shirtId" value="${shirt.shirtId}"/>
                    </c:if>
                    <div class="mb-3">
                        <label for="type" class="form-label">Type</label>
                        <select name="type" class="form-select">
                            <c:forEach var="type" items="${listType}">
                                <option value="${type.typeId}" ${type.typeId == shirt.type.typeId ? 'selected' : ''}>${type.typeName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="shirtName" class="form-label">Name of the shirts</label>
                        <input type="text" id="shirtName" name="shirtName" class="form-control" value="${shirt.shirtName}" required="required">
                    </div>
                    <div class="mb-3">
                        <label for="brand" class="form-label">Brand</label>
                        <input type="text" id="brand" name="brand" class="form-control" value="${shirt.brand}" required="required">
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea rows="5" name="description" id="description" class="form-control" required="required">${shirt.description}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="shirtImage" class="form-label">Shirts' image</label>
                        <input type="file" id="shirtImage" name="shirtImage" class="form-control" ${shirt == null ? 'required' : ''}>
                        <c:if test="${shirt != null}">
                            <img alt="Image Preview" id="thumbnail" class="img-thumbnail mt-3" style="max-height: 300px; max-width: 300px" src="${shirt.shirtImage}">
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="shirtPrice" class="form-label">Price</label>
                        <input type="number" step="0.01" min="0.01" id="shirtPrice" name="shirtPrice" class="form-control" value="${shirt.shirtPrice}" required="required">
                    </div>
                    <div class="mb-3">
                        <label for="releasedDate" class="form-label">Released Date</label>
                        <input type="date" id="releasedDate" name="releasedDate" class="form-control" value="<f:formatDate pattern='yyyy-MM-dd' value='${shirt.releasedDate}'/>" required="required">
                    </div>
                    <br><br>
                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-success me-2">Save</button>
                        <button type="button" class="btn btn-secondary" onclick="history.go(-1);">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <br><br><br>
    <jsp:directive.include file="footer.jsp"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@shinsenter/jquery-richtext@2.0.5/jquery.richtext.min.js"></script>
    <script type="text/javascript">
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
</body>
</html>
