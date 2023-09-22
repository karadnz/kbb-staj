<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
</head>
<body>

<!-- navbar -->
<mytags:navbar/>

<div class="container">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <form method="post" action="/register">
                <h2>Register</h2>

                <c:if test="${errors != null}">
                    <c:forEach items="${errors}" var="error">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong><c:out value="${error.getField()}"></c:out></strong>
                            <c:out value="${error.getDefaultMessage()}"></c:out>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:forEach>
                </c:if>

                <!-- Existing Employee -->
                <h4>Existing Employee</h4>
                <div class="mb-3">
                    <input name="username" class="form-control" type="text" placeholder="username">
                </div>
                <div class="mb-3">
                    <input name="password" class="form-control" type="password" placeholder="password">
                </div>


                <button type="submit" class="btn btn-success">Register</button>
            </form>
        </div>
        <div class="col-sm-4"></div>
    </div>
</div>




</body>
</html>
