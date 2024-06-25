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
            src="../${pageContext.request.contextPath}/js/jquery-3.7.1.min.js" type="application/javascript"></script>
    <link href="../${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet" >
    <script src="../${pageContext.request.contextPath}/js/bootstrap.bundle.min.js" type="application/javascript"></script>
    <link rel="stylesheet" href="../${pageContext.request.contextPath}/css/fontawesome-free/css/font-awesome.css" type="text/css">
    <link href="../${pageContext.request.contextPath}/css/admin.css" rel="stylesheet" type="text/css">
    <title>ACME TRAVEL</title>
</head>
<body>
<div id="wrapper">
    <%@ include file="part/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column  ">
        <div class="col justify-content-center">
<%--            <f:form class="col" modelAttribute="search">--%>

                    <f:form modelAttribute="search" method="post" action="searchTravel">
                <div class="card">
                    <div class="card-body">
                    <div class="row " style="margin-top: 50px; margin-bottom: 25px">
                    <div class="col">
                        <h3>All in One Travel Center</h3>
                    </div>
                </div>
                <div class="form-row ">
                    <div class="col">
                        <f:input type="text" class="form-control" path="origin" placeholder="Departure City"  value="${search.getOrigin()}" id="departureCity"/>
                        <label for="departureCity" >Depart</label>
                        <div id="departureCodeError" class="form-text"><f:errors path="origin"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="col">
                        <f:input type="text" class="form-control" path="destination" value="${search.getDestination()}" placeholder="Arrival City" id="arrivalCity"/>
                        <label for="arrivalCity">Arrive</label>
                        <div id="destinationCodeError" class="form-text"><f:errors path="destination"  cssStyle="color:red;"></f:errors></div>
                    </div>
                </div>
                <div class="form-row " style="margin-top: 25px; margin-bottom: 25px">
                    <div class="col-3 ">
                        <div class="form-check">
                            <f:radiobutton id="customRadio1" path="isRoundTrip" name="customRadio" class="form-check-input" value="true"/>
                            <label class="form-check-label" for="customRadio1">Round Trip</label>
                        </div>
                    </div>
                    <div class="col-3 ">
                        <div class="form-check">
                            <f:radiobutton id="customRadio2" path="isRoundTrip" name="customRadio" class="form-check-input" value="false"/>
                            <label class="form-check-label" for="customRadio2">One Way</label>
                        </div>
                    </div>


                    <div class="col">
                        <f:input type="number" class="form-control" path="numTravelers" value="${search.getNumTravelers()}" id="numTravelers"/>
                        <div id="returnDateCodeError" class="form-text"><f:errors path="numTravelers"  cssStyle="color:red;"></f:errors></div>
                        <label for="numTravelers" >Number of Travelers</label>
                    </div>
                </div>
                <div class="form-row" >
                    <div class="col">
                        <f:input type="date" path="departureDate" class="form-control" id="departureDate" value="${search.getDepartureDate()}"  placeholder="${now}"/>
                        <label for="departureDate">Departure Date</label>
                        <div id="departureDateCodeError" class="form-text"><f:errors path="departureDate"  cssStyle="color:red;"></f:errors></div>
                    </div>
                    <div class="col">
                        <f:input type="date" path="arrivalDate" class="form-control" id="returnDate" value="${search.getArrivalDate()}"  placeholder="Return Date"/>
                        <label for="returnDate">Return Date</label>
                        <div id="returnDateCodeError" class="form-text"><f:errors path="arrivalDate"  cssStyle="color:red;"></f:errors></div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col">
                        <input class="btn btn-primary" type="submit" id="searchSubmit" value="SEARCH">
                    </div>
                </div>
                    </div>
                </div>
            </f:form>

            <f:form modelAttribute="search" action="bookTravel" method="post">
            <c:choose>
            <c:when test="${!empty departureFlights}">
                <div >
                    <div class="row " style="margin-top: 5px; margin-bottom: 5px">
                        <div class="col-3">
                            <h6>Outbound</h6>
                        </div>
                    </div>


                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Airline</th><th>Departure City</th><th>Arrival City</th><th>Departure Time</th><th>Ticket Price</th><th>Select Flight</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${departureFlights}" var="flight">
                        <tr>
                            <td>${flight.getOperatingAirlines().getAirlineCode()}</td>
                            <td>${flight.getDepartureCity()}</td>
                            <td>${flight.getArrivalCity()}</td>
                            <td>${flight.getDepartureTime()}</td>
                            <td>${flight.getTicketPrice()}</td>
                        <td>

                        <sec:authorize access="isAuthenticated()">

                        <div class="form-row">
                        <f:checkbox path="departureFlight" action="saveDepartureFlight" id="saveDepartureFlight" value="${flight}"/>
