<%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 4/1/19
  Time: 5:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tracking Dashboard</title>
</head>

<body bgcolor="#EFF2F4">
<div align="center">
<img src="sean-2.png" height="300" style="border-radius:2px">
<div style="width:50%">
    <div style="font-size: larger;font-weight: bold;">
        Welcome to the tracking dashboard of ski-reference.com.
    </div>
    <div align = "left">
        <br>

        &nbsp;&nbsp;&nbsp;&nbsp;Here you will be able to enter in FIS codes
        of athletes you would like to receive email notifications of completion of a FIS race. One email
        will be sent every day at least one athlete you track competes.
    </div>
    <br>
    <div style="font-size:medium;font-weight:700;" align="left">
        1. Enter in your email<br><br>
        2. Enter the FIS codes of athletes you would like to receive notifications for, separated by spaces.<br><br>
        3. A confirmation will be sent. Check your email, and follow the instructions.<br><br>
    </div>

</div>
<form action="tracking" method="post" style="font-size: larger">
    Email: <input style="font-size:larger;font-family:Times New Roman, Times, serif;"
                                 type = "text" id="email" name="email">
    Racers to be tracked: <input style="font-size:larger;font-family:Times New Roman, Times, serif;"
                       type = "text" id="fisIDs" name="fisIDs">
    <input type = submit value = "Track Racers">
</form>
</div>


</body>
</html>
