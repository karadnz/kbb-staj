<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
</head>
<body>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
</head>
<body>

<!-- navbar -->
<nav class="navbar navbar-expand bg-dark border-bottom border-body" data-bs-theme="dark">

    <div class="container-fluid">

        <a class="navbar-brand" href="#">APP</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                </li>
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

<div class="container">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <form method="post" action="/login">
                <h2>Login</h2>

                <c:if test="${errors != null}">
                    <c:forEach items="${errors}" var="item">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong><c:out value="${item.field}"> </c:out></strong>
                            <c:out value="${item.defaultMessage}"> </c:out>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:forEach>
                </c:if>

                <c:if test="${errorMessage != null}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error:</strong>
                        <c:out value="${errorMessage}"> </c:out>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <div class="mb-3">
                    <input name="username" class="form-control" type="text" placeholder="Username" />
                </div>
                <div class="mb-3">
                    <input name="password" class="form-control" type="password" placeholder="Password" />
                </div>
                <button class="btn btn-primary">Login</button>
            </form>
        </div>
        <div class="col-sm-4"></div>
    </div>
</div>

</body>
</html>
