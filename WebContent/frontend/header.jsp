<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row align-items-center justify-content-between">
        <div class="col-md-3 d-flex align-items-center">
            <a href="${pageContext.request.contextPath}">
                <img src="images/sport-shoes-icon-vector-id121242.png" style="max-height: 300px;" class="img-fluid">
            </a>
            <h1 class="ms-3">
                <a href="${pageContext.request.contextPath}" class="text-dark text-decoration-none">PHK Shirt Store</a>
            </h1>
        </div>
        
        <div class="col-md-6 d-flex align-items-center justify-content-end">
            <div class="dropdown me-3">
                <button class="btn btn-danger dropdown-toggle" type="button" id="categoryMenu" data-bs-toggle="dropdown" aria-expanded="false">
                    Categories
                </button>
                <ul class="dropdown-menu" aria-labelledby="categoryMenu">
                    <c:forEach var="type" items="${listType}">
                        <li>
                            <a class="dropdown-item" href="view_type?id=${type.typeId}">
                                <c:out value="${type.typeName}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="dropdown">
                <c:if test="${loggedCustomer != null}">
                    <button class="btn btn-dark dropdown-toggle" type="button" id="userMenu" data-bs-toggle="dropdown" aria-expanded="false">
                        Welcome, ${loggedCustomer.fullName}
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenu">
                        <li><a class="dropdown-item" href="view_profile">Profile</a></li>
                        <li><a class="dropdown-item" href="view_orders">My Orders</a></li>
                        <li><a class="dropdown-item" href="logout">Logout</a></li>
                    </ul>
                </c:if>
    
                <c:if test="${loggedCustomer == null}">
                    <a href="login" class="btn btn-outline-dark me-2">Login</a>
                    <a href="register" class="btn btn-outline-dark">Sign up</a>
                </c:if>
            </div>
    
            <a href="view_cart" class="btn btn-outline-dark ms-2"><img src="images/online-shopping.png" style="max-height: 20px;"></img>&nbsp;&nbsp;Cart</a>
        </div>
    </div>
	<br>
    <div align ="right">
    	<div class="col-md-3 d-flex align-items-center justify-content-end">
            <form action="search" method="get" class="d-flex">
                <div class="input-group input-group-sm">
                    <input type="text" name="keyword" class="form-control" placeholder="Search keywords"/>
                    <button type="submit" class="btn btn-dark">Search</button>
                </div>
            </form>
        </div>
    </div>
</div>