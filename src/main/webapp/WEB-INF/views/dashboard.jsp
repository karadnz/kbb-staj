<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
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
    <div class="d-flex justify-content-between">
        <h1>Customer Dashboard</h1>
        <!-- Logout button -->
        <form action="/addUser" method="get">
            <button type="submit" class="btn btn-success">Add User</button>
        </form>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Username</th>
            <th scope="col">Name</th>
            <th scope="col">E-mail</th>
            <th scope="col">Active</th>
            <th scope="col">s_id</th>
            <th scope="col">Actions</th>
            <th scope="col">Address</th>

        </tr>
        </thead>
        <tbody>

        <c:forEach items="${Persons}" var="person">
            <tr>
                <td data-eid="${person.eid}"><c:out value="${person.eid}"/></td>
                <td><c:out value="${person.username}"/></td>
                <td><c:out value="${person.name}"/></td>
                <td><c:out value="${person.email}"/></td>
                <td><c:out value="${person.enable}"/></td>
                <td data-sid="${person.s_id}"><c:out value="${person.s_id}"/></td>
                <td>
                    <a href="/edit?id=${person.eid}" class="btn btn-warning">Edit</a>
                    <a href="#" onclick="confirmDelete(${person.eid})" class="btn btn-danger">Delete</a>
                </td>
                <td>
                    <!-- Dropdown placeholder -->
                    <div id="sokakInfo_${person.eid}" class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle fetchSokakInfoOnClick" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Address Info
                        </button>
                        <!-- Dropdown content will be added here -->
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <!-- Content will be populated here -->
                        </div>
                    </div>

                </td>
            </tr>
        </c:forEach>



        </tbody>
    </table>
</div>

<script src="js/dashboard.js"></script>

</body>
</html>