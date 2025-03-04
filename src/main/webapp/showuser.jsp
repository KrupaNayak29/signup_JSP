<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Users</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <!-- FontAwesome for Icons -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/js/all.min.js"></script>

    <script>
        function deleteUser(email) {
            if (confirm("Are you sure you want to delete this user?")) {
                fetch('DeleteUserServlet', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: 'email=' + encodeURIComponent(email)
                })
                .then(response => response.text())
                .then(result => {
                    if (result.trim() === "success") {
                        alert('User deleted successfully');
                        location.reload();
                    } else {
                        alert('Failed to delete user. ' + result);
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }
    </script>
</head>
<body>

<section class="pb-5 header text-center">
    <div class="container py-5 text-white">
        <header class="py-5">
            <h1 class="display-4">User Management</h1>
            <p class="font-italic mb-1">Manage users with Add, Edit, and Delete actions.</p>
        </header>

        <div class="row">
            <div class="col-lg-10 mx-auto">
                <div class="card border-0 shadow">
                    <div class="card-body p-5">
                        <div class="table-responsive">
                            <table class="table m-0">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Contact</th>
                                        <th>Role</th>
                                        <th>Addresses</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                    ResultSet rs = (ResultSet) request.getAttribute("resultSet");
                                    try {
                                        while (rs != null && rs.next()) {
                                    %>
                                    <tr>
                                        <td><%= rs.getInt("id") %></td>
                                        <td><%= rs.getString("uname") %></td>
                                        <td><%= rs.getString("uemail") %></td>
                                        <td><%= rs.getString("umobile") %></td>
                                        <td><%= rs.getString("roles") %></td>
                                        <td>
    <div class="dropdown">
        <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown">
            View Addresses
        </button>
        <ul class="dropdown-menu p-2">
            <% 
                String addresses = rs.getString("addresses"); 
                if (addresses != null && !addresses.isEmpty()) {
                    String[] addressArray = addresses.split(", ");
                    for (String address : addressArray) { 
            %>
                <li>
                    <a class="dropdown-item" href="#" onclick="selectAddress('<%= address %>')">
                        <%= address %>
                    </a>
                </li>
            <% 
                    }
                } else {
            %>
                <li class="dropdown-item">No Address Available</li>
            <% } %>
        </ul>
    </div>
</td>

                                        <td>
                                            <ul class="list-inline m-0">
                                                <li class="list-inline-item">
                                                    <button class="btn btn-success btn-sm" 
                                                        onclick="window.location.href='UpdateUserServlet?id=<%= rs.getInt("id") %>'">
                                                        <i class="fa fa-edit"></i> Edit
                                                    </button>
                                                </li>
                                                <li class="list-inline-item">
                                                    <button class="btn btn-danger btn-sm" type="button" title="Delete" 
                                                        onclick="deleteUser('<%= rs.getString("uemail") %>')">
                                                        <i class="fa fa-trash"></i>
                                                    </button>
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                    <% 
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <a href="admin.jsp" class="btn btn-secondary mt-3">Back to Admin</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Bootstrap JS for Dropdown -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function addAddress(userId) {
        let newAddress = document.getElementById("new-address-" + userId).value.trim();

        if (newAddress === "") {
            alert("Please enter an address.");
            return;
        }

        fetch("AddAddressServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "userId=" + encodeURIComponent(userId) + "&address=" + encodeURIComponent(newAddress)
        })
        .then(response => response.text())
        .then(result => {
            if (result.trim() === "success") {
                alert("Address added successfully!");
                location.reload(); // Refresh to show updated addresses
            } else {
                alert("Failed to add address: " + result);
            }
        })
        .catch(error => console.error("Error:", error));
    }
    function selectAddress(address) {
        document.getElementById("selected-address").value = address;
    }


</script>

</body>
</html>
