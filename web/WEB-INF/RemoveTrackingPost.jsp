<%@ page import="java.util.Scanner" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tracking.CreateTracking" %>
<%@ page import="com.tracking.RemoveTracking" %><%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 4/2/19
  Time: 11:03 AM
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
    RemoveTracking removeTracking = new RemoveTracking(fisIds, email);
    ArrayList<String> untrackedRemovals = removeTracking.getUnremovedTrackings();
    removeTracking.executeRemoveTracking();
    removeTracking.sendRemovalConfirmation();
%>
<html>
<head>
    <title>Tracking Removed</title>
</head>
<body bgcolor="#EFF2F4">
<div align="center">
    <img src="sean-2.png" height="300" style="border-radius:2px">
    <br>
    <br>
<%
//    if (!untrackedRemovals.equals("")) {
//	out.print("The following trackings were not removed " +
//            "because you are not currently tracking them:<br>");
//	Scanner scanner1 = new Scanner(untrackedRemovals);
//	scanner.useDelimiter("\\s*, \\s*");
//	while(scanner.hasNext()) {
//    out.print("-" + untrackedRemovals + "<br>");
//	}

    if (untrackedRemovals.size() > 0) {


    	out.print("The following trackings were not removed " +
            "because you are not currently tracking them:<br>");

    	out.print("<div style=\"width:25%;\">");
    	out.print("<br>");
    	out.print("<div align = \"left\">");
    	for (int i = 0; i < untrackedRemovals.size(); i++) {
    		out.print("* " + untrackedRemovals.get(i) + "<br>");
    	}
    	out.print("</div>");
    	out.print("</div>");

}
%>
    <br>
    <br>
    An email confirmation has been sent stating the athletes you have stopped tracking.
</body>
</html>
