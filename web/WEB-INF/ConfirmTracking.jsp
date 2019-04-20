<%@ page import="com.updatedb.DBconnection" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 4/16/19
  Time: 11:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tracking Confirmed</title>
</head>
<body>
<%
    DBconnection dBconnection = new DBconnection();
    Connection connection = new DBconnection().connect();
    String query = "UPDATE tracking SET confirmed = 1 WHERE confirmID = " + request.getParameter("confirmID");

    try {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();
    }


%>
Your Tracking has been confirmed
</body>
</html>
