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
<script>
</script>
<div id="wrapper">
    <%@ include file="part/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column justify-content-top">
        <div class="container" style="margin-top: 30px;">
            <h3>Passenger Form</h3>
        </div>
        <div class="container">
            <div style="margin-top: 50px">
                <f:form action="savePassenger" modelAttribute="passenger">
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Passenger ID</div>
                        <f:input class="form-control"  id="airlineId" readonly="true" path="passengerId" value="${passenger.getPassengerId()}" />
                        <div id="airlineIdError" class="form-text"><f:errors path="passengerId"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">First Name</div>
                        <f:input class="form-control"  id="airlineCode" path="firstName" value="${passenger.getFirstName()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="firstName"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Last Name</div>
                        <f:input class="form-control"  id="airlineCode" path="lastName" value="${passenger.getLastName()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="lastName"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Email</div>
                        <f:input class="form-control"  id="email" path="email" value="${passenger.getEmail()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="email"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Mobile Number</div>
                        <f:input class="form-control"  id="mobileNumber" path="mobileNumber" value="${passenger.getMobileNumber()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="mobileNumber"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Date of Birth</div>
                        <f:input class="form-control"  id="birthDate" path="birthDate" value="${passenger.getBirthDate()}" type="date"/>
                        <div id="airlineCodeError" class="form-text"><f:errors path="birthDate"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3 justify-content-between">
                        <div  >Gender</div>
                        <c:forEach items="${genders}" var="gender">
                        <c:if test="${passengerGender.equals(gender.toString())}">
                         <f:radiobutton  path="gender" label="${gender}" value="${gender}" checked="checked"/>
                         </c:if>
                        <c:if test="${!passengerGender.equals(gender.toString())}">
                         <f:radiobutton  path="gender" label="${gender}" value="${gender}"/>
                         </c:if>
                        <div ><f:errors path="gender"  cssStyle="color:red;"></f:errors></div>
                        </c:forEach>
                    </div>

                    <div class="input-group mb-0 justify-content-between">
                        <div >ID Type</div>
                        <c:forEach items="${identificationTypes}" var="id">
                        <c:if test="${passengerIdType.equals(id.name())}">
                        <f:radiobutton path="identificationType" label="${id.name()}" value="${id.name()}" checked="checked"/>
                        </c:if>
                        <c:if test="${!passengerIdType.equals(id.name())}">
                        <f:radiobutton path="identificationType" label="${id.name()}" value="${id.name()}"/>
                        </c:if>
                        </c:forEach>

                        <div ><f:errors path="identificationType"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <tr>
                    <td><button type="submit" class="btn btn-outline-primary">Submit</button></td>

                    </tr>
                </f:form>
            </div>
        </div>
        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>
                        <form action="togglePassengerId" method="post">
                            <input type="submit" value="Passenger ID" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        <form action="togglePassengerName" method="post">
                            <input type="submit" value="Last Name" class="table-head"/>
                        </form>
                    </th>
                    <th>First Name</th><th>Actions</th>
                </tr>
                </thead>
            <tbody>
            <c:forEach items="${passengers}" var="passenger">
            <tr>
               <td>${passenger.getPassengerId()}</td>
                    <td>${passenger.getFirstName()}</td>
                    <td>${passenger.getLastName()}</td>
                    <td>
                    <a href="deletePassenger?passengerId=${passenger.getPassengerId()}">Delete</a>
                    <a href="updatePassenger?passengerId=${passenger.getPassengerId()}">Update</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
            </table>
        </div>
        <div style="text-align: center">
            <p style="display: inline-block">Go to page:</p>
                <c:set var="totalPages" value="${totalPages}"></c:set>
                <c:set var="pageSize" value="${passengerPageInfo.getPageSize()}"></c:set>
                <c:set var="pageNo" value="${passengerPageInfo.getPageNo()}"></c:set>
                <c:set var="pages" value="${passengerPageInfo.getPages()}"></c:set>

                <f:form action="setPassengerPageNo" modelAttribute="passengerPageInfo" method="get">
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
