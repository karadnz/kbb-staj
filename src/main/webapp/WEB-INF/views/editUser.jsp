<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>
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
            <form method="post" action="/editUser/${idToEdit}"> <!-- Assuming the user's ID is passed as 'user.id' -->
                <h2>Edit user</h2>

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
                    <input name="username" class="form-control" type="text" placeholder="Username" value="${user.username}" />
                </div>
                <div class="mb-3">
                    <input name="name" class="form-control" type="text" placeholder="Name" value="${user.name}" />
                </div>
                <div class="mb-3">
                    <input name="email" class="form-control" type="email" placeholder="E-mail" value="${user.email}" />
                </div>
                <!-- Note: Typically, you might not want to pre-populate the password field for security reasons -->

                <div class="mb-3">
                    <select id="illerDropdown" class="form-control">
                        <option>Select Iller</option>
                    </select>
                </div>
                <div class="mb-3">
                    <select id="ilcelerDropdown" class="form-control">
                        <option>Select Ilceler</option>
                    </select>
                </div>
                <div class="mb-3">
                    <select id="mahallelerDropdown" class="form-control">
                        <option>Select Mahalleler</option>
                    </select>
                </div>
                <div class="mb-3">
                    <select name="s_id" id="sokaklarDropdown" class="form-control">
                        <option>Select Sokaklar</option>
                    </select>
                </div>

                <c:forEach items="${roles}" var="role">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="${role.rid}" name="roleRids" id="role-${role.rid}"
                               <c:if test="${user.roles.contains(role)}">checked</c:if> > <!-- Check the checkbox if the user has this role -->
                        <label class="form-check-label" for="role-${role.rid}">
                                ${role.name}
                        </label>
                    </div>
                </c:forEach>

                <button class="btn btn-primary">Save Changes</button>
            </form>
        </div>
        <div class="col-sm-4"></div>
    </div>
</div>

<script src="http://localhost:8090/js/editUser.js"></script>
</body>
</html>
