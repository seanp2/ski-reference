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
    <title>Title</title>
</head>

<body bgcolor="#EFF2F4">
<div style="height:40%"></div>
<div align="center" style="width:100%">
    Welcome to the tracking dashboard of ski-reference.com. Here you will be able to enter in FIS ids
    of athletes you would wish to receive email notifications upon completion of a FIS race. Notifications
    will be sent on a weekly basis, and you will receive one email per week, consisting of a summary of all results
    completed by all athletes you decide to track.
</div>

<div>
    Please enter in your email, and then enter in the FIS id's of athletes you would like to track separated by spaces.
</div>
<form action="tracking" method="post" style="font-size: larger">
    Email: <input style="font-size:larger;font-family:Times New Roman, Times, serif;"
                  type = "text" id="email" name="email">
    Racers to be tracked: <input style="font-size:larger;font-family:Times New Roman, Times, serif;"
                                 type = "text" id="fisIDs" name="fisIDs">
    <input type = submit value = "Track Racers">
</form>
** Invalid email or FIS id entries. Make sure to enter a valid email, and enter valid FIS ids separated by spaces.


</body>
</html>
