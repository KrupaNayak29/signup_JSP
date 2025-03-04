<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.InitialContext" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Users</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/js/all.min.js"></script>
    <script>
        function deleteUser(email) {
            if (confirm("Are you sure you want to delete this user?")) {
                let form = document.createElement("form");
                form.method = "post";
                form.action = "DeleteUserServlet";
                let input = document.createElement("input");
                input.type = "hidden";
                input.name = "email";
                input.value = email;
                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
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
                <div class="col-lg-8 mx-auto">
                    <div class="card border-0 shadow">
                        <div class="card-body p-5">
                            <div class="table-responsive">
                                <table class="table m-0">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">Contact</th>
                                            <th scope="col">Role</th>
                                            <th scope="col">Address</th>
                                            <th scope="col">Actions</th>
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
                                            <td><%= rs.getString("address_line") %></td>
                                            <td>
                                                <ul class="list-inline m-0">
                                                    <li class="list-inline-item">
                                                        <button class="btn btn-primary btn-sm" type="button" title="Add"><i class="fa fa-user-plus"></i></button>
                                                    </li>
                                                    <li class="list-inline-item">
                                                        <button class="btn btn-success btn-sm" type="button" title="Edit"><i class="fa fa-edit"></i></button>
                                                    </li>
                                                    <li class="list-inline-item">
                                                        <button class="btn btn-danger btn-sm" type="button" title="Delete" onclick="deleteUser('<%= rs.getString("uemail") %>')"><i class="fa fa-trash"></i></button>
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
</body>
</html>
