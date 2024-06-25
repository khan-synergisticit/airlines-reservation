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
            rel="stylesheet">
    <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" >
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js" type="application/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome-free/css/font-awesome.css" type="text/css">
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet" type="text/css">
    <title>ACME TRAVEL</title>
</head>
<script>
</script>
<body>
<div id="wrapper" >
    <%@ include file="part/sidebar.jsp"%>
    <div id="form-group col-xs-3 needs-validation" class="container container-sm justify-content-center" style="width: 350px; margin-top: 100px">
        <h3>Signup Form</h3>
        <f:form action="saveSignup" method="POST" modelAttribute="user">

        <div class="login-form-group row">
        <label for="username">Username</label>
        <f:input path="username" type="text" name="username" class="form-control" id="username" aria-describedby="userNameHelp" placeholder="User Name"/>
         <div id="airlineIdError" class="form-text"><f:errors path="username"  cssStyle="color:red;"></f:errors></div>
        </div>
        <div class="login-form-group row">
        <label for="email">Email address</label>
        <f:input path="email" type="text" name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Email"/>
         <div id="airlineIdError" class="form-text"><f:errors path="email"  cssStyle="color:red;"></f:errors></div>
        </div>
        <div class="login-form-group row">
        <label for="confirmPassword">Password</label>
        <f:input  path="confirmPassword" type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="Password"/>
        <div id="airlineIdError" class="form-text"><f:errors path="password"  cssStyle="color:red;"></f:errors></div>
        </div>
        <div class="login-form-group row">
        <label for="exampleInputPassword1">Password</label>
        <f:input path="password" type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Confirm Password"/>
         <div id="airlineIdError" class="form-text"><f:errors path="confirmPassword"  cssStyle="color:red;"></f:errors></div>
        </div>
        <button type="submit" class="btn btn-primary" style="margin-top: 50px">Submit</button>
        </f:form>
    </div>
</div>
</body>
</html>