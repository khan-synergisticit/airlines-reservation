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
        <div class="container" style="margin-top: 30px;">
            <h3>Reservation Form</h3>
        </div>
        <div class="container">
            <div class="col" style="margin-top: 50px">
                <f:form  action="saveReservation" modelAttribute="reservation">
                    <sec:authorize access="hasAnyAuthority('STAFF', 'ADMIN')">
                        <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Ticket Number</div>
                        <f:input class="form-control"  id="reservation.ticketNumber" readonly="true" path="ticketNumber" value="${reservation.getTicketNumber()}" />
                        <div id="reservationIdError" class="form-text"><f:errors path="ticketNumber"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <c:forEach items="${reservation.getFlights()}" var="flight">
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Flight ID</div>
                        <f:input class="form-control"   path="flights" value="${flight.getFlightId()}" />
                        <div class="form-text"><f:errors path="flights"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    </c:forEach>
                    <c:forEach items="${reservation.getPassengers()}" var="passenger">
                    <div class="row">
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-text" id="btnGroupAddon">Passenger ID</div>
                        <f:input class="form-control"  id="airlineCode1" path="passengers" value="${passenger.getPassengerId()}" />
                        <div  class="form-text"><f:errors path="passengers"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3 col-6">
                        <div class="input-group-text" id="btnGroupAddon2">Last Name</div>
                        <input class="form-control"  id="airlineCode" value="${passenger.getLastName()}" />
                        <div  class="form-text"><f:errors path="passengers"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    </div>
                    </c:forEach>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Number of Checked Bags</div>
                        <f:input class="form-control"  id="checkedBags" path="checkedBags" value="${reservation.getCheckedBags()}" />
                        <div class="form-text"><f:errors path="checkedBags"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Checkin Status</div>
                        <f:input class="form-control"  id="checkedIn" path="checkedIn" value="${reservation.getCheckedIn()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="checkedIn"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <button type="submit" class="btn btn-outline-primary">Submit</button>
                    </sec:authorize>
                    <div class="row">
                    <sec:authorize access="hasAnyAuthority('CUSTOMER')">
                        <div class="input-group mb-3">
                        <label class="input-group-text" for="ticketNumber">Ticket Number</label>
                        <input class="form-control"  id="ticketNumber" readonly value="${reservation.getTicketNumber()}" />
                    </div>
                    <c:forEach items="${reservation.getFlights()}" var="flight">
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="btnGroupAddon1">Flight ID</label>
                        <input class="form-control" id="btnGroupAddon1"   value="${flight.getFlightId()}" readonly />
                    </div>
                    </c:forEach>
                    <c:forEach items="${reservation.getPassengers()}" var="passenger">
                    <div class="row">
                    <div class="input-group mb-3 col">
                        <label class="input-group-text" for="btnGroupAddon4">Passenger ID</label>
                        <input class="form-control"  id="btnGroupAddon4" readonly value="${passenger.getPassengerId()}" />
                    </div>
                    <div class="input-group mb-3 col">
                        <label class="input-group-text" for="btnGroupAddon3">Last Name</label>
                        <input class="form-control"  id="btnGroupAddon3" readonly value="${passenger.getLastName()}" />
                    </div>
                    </div>
                    </c:forEach>
                    <div class="row">
                    <div class="input-group mb-3 col">
                        <label class="input-group-text" for="btnGroupAddon5">Number of Checked Bags</label>
                        <input class="form-control"  id="btnGroupAddon5" readonly value="${reservation.getCheckedBags()}" />
                    </div>
                    <div class="input-group mb-3 col">
                        <label class="input-group-text" for="checkedIn">Checked In</label>
                        <input class="form-control"  id="checkedIn" value="${reservation.getCheckedIn()}" readonly />
                    </div>
                    </div>
                    </div>

                    </sec:authorize>
                </f:form>
            </div>
        </div>

        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>
                        <f:form action="toggleTicketNumber" method="post">
                            <input type="submit" value="TicketNumber" class="table-head"/>
                        </f:form>
                    </th>
                    <th>
                        <f:form action="togglePassengerFlightNumber" method="post">
                            <input type="submit" value="Flight Number" class="table-head"/>
                        </f:form>
                    </th>
                    <th>Last Name</th><th>Checkin Status</th><th>Actions</th>
                </tr>
                </thead>
            <tbody>
            <c:forEach items="${reservations}" var="reservation">
            <tr>
                    <td>${reservation.getTicketNumber()}</td>

                    <td>
                    <c:forEach items="${reservation.getFlights()}" var="flight">
                    ${flight.getFlightNumber()} |
                    </c:forEach></td>

                    <td><c:forEach items="${reservation.getPassengers()}" var="passenger">
                    ${passenger.getFirstName()}
                    </c:forEach></td>
                    <td>${reservation.getCheckedIn()}</td>
                    <td>
<%--                    <a href="deleteReservation?reservationId=${reservation.getTicketNumber()}">Delete</a>--%>
                    <a href="updateReservation?reservationId=${reservation.getTicketNumber()}">Update</a>
                    <sec:authorize access="hasAnyAuthority('STAFF', 'ADMIN')">
                    <a href="checkin?reservationId=${reservation.getTicketNumber()}">Checkin</a>
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
                <c:set var="pageSize" value="${reservationPageInfo.getPageSize()}"></c:set>
                <c:set var="pageNo" value="${reservationPageInfo.getPageNo()}"></c:set>
                <c:set var="pages" value="${reservationPageInfo.getPages()}"></c:set>

                <f:form action="setReservationPageNo" modelAttribute="reservationPageInfo" method="get">
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
</body>
</html>
