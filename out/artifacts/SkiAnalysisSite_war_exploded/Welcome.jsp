<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.updatedb.DBconnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%--<link rel="icon" href="https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-120532899825/sean_2_url_xhZ_icon.ico" type="image/x-icon"/>--%>
    <%--<link rel="shortcut icon" href="https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-120532899825/sean_2_url_xhZ_icon.ico" type="image/x-icon"/>--%>
    <%--<link rel="shortcut icon" type="image/png" href='/favicon.ico'/>--%>
    <%--<link rel="shortcut icon" type="image/png" href="https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-120532899825/sean_2_url_xhZ_icon.ico"/>--%>
    <%--<link rel="shortcut icon" href="https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-120532899825/sean_2_url_xhZ_icon.ico" />--%>
    <%--<link rel="shortcut icon" href="/favicon.ico">--%>
    <%--<link rel="apple-touch-icon" href="/favicon.ico">--%>
    <link rel="shortcut icon" type="image/png" href="/favicon.png"/>



    <title>FIS Alpine Stats, Graphs, and More</title>

</head>
<body bgcolor="#EFF2F4">
<style>
    .welcomeBlurb {
        font-size: larger;
        font-weight: bold;
    }
</style>
<%
    try {
        Connection connection = new DBconnection().connect();
        Statement statement = connection.createStatement();
        String query =  "SET SQL_SAFE_UPDATES = 0;" +
                "UPDATE VisitorTrack SET  HomeVisits = HomeVisits + 1  WHERE ID=0;";
        statement.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
<div align="center">
    <img src="sean-2.png" height="300" style="border-radius:2px">
    <div class = welcomeBlurb>
        Search for any FIS alpine race to see stats, graphs, and which athletes scored.
        <br>
        Copy and paste the url of the FIS result page from the
        <a href="https://data.fis-ski.com/alpine-skiing/results.html" target="_blank">
            FIS Website
        </a>
    </div>
<%@ include file="WEB-INF/ResultSearch.jsp" %>
</div>
<div  style="bottom:10px;position:absolute;">
<div align="center">
    Developed by Sean Pomerantz. Email: pomerantz.s@husky.neu.edu
</div>
</div>
</body>
</html>


