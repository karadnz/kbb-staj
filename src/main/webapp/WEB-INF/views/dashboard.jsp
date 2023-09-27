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

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>


</head>

<body>

<!-- navbar -->
<mytags:navbar/>

<div class="container">
    <div class="row">
        <!-- Left Column: User List -->
        <div class="col-md-6">
            <h2>User List</h2>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Username</th>
                    <th scope="col">E-mail</th>
                    <th scope="col">Details</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${Persons}" var="person">
                    <tr>
                        <td><c:out value="${person.eid}"/></td>
                        <td><c:out value="${person.username}"/></td>
                        <td><c:out value="${person.email}"/></td>
                        <td>
                            <button class="btn btn-info fetchSokakInfoOnClick" data-person-id="${person.eid}">Details</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Right Column: Detailed User Info and Map -->
        <div class="col-md-6">
            <h2>User Details</h2>
            <div id="map" style="height: 400px;"></div>

            <!-- User details under the map -->
            <div id="detailedUserInfo" class="mt-4 border p-3 rounded">
                <h3>User Details</h3>
                <hr>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <strong>ID:</strong>
                        <span id="detailEid"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <strong>Username:</strong>
                        <span id="detailUsername"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <strong>Name:</strong>
                        <span id="detailName"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <strong>Email:</strong>
                        <span id="detailEmail"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <strong>Active:</strong>
                        <span id="detailActive"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <strong>s_id:</strong>
                        <span id="detailSid"></span>
                    </div>

                    <!-- Placeholder for roles -->
                    <div class="col-md-12 mb-3">
                        <strong>Roles:</strong>
                        <ul id="detailRoles"></ul>
                    </div>

                    <!-- Add other fields as needed in the same pattern -->

                    <div class="d-flex justify-content-end">
                        <a id="editButton" href="#" class="btn btn-warning mr-2">Edit</a>
                        <a id="deleteButton" href="#" onclick="return confirmDelete();" class="btn btn-danger">Delete</a>
                    </div>

                </div>


            </div>


        </div>
    </div>
</div>

<script>
    const userToken = '<%= session.getAttribute("token") %>';
</script>
<script src="js/dashboard.js"></script>

</body>
</html>