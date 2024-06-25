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
            <h3>Flights Form</h3>
        </div>
        <div class="container">
            <div style="margin-top: 40px; margin-bottom: 40px">
                <f:form action="saveFlight" modelAttribute="flight">
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Flight ID</div>
                        <f:input class="form-control"  id="flightId" readonly="true" path="flightId" value="${flight.getFlightId()}" />
                        <div id="airlineIdError" class="form-text"><f:errors path="flightId"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Flight Number</div>
                        <f:input class="form-control"  id="flightNumber" path="flightNumber" value="${flight.getFlightNumber()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="flightNumber"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Airline Code</div>
                        <f:input class="form-control"  id="operatingAirlines" path="operatingAirlines.airlineCode" value="${flight.getOperatingAirlines().getAirlineCode()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="operatingAirlines.airlineCode"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Arrival City</div>
                        <f:input class="form-control"  id="arrivalCity" path="arrivalCity" value="${flight.getArrivalCity()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="arrivalCity"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Departure City</div>
                        <f:input class="form-control"  id="departureCity" path="departureCity" value="${flight.getDepartureCity()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="departureCity"  cssStyle="color:red;"></f:errors></div>
                    </div>

                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Departure Date</div>
                        <f:input class="form-control"  id="departureDate" path="departureDate" value="${flight.getDepartureDate()}" type="date"/>
                        <div id="airlineCodeError" class="form-text"><f:errors path="departureDate"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Departure Time</div>
                        <f:input class="form-control"  id="departureTime" path="departureTime" value="${flight.getDepartureTime()}" type="time"/>
                        <div id="airlineCodeError" class="form-text"><f:errors path="departureTime"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Ticket Price</div>
                        <f:input class="form-control"  id="ticketPrice" path="ticketPrice" value="${flight.getTicketPrice()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="ticketPrice"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Capacity</div>
                        <f:input class="form-control"  id="capacity" path="capacity" value="${flight.getCapacity()}" />
                        <div id="airlineCodeError" class="form-text"><f:errors path="capacity"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Amount Booked</div>
                        <f:input class="form-control"  id="booked" path="booked" value="${flight.getBooked()}"  />
                        <div id="airlineCodeError" class="form-text"><f:errors path="booked"  cssStyle="color:red;"></f:errors></div>
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
                        <form action="toggleFlightId" method="post">
                            <input type="submit" value="Flight ID" class="table-head"/>
                        </form>
                    </th>
                    <th>
                        <form action="toggleFlightNumber" method="post">
                            <input type="submit" value="Airline Code" class="table-head"/>
                        </form>
                    </th>
                    <th>Airline</th><th>Departure City</th><th>Arrival City</th><th>Amount Booked</th><th>Action</th>
                </tr>
                </thead>
            <tbody>
            <c:forEach items="${flights}" var="flight">
            <tr>
                    <td>${flight.getFlightId()}</td>
                    <td>${flight.getFlightNumber()}</td>
                    <td>${flight.getOperatingAirlines().getAirlineCode()}</td>
                    <td>${flight.getDepartureCity()}</td>
                    <td>${flight.getArrivalCity()}</td>
                    <td>${flight.getBooked()}</td>
                    <td>
                    <a href="deleteFlight?flightId=${flight.getFlightId()}">Delete</a>
                    <a href="updateFlight?flightId=${flight.getFlightId()}">Update</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
            </table>
        </div>
        <div style="text-align: center">
            <p style="display: inline-block">Go to page:</p>
                <c:set var="totalPages" value="${totalPages}"></c:set>
                <c:set var="pageSize" value="${flightPageInfo.getPageSize()}"></c:set>
                <c:set var="pageNo" value="${flightPageInfo.getPageNo()}"></c:set>
                <c:set var="pages" value="${flightPageInfo.getPages()}"></c:set>

                <f:form action="setFlightsPageNo" modelAttribute="flightPageInfo" method="get">
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
