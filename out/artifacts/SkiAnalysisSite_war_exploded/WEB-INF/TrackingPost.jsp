<%@ page import="java.util.Scanner" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tracking.CreateTracking" %><%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 4/1/19
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String fisIdsCSV = request.getParameter("fisIDs");
    String email = request.getParameter("email");
    Scanner scanner = new Scanner(fisIdsCSV);
    ArrayList<Integer> fisIds = new ArrayList<>();
    while (scanner.hasNext()) {
        int next = scanner.nextInt();
        System.out.println(next);
        fisIds.add(next);
    }
    CreateTracking newTracking = new CreateTracking(fisIds, email);
    newTracking.updateTrackingDB();
    newTracking.sendConfirmTracking();

%>
<html>
<head>
    <title>Tracking</title>
</head>
<body>
An email confirmation has been sent
</body>
</html>
