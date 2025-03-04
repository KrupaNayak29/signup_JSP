<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Edit User</h2>
        
        <form action="UpdateUserServlet" method="post">
            <input type="hidden" name="id" value="<%= request.getAttribute("userId") != null ? request.getAttribute("userId") : "" %>">

            <div class="mb-3">
                <label class="form-label">Name</label>
                <input type="text" class="form-control" name="name" 
                       value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" name="email" 
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Contact</label>
                <input type="text" class="form-control" name="contact" 
                       value="<%= request.getAttribute("contact") != null ? request.getAttribute("contact") : "" %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Addresses</label>
                <div id="address-container">
                    <% 
                    String addresses = (String) request.getAttribute("addresses");
                    if (addresses != null && !addresses.isEmpty()) {
                        String[] addressArray = addresses.split(", ");
                        for (String address : addressArray) { 
                    %>
                        <div class="input-group mb-2">
                            <input type="text" class="form-control" name="address_line" value="<%= address %>" required>
                            <button type="button" class="btn btn-danger" onclick="removeAddress(this)">Delete</button>
                        </div>
                    <% 
                        } 
                    } 
                    %>
                </div>
                <button type="button" class="btn btn-success mt-2" onclick="addAddress()">Add Address</button>
            </div>

            <button type="submit" class="btn btn-primary">Update User</button>
            <a href="showuser.jsp" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <script>
    function addAddress() {
        let container = document.getElementById("address-container");
        let div = document.createElement("div");
        div.classList.add("input-group", "mb-2");
        div.innerHTML = `
            <input type="text" class="form-control" name="address_line" required>
            <button type="button" class="btn btn-danger" onclick="removeAddress(this)">Delete</button>
        `;
        container.appendChild(div);
    }

    function removeAddress(button) {
        button.parentElement.remove();
    }
    </script>
</body>
</html>
