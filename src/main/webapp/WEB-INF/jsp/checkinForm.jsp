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
<body>
<div id="wrapper">
    <%@ include file="part/sidebar.jsp"%>
        <div id="content-wrapper" class="d-flex flex-column justify-content-top">
            <div class="container" style="margin-top: 30px;">
                <h3>Checkin</h3>
            </div>
            <div class="card" style="width: 800px">
                <div class="card-body">
                    <f:form  action="saveCheckin" modelAttribute="reservation" method="post" >
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Ticket Number</div>
                        <f:input class="form-control"  id="reservation.ticketNumber" readonly="true" path="ticketNumber" value="${reservation.getTicketNumber()}" />
                        <div id="reservationIdError" class="form-text"><f:errors path="ticketNumber"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <c:forEach items="${reservation.getFlights()}" var="flight">
                    <div class="input-group mb-3">
                        <div class="input-group-text" id="btnGroupAddon">Flight ID</div>
                        <f:input class="form-control" readonly="true"  path="flights" value="${flight.getFlightId()}" />
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
                        <div class="input-group-text" id="btnGroupAddon" style="margin-right: 10px">Checkin</div>
                        <c:if test="${reservation.getCheckedIn()}" >
                        <f:checkbox path="checkedIn" value="Checkin" checked="true"/>
                        </c:if>
                        <c:if test="${!reservation.getCheckedIn()}" >
                        <f:checkbox path="checkedIn" value="Checkin" checked=""/>
                        </c:if>

                        <div id="airlineCodeError" class="form-text"><f:errors path="checkedIn"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <button type="submit" class="btn btn-outline-primary">Submit</button>
                </f:form>
                </div>
            </div>
        </div>
</div>

</body>
</html>
