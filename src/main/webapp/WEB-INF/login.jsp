<%--
  Created by IntelliJ IDEA.
  User: mhsar
  Date: 8/26/2024
  Time: 9:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="includes/header.jsp" %>
<%@ include file="includes/navigation.jsp" %>

<div class="container">
    <div>
        <c:if test="${message != null}">
            <div class="alert alert-success">
                    ${message}
            </div>
        </c:if>
    </div>
    <h2>Log In</h2>
    <hr class="mb-4">
    <form class="form-horizontal" role="form"
          action="<c:url value="/login"/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text"
                   class="form-control"
                   id="username"
                   name="username"
            />
            <c:if test="${errors.username != null}">
                <small class="text-danger">${errors.username}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
            />
            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>
        <hr class="mb-4">
        <div class="mb-2">
            <span>
                Don't have a user account?
                <a class="btn-link" href="<c:url value="/signup"/>">
                    SignUp
                </a>
            </span>
        </div>

        <div class="form-group">
            <button class="btn btn-primary btn-lg" type="submit">
                Login
            </button>
        </div>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>

