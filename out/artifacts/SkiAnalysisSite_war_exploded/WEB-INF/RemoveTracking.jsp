<%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 4/1/19
  Time: 6:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Remove Tracking</title>
</head>
<body bgcolor="#EFF2F4">
<div align="center">
<img src="sean-2.png" height="300" style="border-radius:2px">
    <br>
    <br>
    An email will be sent stating the FIS codes of the athletes you have stopped tracking.
    <br>
    <br>
<form action="removeTracking" method="post" style="font-size: larger">
    Racers to be removed from tracking: <input style="font-size:larger;font-family:Times New Roman, Times, serif;"
                                 type = "text" id="fisIDs" name="fisIDs">
    <input type = "hidden" name = "email" id ="email" value ="<%out.print(request.getParameter("email"));%>" />
    <input type = submit value = "Remove Tracking">
</form>
</div>
</body>
</html>
