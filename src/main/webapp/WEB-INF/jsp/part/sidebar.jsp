<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link
          href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet" >
    <script
            src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js" type="application/javascript"></script>
<%--    <script src="<c:url value="./src/main/resources/static/js/jquery-3.7.1.min.js"/>" type="application/javascript"></script>--%>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet" >
<%--    <link href="<c:url value="./src/main/resources/static/css/bootstrap.min.css"/>" type="text/css" rel="stylesheet" >--%>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js" type="application/javascript"></script>
<%--    <script src="<c:url value="./src/main/resources/static/js/bootstrap.bundle.min.js"/>" type="application/javascript"></script>--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome-free/css/font-awesome.css" type="text/css">
<%--    <link rel="stylesheet" href="<c:url value="./src/main/resources/static/css/fontawesome-free/css/font-awesome.css"/>" type="text/css">--%>
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet" type="text/css">
<%--    <link href="<c:url value="./src/main/resources/static/css/admin.css"/>" rel="stylesheet" type="text/css">--%>
    <title>ACME TRAVEL</title>
</head>
<script>

</script>
<body>
  <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
  <sec:authorize access="isAuthenticated()">
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/home">
      <div class="title">ACME Flight Manager</div>
    </a>
   </sec:authorize>
   <sec:authorize access="isAnonymous()">
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/home">
      <div class="title">LET's GO TRAVEL</div>
    </a>
   </sec:authorize>

    <hr class="sidebar-divider">
        <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/travel/"
         aria-expanded="true" >
        <i class="fa fa-globe" aria-hidden="true" style="font-size: 30px"></i>
        <span>Travel</span>
      </a>
    </li>
    <sec:authorize access="hasAnyAuthority('STAFF')">
      <sec:authorize access="hasAuthority('ADMIN')">
    <!-- Heading -->
    <div class="sidebar-heading">
      Interface
    </div>
    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/airlines/"
         aria-expanded="true" >
        <i class="fa fa-plane" aria-hidden="true" style="font-size: 30px"></i>
        <span>Airlines</span>
      </a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/roles"
         aria-expanded="true" >
        <i class="fa fa-plane" aria-hidden="true" style="font-size: 30px"></i>
        <span>Roles</span>
      </a>
    </li>
    </sec:authorize>
      <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/flights/"
             aria-expanded="true" >
              <i class="fa fa-location-arrow" aria-hidden="true" style="font-size: 30px"></i>
              <span>Flights</span>
          </a>
      </li>
      <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/passengers"
             aria-expanded="true" >
              <i class="fa fa-users" aria-hidden="true" style="font-size: 30px"></i>
              <span>Passengers</span>
          </a>
      </li>


    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
    <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/reservations/"
             aria-expanded="true" >
              <i class="fa fa-ticket" aria-hidden="true" style="font-size: 30px"></i>
              <span>Reservations</span>
          </a>
      </li>
    <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/users/"
             aria-expanded="true" >
              <i class="fa fa-user" aria-hidden="true" style="font-size: 30px"></i>
              <span>User</span>
          </a>

      </li>
    <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/logout"
             aria-expanded="true" >
              <i class="fa fa-arrow-right" aria-hidden="true" style="font-size: 30px"></i>
              <span>Logout</span>
          </a>
      </li>
    </sec:authorize>
    <!-- Divider -->
    <sec:authorize access="hasAuthority('CUSTOMER')">
    <hr class="sidebar-divider">
   <form class="form-inline my-2 my-lg-0" >
       <input readonly="readonly" class="form-control mr-sm-2" type="text" style="border-radius: 0; font-weight: bolder; background-color: white"  value="Customer: ${pageContext.request.userPrincipal.name}" aria-label="Search">
   </form>
   </sec:authorize>
   <sec:authorize access="hasAuthority('STAFF')">
    <hr class="sidebar-divider">
   <form class="form-inline my-2 my-lg-0" >
       <input readonly="readonly" class="form-control mr-sm-2" type="text" style="border-radius: 0; font-weight: bolder; background-color: white"  value="Staff: ${pageContext.request.userPrincipal.name}" aria-label="Search">
   </form>
   </sec:authorize>
   <sec:authorize access="hasAuthority('ADMIN')">
       <form class="form-inline my-2 my-lg-0" >
           <input readonly="readonly" class="form-control mr-sm-2" style="border-radius: 0; font-weight: bolder; background-color: white" type="text" value="Admin: ${pageContext.request.userPrincipal.name}" aria-label="Search">
       </form>
   </sec:authorize>
    <hr class="sidebar-divider">
      <sec:authorize access="isAnonymous">
      <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/login"
             aria-expanded="true" >
              <i class="fa fa-user" aria-hidden="true" style="font-size: 30px"></i>
              <span>Login</span>
          </a>
      </li>
      <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/signup"
             aria-expanded="true" >
              <i class="fa fa-user" aria-hidden="true" style="font-size: 30px"></i>
              <span>Signup</span>
          </a>
      </li>
</sec:authorize>
  </ul>
</body>
</html>