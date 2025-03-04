<%
    if (session.getAttribute("name") == null) {
        response.sendRedirect("admin.jsp");
    }
    String role = (String) session.getAttribute("roles");
    String name = (String) session.getAttribute("name");
   
%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Admin Page</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
    <link rel="stylesheet" href="css/style.css">

    <!-- SweetAlert2 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- Bootstrap for styling (optional) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
<!-- Main css -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>

    <!-- Status for SweetAlert -->
    <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
    

    <div class="main">
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form">
                        <h2 class="form-title">Add Users</h2>

                        <form method="post" action="${pageContext.request.contextPath}/admin" class="admin" id="admin">
    <div class="form-group">
        <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
        <input type="text" name="name" id="name" placeholder="Your Name" required />
    </div>
    <div class="form-group">
        <label for="email"><i class="zmdi zmdi-email"></i></label>
        <input type="email" name="email" id="email" placeholder="Your Email" required />
    </div>
    <div class="form-group">
        <label for="contact"><i class="zmdi zmdi-phone"></i></label>
        <input type="text" name="contact" id="contact" placeholder="Contact no" required />
    </div>
    <div class="form-group">
        <label for="roles"><i class="zmdi zmdi-assignment-account"></i></label>
        <input type="text" name="roles" id="roles" placeholder="Enter your role" value="Employee" required />
    </div>

    <!-- Dynamic Address Field -->
    <div id="address-container" class="mb-3">
        <div class="address-item row mb-2">
            <div class="col-md-10">
                <input type="text" class="form-control" name="address_line" placeholder="Address Line" required>
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-success" onclick="addAddressField()">+</button>
            </div>
        </div>
    </div>

    <div class="form-group form-button">
        <input type="submit" name="signup" id="signup" class="form-submit" value="Register" />
    </div>
</form>
                    </div>

                    <div class="signup-image">
                        <figure>
                            <img src="images/signup-image.jpg" alt="sign up image">
                        </figure>
                        <a href="login.jsp" class="signup-image-link">I am already a member</a>
                          <a href="admin.jsp" class="signup-image-link">Back to admin</a>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- jQuery and Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Dynamic Address Fields & SweetAlert -->
    <script type="text/javascript">
        document.addEventListener('DOMContentLoaded', () => {
            const status = document.getElementById("status").value;
            if (status === "success") {
                Swal.fire({
                    icon: 'success',
                    title: 'User Added Successfully!',
                    text: 'The user has been successfully registered.',
                    confirmButtonText: 'OK'
                }).then(() => {
                    window.location.href = '${pageContext.request.contextPath}/admin';
                });
            } else if (status === "failed") {
                Swal.fire({
                    icon: 'error',
                    title: 'Registration Failed',
                    text: 'Please try again.',
                    confirmButtonText: 'OK'
                });
            }
        });

        // Add dynamic address field
        function addAddressField() {
            const container = document.getElementById('address-container');
            const addressItem = document.createElement('div');
            addressItem.className = 'address-item row mb-2';

            addressItem.innerHTML = `
                <div class="col-md-10">
                    <input type="text" class="form-control" name="address_line" placeholder="Address Line" required>
                </div>
                <div class="col-md-2">
                    <button type="button" class="btn btn-danger" onclick="removeAddressField(this)">-</button>
                </div>
            `;

            container.appendChild(addressItem);
        }

        // Remove dynamic address field
        function removeAddressField(button) {
            button.parentElement.parentElement.remove();
        }
    </script>
</body>
</html>
