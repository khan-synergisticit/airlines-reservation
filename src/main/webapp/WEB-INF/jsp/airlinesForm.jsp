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

<div id="wrapper">
    <%@ include file="part/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column justify-content-top">
        <div class="col justify-content-center">
            <div class="container" style="margin-top: 30px;">
            <h3>Airlines Form</h3>
        </div>
        <div class="container">
            <div style="margin-top: 50px; margin-bottom: 50px">
                <f:form action="saveAirlines" modelAttribute="airlines">
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Airline ID</div>
                        <f:input class="form-control"  id="airlineId" readonly="true" path="airlineId" value="${airlines.getAirlineId()}" />
                        <div id="airlineIdError" class="form-text"><f:errors path="airlineId"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Airline Code</div>
                        <f:input class="form-control"  id="airlineCode" path="airlineCode" value="${airlines.getAirlineCode()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="airlineCode"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Airline Name</div>
                        <f:input class="form-control"  id="airlineName" path="airlineName" value="${airlines.getAirlineName()}" />
                        <div id="airlineNameError" class="form-text"><f:errors path="airlineName"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <button type="submit" class="btn btn-outline-primary">Submit</button>
                </f:form>
            </div>
        </div>
            <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>
                        <form action="toggleAirlinesId" method="post">
                            <input type="submit" value="Airline ID" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        <form action="toggleAirlinesCode" method="post">
                            <input type="submit" value="Airline Code" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        <form action="toggleAirlinesName" method="post">
                            <input type="submit" value="Airline Name" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        Actions
                    </th>
                </tr>
                </thead>
            <tbody>
            <c:forEach items="${allAirlines}" var="airlines">
            <tr>
                <td>${airlines.getAirlineId()}</td>
                <td>${airlines.getAirlineCode()}</td>
                <td>${airlines.getAirlineName()}</td>
                <td><a href="deleteAirlines?accountId=${airlines.getAirlineId()}">Delete</a>
                <a href="updateAirlines?airlineId=${airlines.getAirlineId()}">Update</a>
                </td>
            </tr>
            </c:forEach>
            </tbody>
            </table>
        </div>
            <div style="text-align: center">
            <p style="display: inline-block">Go to page:</p>
                <c:set var="totalPages" value="${totalPages}"></c:set>
                <c:set var="pageSize" value="${airlinesPageInfo.getPageSize()}"></c:set>
                <c:set var="pageNo" value="${airlinesPageInfo.getPageNo()}"></c:set>
                <c:set var="pages" value="${airlinesPageInfo.getPages()}"></c:set>
                <f:form action="setAirlinesPageNo" modelAttribute="airlinesPageInfo" method="get">
                <f:select path="pageNo"  >
                <c:forEach begin="1" end="${totalPages}" var="page">
                <f:option value="${page}">${page}</f:option>
                </c:forEach>
                </f:select>
                <input type="submit" value="Go">
                </f:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
