<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6 text-center">
            <h1>Welcome!</h1>

            <!-- Employee Section -->
            <div class="mb-4">
                <h2>Employee</h2>
                <a href="/employeeLogin" class="btn btn-primary">Login</a>
                <a href="/employeeRegister" class="btn btn-secondary">Register</a>
            </div>

            <!-- Customer Section -->
            <div class="mb-4">
                <h2>Customer</h2>
                <a href="/customerLogin" class="btn btn-primary">Login</a>
                <a href="/customerRegister" class="btn btn-secondary">Register</a>
            </div>
        </div>
        <div class="col-sm-3"></div>
    </div>
</div>

</body>
</html>
