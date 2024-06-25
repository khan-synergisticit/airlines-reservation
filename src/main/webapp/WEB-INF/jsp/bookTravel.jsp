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
    <link href="../${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" >
    <script src="../${pageContext.request.contextPath}/js/bootstrap.bundle.min.js" type="application/javascript"></script>
    <link rel="stylesheet" href="../${pageContext.request.contextPath}/css/fontawesome-free/css/font-awesome.css" type="text/css">
    <link href="../${pageContext.request.contextPath}/css/admin.css" rel="stylesheet" type="text/css">
    <title>ACME TRAVEL</title>
</head>
<body >

<div id="wrapper">

    <%@ include file="part/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column justify-content-top ">
        <div class="row justify-content-center" style="margin-top: 30px;">
            <f:form modelAttribute="reservation" method="post" action="saveTravel">
            <div class="col" style="padding: 20px">
                <div class="card-body">

                        <div class="row justify-content-center" style="margin-top: 10px; margin-bottom: 25px">
                            <div class="col">
                                <h3>Travel Booking</h3>
                            </div>
                        </div>
                        <div class="form-row ">
                           <table class="table table-striped" id="flightTable" >
                               <thead>
                               <tr>
                                   <th>Flight Number</th><th>Departure City</th><th>Arrival City</th><th>Departure Date</th><th>Price</th>
                               </tr>
                               </thead>
                               <tbody >
                               <c:forEach items="${flights}" var="flight">
                               <tr>
                               <td id="flightNumber">${flight.getFlightNumber()}</td><td>${flight.getDepartureCity()}</td><td>${flight.getArrivalCity()}</td><td>${flight.getDepartureDate()}</td><td>${flight.getTicketPrice()}</td>
                                </tr>
                                </c:forEach>
                               </tbody>
                           </table>
                        </div>
                </div>



                <c:forEach items="${passengers}" var="passenger" varStatus="pStatus">
                <div class="container-xl">
                <div class="form-row" >
                    <div class="form-group col-md-6">
                      <label for="firstName">First Name</label>
                      <f:input path="passengers[${pStatus.index}].firstName"  type="text" class="form-control" id="firstName" name="firstName" placeholder="First Name"/>
                    </div>
                    <div class="form-group col-md-6">
                      <label for="lastName">Last Name</label>
                      <f:input path="passengers[${pStatus.index}].lastName"  type="text" class="form-control" id="lastName" name="lastName" placeholder="Last Name"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="email">Email</label>
                    <f:input path="passengers[${pStatus.index}].email" type="text" class="form-control" id="email" name="email" placeholder="Email"/>
                  </div>
                  <div class="form-group">
                    <label for="mobileNumber">Mobile Number</label>
                    <f:input path="passengers[${pStatus.index}].mobileNumber" type="text" class="form-control" id="mobileNumber" name="mobileNumber" placeholder="Mobile Number"/>
                  </div>

                  <div class="form-row">
                  <div class="form-group col-md-4">
                  <label for="idType">ID Type</label>
                  <f:select id="idType" class="form-control" path="passengers[${pStatus.index}].identificationType"  name="idType">
                  <c:forEach items="${identificationTypes}" var="idType" varStatus="idStatus">

                    <f:option value="${idType}" />
                        </c:forEach>
                      </f:select>

                    </div>
                    <div class="form-group col-md-8">
                      <label for="idNumber">ID Number</label>
                      <f:input path="passengers[${pStatus.index}].idNumber" type="text" class="form-control" id="idNumber" name="idNumber"/>
                    </div>
                    </div>

                    <div class="form-row">
                    <div class="form-group col-md-3">
                    <label for="gender">Gender</label>
                    <f:select class="form-control" id="genderOptions"  path="passengers[${pStatus.index}].gender" name="gender">
                  <c:forEach items="${genders}" var="gender" varStatus="gStatus">

                    <f:option  value="${gender}"/>

                      </c:forEach>
                        </f:select>
                    </div>

                    <div class="form-group col-md-6">
                      <label for="birthDate">Date of Birth</label>
                      <f:input path="passengers[${pStatus.index}].birthDate" value="${passenger.getBirthDate()}" type="date" class="form-control" id="birthDate" name="birthDate"/>
                    </div>


                    <div class="form-group col-md-3">
                      <label for="checkedBags">Checked Bags</label>
                      <f:input path="checkedBags" type="number"  class="form-control" id="checkedBags" name="checkedBags" placeholder="Number of Bags"/>
                    </div>
                    </div>
                    </div>
                </c:forEach>

                <div class="container" style="margin-bottom: 50px">
                <table class="table">
                <thead>
                <tr>
                    <th>Total Price</th><th class="float-right">Reserve</th>
                </tr>
                </thead>
            <tbody>
                <tr>
                <td>
                ${flightCost}0
                </td>
                <td>
                    <div class="form-row">
                    <div class="col">

                    </div>
                </div>
                </td>
                </tr>

            </tbody>
            </table>
<%--             <input type="hidden" name="reservation" id="numTravelers" value="${search.getNumTravelers()}"/>--%>
             <input   type="submit" class="btn btn-primary float-right"  id="saveTravel" value="RESERVE">
            </f:form>
            </div>
        </div>
</div>
</div>
</body>
</html>
