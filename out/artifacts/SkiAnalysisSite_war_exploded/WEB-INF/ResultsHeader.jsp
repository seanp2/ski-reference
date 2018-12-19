<%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 8/13/18
  Time: 7:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${param.pageTitle}</title>

</head>
<style>
    a.button {
        -webkit-appearance: button;
        -moz-appearance: button;
        appearance: button;

        text-decoration: none;
        color: initial;
    }
</style>
<body align = "middle" bgcolor = #55ACEE  >
<div align="left">
    <form action="${pageContext.request.contextPath}/">
        <input type="submit" value="Home" />
    </form>


<form action="ResultSearch" method="get">
    Race ID: <input type = "text" name = "raceid">
    Event: <select required name = "event">
            <option value = "">Select Event</option>
            <option name = "event" value = "SL">SL</option>
            <option name = "event" value = "GS">GS</option>
            <option name = "event" value = "SG">SG</option>
            <option name = "event" value = "DH">DH</option>
    </select>

    <input type = "submit">

    <%--Event: <input type = "radio" name = "event" Value = "DH"> DH--%>
    <%--<input type = "radio" name = "event" Value = "SG"> SG--%>
    <%--<input type = "radio" name = "event" Value = "GS"> GS--%>
    <%--<input type = "radio" name = "event" Value = "SL"> SL--%>
</form>
</div>

