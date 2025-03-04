<%
    if (session.getAttribute("name") == null) {
        response.sendRedirect("login.jsp");
    }
    String role = (String) session.getAttribute("roles");
    String name = (String) session.getAttribute("name");
    //response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	//response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	//response.setDateHeader("Expires", 0); // Proxies.
    
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    
    <!-- Font Awesome Icons -->
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
    
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Custom CSS -->
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <i class="fas fa-user-shield"></i> Admin Dashboard
        </a>

        <div class="d-flex align-items-center">
            <span class="badge bg-primary me-3">
                <i class="fas fa-user"></i> <%= name %>
            </span>
            <span class="badge bg-warning me-3">
                <i class="fas fa-user-tag"></i> Role: <%= role %>
            </span>
         		 <a href="${pageContext.request.contextPath}/showUser" class="btn btn-success">
   					 <i class="fas fa-users"></i> Show Users		
				</a>&nbsp;
            <a href="adminadduserpage.jsp" class="btn btn-success">
                <i class="fas fa-user-plus"></i> Add Users
            </a>&nbsp;
           
            
             
             <form action="logout" method="get"><a  href="logout" class="btn btn-danger me-2">
                <i class="fas fa-sign-out-alt"></i> Logout <%  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    	    	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    	    	response.setDateHeader("Expires", 0); // Proxies.
    	         %>
            </a></form>
              
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mt-5">
    <h2 class="text-center mb-4">Welcome, <%= name %>!</h2>
    <p class="text-center text-muted">Manage users, view reports, and configure settings here.</p>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
