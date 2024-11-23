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
            <c:when test="${promotion != null}">Edit Promotion</c:when>
            <c:otherwise>Create New Promotion</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="pagehead.jsp"></jsp:include>
</head>
<body>
<jsp:directive.include file="header.jsp"/>
<div class="container mt-5">
    <div class="row">
        <div class="col-12 text-center">
            <h1>
                <c:choose>
                    <c:when test="${promotion != null}">Edit Promotion</c:when>
                    <c:otherwise>Create New Promotion</c:otherwise>
                </c:choose>
            </h1>
        </div>
    </div>
    <div class="row justify-content-center mt-4">
        <div class="col-md-8">
            <form action="${promotion == null ? 'create_promotion' : 'update_promotion'}" method="post" id="promotionForm">
                <c:if test="${promotion != null}">
                    <input type="hidden" name="promotionId" value="${promotion.promotionId}"/>
                </c:if>

                <c:if test="${promotion != null}">
                    <div class="mb-3">
                        <label for="promotionId1" class="form-label">Promotion code </label>
                        <input type="text" id="promotionId1" name="promotionId" class="form-control" value="${promotion.promotionId}" readonly required>
                    </div>
                </c:if>
                <c:if test="${promotion == null}">
                    <div class="mb-3">
                        <label for="promotionId" class="form-label">Promotion code </label>
                        <input type="text" id="promotionId" name="promotionId" class="form-control" value="${promotion.promotionId}" required>
                    </div>
                </c:if>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea rows="3" name="description" id="description" class="form-control" required>${promotion.description}</textarea>
                </div>

                <div class="mb-3">
                    <label for="startDate" class="form-label">Validity Period</label>
                </div>
                <div class="mb-3" style="display: flex; gap: 10px;">
                    <div style="flex: 1;">
                        <label for="startDate" class="form-label">Start Date</label>
                        <input type="date" id="startDate" name="startDate" class="form-control" value="<f:formatDate pattern='yyyy-MM-dd' value='${promotion.startDate}'/>" required>
                    </div>
                    <div style="flex: 1;">
                        <label for="endDate" class="form-label">End Date</label>
                        <input type="date" id="endDate" name="endDate" class="form-control" value="<f:formatDate pattern='yyyy-MM-dd' value='${promotion.endDate}'/>" required>
                    </div>
                </div>

                <c:if test="${promotion != null}">
                    <div class="mb-3">
                        <label for="type1" class="form-label">Promotion Type</label>
                        <select name="type" id="type1" class="form-control" disabled required="required">
                            <option value="" <c:if test="${promotion.type == null}">selected</c:if>></option>
                            <option value="Order Discount" <c:if test="${promotion.type == 'Order Discount'}">selected</c:if>>Order Discount</option>
                            <option value="Shipping Discount" <c:if test="${promotion.type == 'Shipping Discount'}">selected</c:if>>Shipping Discount</option>
                        </select>
                    </div>
                </c:if>

                <c:if test="${promotion == null}">
                    <div class="mb-3">
                        <label for="type" class="form-label">Promotion Type</label>
                        <select name="type" id="type" class="form-control" required="required">
                            <option value="" <c:if test="${promotion.type == null}">selected</c:if>></option>
                            <option value="Order Discount" <c:if test="${promotion.type == 'Order Discount'}">selected</c:if>>Order Discount</option>
                            <option value="Shipping Discount" <c:if test="${promotion.type == 'Shipping Discount'}">selected</c:if>>Shipping Discount</option>
                        </select>
                    </div>
                </c:if>

                <div class="mb-3">
                    <label for="percent" class="form-label">Detail Discount</label>
                </div>
                <c:if test="${promotion != null}">
                    <div class="mb-3" style="display: flex; gap: 10px;">
                        <div style="flex: 1; position: relative;">
                            <label for="percent1" class="form-label">Discount Rate</label>
                            <input type="number" id="percent1" name="percent" class="form-control" value="${promotion.percent}" required min="0" max="100" step="0.01" readonly>
                            <span style="position: absolute; right: 10px; top: 35px; pointer-events: none;">%</span>
                        </div>
                        <div style="flex: 1;">
                            <label for="maxDiscount1" class="form-label">Max Discount</label>
                            <input type="text" id="maxDiscount1" name="maxDiscount" class="form-control" value="${promotion.maxDiscount}" required min="0" step="0.01" readonly>
                        </div>
                    </div>
                </c:if>
                <c:if test="${promotion == null}">
                    <div class="mb-3" style="display: flex; gap: 10px;">
                        <div style="flex: 1; position: relative;">
                            <label for="percent" class="form-label">Discount Rate</label>
                            <input type="number" id="percent" name="percent" class="form-control" value="${promotion.percent}" required min="0" max="100" step="0.01">
                            <span style="position: absolute; right: 10px; top: 35px; pointer-events: none;">%</span>
                        </div>
                        <div style="flex: 1;">
                            <label for="maxDiscount" class="form-label">Max Discount</label>
                            <input type="text" id="maxDiscount" name="maxDiscount" class="form-control" value="${promotion.maxDiscount}" required min="0" step="0.01">
                        </div>
                    </div>
                </c:if>

                <c:if test="${promotion != null}">
                    <div class="mb-3">
                        <label for="priceLimit1" class="form-label">Order's Limit Price To Apply Promotion </label>
                        <input type="number" id="priceLimit1" name="priceLimit" class="form-control" value="${promotion.priceLimit}" required readonly/>
                    </div>
                </c:if>
                <c:if test="${promotion == null}">
                    <div class="mb-3">
                        <label for="priceLimit" class="form-label">Order's Limit Price To Apply Promotion </label>
                        <input type="number" id="priceLimit" name="priceLimit" class="form-control" value="${promotion.priceLimit}" required/>
                    </div>
                </c:if>
                <div class="mb-3">
                    <label for="quantityInStock" class="form-label">Quantity of promotion</label>
                    <input type="number" id="quantityInStock" name="quantityInStock" class="form-control" value="${promotion.quantityInStock}" required>
                </div>
                <c:if test="${promotion != null}">
                    <label for="state" class="form-label">State</label>

                    <select name="status" id="state" class="form-control" required="required">
                        <option value="Pending" <c:if test="${promotion.status == 'Pending'}">selected</c:if>>Pending</option>
                        <option value="Active" <c:if test="${promotion.status == 'Active'}">selected</c:if>>Active</option>
                        <option value="Expired" <c:if test="${promotion.status == 'Expired'}">selected</c:if>>Expired</option>
                    </select>
                </c:if>
                <c:if test="${promotion == null}">
                    <div class="mb-3">
                        <label for="status" class="form-label">State</label>
                        <input type="text" id="status" name="status" class="form-control" value="Pending" required readonly>
                    </div>
                </c:if>

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
</script>
</body>
</html>
