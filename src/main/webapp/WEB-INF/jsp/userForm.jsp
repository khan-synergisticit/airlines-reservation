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
<body id="page-top" style="background-color: #F5F5F5">
<style>
    .check-box{
        margin-left: 15px;
        margin-right: 25px;
        padding: 0;
        border: 0;
    }
</style>
<script>

</script>
<div id="wrapper">

    <%@ include file="part/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column justify-content-top">
        <div class="container" style="margin-top: 30px;">
            <h3>User Form</h3>
        </div>
        <div class="container">
            <div style="margin-top: 50px; margin-bottom: 35px">
                <f:form action="saveUser" modelAttribute="user" method="post">
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">User ID</div>
                        <f:input class="form-control"  id="userId" readonly="true" path="userId" value="${user.getUserId()}" />
                        <div id="airlineIdError" class="form-text"><f:errors path="userId"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">User Name</div>
                        <f:input class="form-control"  id="username" path="username" value="${user.getUsername()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="username"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Password</div>
                        <f:input class="form-control"  id="password" path="password" value="${user.getPassword()}" type="password"/>
                        <div id="airlineCodeError" class="form-text"><f:errors path="password"  cssStyle="color:red;"></f:errors></div>
                    </div><div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Password</div>
                        <f:input class="form-control"  id="password" path="confirmPassword" value="${user.getPassword()}" type="password"/>
                        <div id="airlineCodeError" class="form-text"><f:errors path="confirmPassword"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Email</div>
                        <f:input class="form-control"  id="email" path="email" value="${user.getEmail()}" type="email"/>
                        <div id="airlineCodeError" class="form-text"><f:errors path="email"  cssStyle="color:red;"></f:errors></div>
                    </div>

                    <div class="input-group mb-3 justify-content-between">

                        <div >User Roles</div>
                            <sec:authorize access="hasAnyAuthority('ADMIN')">
                            <c:forEach items="${roles }" var="role">
                                <c:if test="${retrievedRoles.contains(role)}">
                                    <f:checkbox  path="roles" label="${role.getRoleName() }" value="${role.getRoleId() }" checked="true"/>
                                </c:if>
                                <c:if test="${!retrievedRoles.contains(role)}">
                                    <f:checkbox path="roles" label="${role.getRoleName() }" value="${role.getRoleId() }"/>
                                </c:if>
                            </c:forEach>

                        <div ><f:errors path="roles"  cssStyle="color:red;"></f:errors></div>
                        </sec:authorize>
                    </div>
                    <tr>
                        <td><button type="submit" class="btn btn-outline-primary" id="saveUserBtn">Submit</button></td>
                    </tr>
                </f:form>
            </div>
        </div>
        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>
                        <form action="toggleUserId" method="post">
                            <input type="submit" value="User ID" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        <form action="toggleUsername" method="post">
                            <input type="submit" value="User Name" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        Roles
                    </th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.getUserId()}</td>
                        <td>${user.getUsername()}</td>
                        <td>
                        <c:forEach items="${user.getRoles()}" var="role">
                        ${role.getRoleName()}
                        </c:forEach>
                        </td>
                        <td>
                             <a href="updateUser?userId=${user.getUserId()}">Update</a>
                            <sec:authorize access="hasAnyAuthority('ADMIN')">
                            <a href="deleteUser?userId=${user.getUserId()}">Delete</a>

                            </sec:authorize>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div style="text-align: center">
            <p style="display: inline-block">Go to page:</p>
            <c:set var="totalPages" value="${totalPages}"></c:set>
            <c:set var="pageSize" value="${userPageInfo.getPageSize()}"></c:set>
            <c:set var="pageNo" value="${userPageInfo.getPageNo()}"></c:set>
            <c:set var="pages" value="${userPageInfo.getPages()}"></c:set>
            <f:form action="setUserPageNo" modelAttribute="userPageInfo" method="get">
                <f:select path="pageNo"  >
                    <c:forEach begin="1" end="${totalPages}" var="page">

                        <f:option value="${page}">${page}</f:option>
                    </c:forEach>
                </f:select>
                <input type="submit" value="Go">
            </f:form>
        </div>
    </div>
</body>
</html>