<%--                        <input class="form-check-input" type="checkbox" value="${flight.getFlightId()}" id="saveDepartureFlight">--%>
                          <label class="form-check-label" for="saveDepartureFlight">
                          </label>
                          </div>

<%--                        --%>

<%--                    <a  href="${pageContext.request.contextPath}/bookTravel?">Book</a>--%>
                        </sec:authorize>

                        <sec:authorize access="isAnonymous()">
                            Login to Book
                        </sec:authorize>
<%--                    <a href="updateFlight?flightId=${flight.getFlightId()}">Update</a>--%>
                        </td>
                         </tr>
                        </c:forEach>

                        </tbody>
                        </table>

            </c:when>
                        <c:otherwise>

                        </c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${!empty arrivalFlights}">
                <div class="row " style="margin-top: 5px; margin-bottom: 5px">
                    <div class="col-3">
                        <h6>Inbound</h6>
                    </div>
                </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Airline</th><th>Departure City</th><th>Arrival City</th><th>Departure Time</th><th>Ticket Price</th><th>Select Flight</th>
                    </tr>
                </thead>
            <tbody>
            <c:forEach items="${arrivalFlights}" var="flight1">
            <tr>
            <td>${flight1.getOperatingAirlines().getAirlineCode()}</td>
            <td>${flight1.getDepartureCity()}</td>
            <td>${flight1.getArrivalCity()}</td>
            <td>${flight1.getDepartureTime()}</td>
            <td>${flight1.getTicketPrice()}0</td>
            <td>
            <sec:authorize access="isAuthenticated()">


            <div class="form-row">
                        <f:checkbox path="arrivalFlight" id="saveArrivalFlight" value="${flight1}"/>
<%--            <input class="form-check-input" type="checkbox" value="${flight1.getFlightId()}" id="saveArrivalFlight">--%>
              <label class="form-check-label" for="saveArrivalFlight">

              </label>
            </div>
                        </sec:authorize>
                        </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                        </table>

                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                    </c:choose>
                    <c:choose>
                    <c:when test="${!empty departureFlights || !empty arrivalFlights}">
                    <sec:authorize access="isAnonymous()">
                        Login to Book
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                    <div class="form-row justify-content-center" style="margin-bottom: 20px" >
                        <div class="col">
                        <input type="hidden" name="numTravelers" id="numTravelers" value="${search.getNumTravelers()}">
                            <button class="btn btn-primary" type="submit"  id="bookTravelBtn" value="Book" >Book</button>
<%--                    <f:input path="search" action="bookTravel" type="submit" value="BOOK"/>--%>
<%--                            <a class="btn btn-primary" type="submit" href="${pageContext.request.contextPath}/travel/bookTravel?numTravelers=${search.getNumTravelers()}">BOOK</a>--%>
                        </div>
                    </div>
                  </sec:authorize>
                    </c:when>
                     <c:otherwise>

                    </c:otherwise>
                   </c:choose>
                    </f:form>
                </div>
            </div>
<%--            </f:form>--%>
            </div>
        </div>


</body>
</html>
