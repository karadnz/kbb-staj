<!-- navbar -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand bg-dark border-bottom border-body" data-bs-theme="dark">

    <div class="container-fluid">

        <a class="navbar-brand" href="#">APP</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${currentUser != null}">
                        <!-- if user logged in -->
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/addUser">Add User</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <!-- if user is not logged in -->
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/login">Log in</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/register">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

            <!-- if user logged in display info -->
            <c:if test="${currentUser != null}">
                <li class="dropdown d-flex navbar-text pe-5">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        User: <c:out value="${currentUser}" />
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="/profile">Profile</a></li>
                        <li><a class="dropdown-item" href="/settings">Settings</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <form class="dropdown-item" action="/logout" method="post">
                                <button class="btn btn-danger btn-sm" type="submit">Log out</button>
                            </form>
                        </li>
                    </ul>
                </li>
            </c:if>

        </div>

    </div>

</nav>