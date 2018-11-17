
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>FIS Alpine Stats, Graphs, and More</title>

</head>
<body bgcolor="#EFF2F4">
<style>
    .welcomeBlurb {
        font-size: larger;
        font-weight: bold;
    }
</style>
<div align="center">
    <img src="sean.jpg" height="300" style="border-radius:2px">
    <div class = welcomeBlurb>
        Search for any FIS alpine race to see stats, graphs, and which athletes scored.
        <br>
        Copy and paste the url of the FIS result page from the
        <a href="https://data.fis-ski.com/alpine-skiing/results.html" target="_blank">
            FIS Website
        </a>
    </div>
    <%@ include file="ResultSearch.jsp" %>
    *Race not found. Make sure to enter the correct discipline and result URL from the FIS website.
</div>
</body>
</html>