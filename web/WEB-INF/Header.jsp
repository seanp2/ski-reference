<%@ page import="com.updatedb.DBconnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>${param.pageTitle}</title>

</head>
<style>
    /* Style the header with a grey background and some padding */
    .header {
        overflow: hidden;
        background-color: #CCD6DD;
        font-weight: bold;
        padding: 10px 10px;
        border-radius: 4px;
    }

    /* Style the header links */
    .header a {
        float: left;
        color: black;
        text-align: center;
        padding: 12px;
        text-decoration: none;
        font-size: 18px;
        line-height: 25px;
        border-radius: 4px;
    }


    .header a.logo {
        font-size: 25px;
        font-weight: bold;
    }

    /* Change the background color on mouse-over */
    a:hover {
        background-color: #ddd;
        color: black;
    }


    /* Add media queries for responsiveness - when the screen is 500px wide or less, stack the links on top of each other */
    @media screen and (max-width: 500px) {
        .header a {
            float: none;
            display: block;
            text-align: left;
        }
        .header-right {
            float: none;
        }
    }
</style>
<body align = "middle" bgcolor=#EFF2F4 >
<div class="header">
    <%--<div align="left">--%>
    <%--<a href="http://www.ski-reference.com/" class="logo" >--%>
        <%--Home--%>
    <%--</a>--%>
    <%--</div>--%>
    <div align="center">
    <%@ include file="ResultSearch.jsp" %>
    </div>
</div>


