
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
    <img src="sean-2.png" height="300" style="border-radius:2px">
    <div class = welcomeBlurb>
        Search for any FIS alpine race to see stats, graphs, and which athletes scored.
        <br>
        Copy and paste the url of the FIS result page from the
        <a href="https://data.fis-ski.com/alpine-skiing/results.html" target="_blank">
            FIS Website
        </a>
    </div>
    <%@ include file="ResultSearch.jsp" %>
    *Race not found. Make sure to enter the correct result URL from the FIS website.
</div>
<div  style="bottom:10px;position:absolute;">
    <div align="center">
        Developed by Sean Pomerantz. Email: pomerantz.s@husky.neu.edu
    </div>
</div>
</body>
</html>