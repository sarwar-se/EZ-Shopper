<%--
  Created by IntelliJ IDEA.
  User: mhsar
  Date: 8/24/2024
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://eshoppers.com/functions" %>
<%@ include file="includes/header.jsp" %>
<%@ include file="includes/navigation.jsp" %>

<div class="container">

    <div class=" mb-3 p-3 bg-light rounded row">
        <div class="row">
            <c:if test="${message != null}">
                <div class="alert alert-success">
                        ${message}
                </div>
            </c:if>
        </div>

        <div class="col-6">
            <c:if test="${sec:isAuthenticated(pageContext.request)}">
                <h2> Hello <c:out value="${sec:getCurrentUser(pageContext.request).firstName}"/>,
                    Welcome to e-shoppers!</h2>
            </c:if>
            <img src="<c:url value="/image/cart.jpg"/>" style="height: 100px" alt="cart-image"/>
        </div>
        <div class="col-6">
            <c:if test="${cart != null && cart.cartItems.size() > 0}">
                <div class="card shadow-sm p-3 mb-5 bg-white">
                    <div class="card-header">
                        <h4>Your Cart</h4>
                    </div>
                    <div class="card-body">
                        <p>
                            Total Item: <span class="badge bg-success"><c:out value="${cart.totalItem}"/></span>
                        </p>
                        <p>Total Price: $ <c:out value="${cart.totalPrice}"/></p>
                        <p>
                            <a href="<c:url value="/checkout"/>" class="btn btn-outline-info">Checkout</a>
                        </p>
                    </div>
                </div>
            </c:if>
        </div>

    </div>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${product.name}"/></h5>
                        <p class="card-text"><c:out value="${product.description}"/></p>
                        <p class="card-text">Price: $ <c:out value="${product.price}"/></p>
                        <a class="card-link btn btn-outline-info"
                           onclick="addToCart(${product.id})"
                        >Add to Cart</a>

                        <form style="visibility: hidden"
                              id="addToCart_${product.id}"
                              method="post"
                              action="<c:url value="/add-to-cart?productId=${product.id}" />"
                        >
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>

<script>
    function addToCart(productId) {
        let form = document.getElementById("addToCart_" + productId);
        form.submit();
    }
</script>
