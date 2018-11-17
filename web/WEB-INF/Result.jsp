<%@ page import="java.io.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.results.*" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="com.updatedb.DBconnection" %>
<%@ page import="javax.swing.plaf.nimbus.State" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: seanpomerantz
  Date: 8/13/18
  Time: 4:57 PM
  To change this template use File | Settings | File Templates. <%@ include file="ResultsHeader.jsp"%>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>





<%

    try {
        Connection connection = new DBconnection().connect();
        Statement statement = connection.createStatement();
        String query = "SET SQL_SAFE_UPDATES = 0;" +
                "UPDATE VisitorTrack SET  ResultVisits = ResultVisits + 1  WHERE ID= 0;";
        statement.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    String hostname = "www.ski-reference.com/";
    String raceID = request.getParameter("raceid");
    String eventType = request.getParameter("event");
    String filter = request.getParameter("filter");
    String graph = request.getParameter("graph");
    if (filter == null) {
    	filter = "none";
    }
    if (graph == null) {
    	graph = "bib_vs_rank";
    }
    Race race = null;
    ArrayList<RaceAthlete> scorers = null;
    try {
        if (eventType.equals("GS") || eventType.equals("SL")) {
            race = new TechRace("https://data.fis-ski." +
                    "com/dynamic/results.html?sector=AL&raceid=" + raceID, eventType); // ** event is off
        } else {
            race = new SpeedRace("https://data.fis-ski." +
                    "com/dynamic/results.html?sector=AL&raceid=" + raceID, eventType);
        }
        scorers = race.getScorers();
    } catch(Exception e) {
    	e.printStackTrace();
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Error.jsp");
    	rd.include(request,response);
    }
    ArrayList<Integer> scoringIndices = new ArrayList<>();
    for (int k = 0; k < race.getResults().size(); k ++) {
    	if (scorers.contains(race.getResults().get(k))) {
            scoringIndices.add(k);
        }
    }

    ArrayList<Integer> dnfIndices = new ArrayList<>();
    for(int i = 0; i < race.getResults().size(); i++) {
    	if (race.getResults().get(i).getResult() instanceof DNF) {
    		dnfIndices.add(i);
        }
    }





    String title =    race.getVenue() +": " +race.getDate();
%>
<jsp:include page="Header.jsp" >
    <jsp:param name="pageTitle" value= "<%= title %>"/>
</jsp:include>
<style>
    .chart {
        width: 80%;
        height: 500px;
        margin: 0 auto;
    }
     <%
     String noFilterColor;
     String scorersFilterColor;
     if (filter.equals( "none")) {
         noFilterColor = "#FFFFFF";
         scorersFilterColor = "#CCD6DD";
     }  else {
         noFilterColor = "#CCD6DD";
         scorersFilterColor = "#FFFFFF";
     }
     %>


    .filterSelector {
        height:35px;
        overflow:hidden;
        margin: 0 auto;
        color: black;
        font-size: larger;
        text-decoration: none;

    }
         /* Style the header with a grey background and some padding */
     .nofilterSelector {
         background-color: <%
        out.print(noFilterColor);
        %>;
         color: black;
         text-decoration: none;
         width: 30px;
         padding: 20px 10px;
         text-decoration: none;

     }
    .scorersSelector {
        background-color: <%
        out.print(scorersFilterColor);
        %>;
        color: black;
        text-decoration: none;
        width: 100px;
        padding: 20px 10px;
        text-decoration: none;
    }

    .graphSelector {
        width: 80%;
        height:30px;
        overflow:hidden;
        margin: 0 auto;
        color: black;
        font-size: large;
        text-decoration: none;


    }

    <%
    String bibrankColor;
    String rankscoreColor;
    if (graph.equals( "bib_vs_rank")) {
        bibrankColor = "#FFFFFF";
        rankscoreColor = "#CCD6DD";
    }  else {
        bibrankColor = "#CCD6DD";
        rankscoreColor = "#FFFFFF";
    }
%>

    .bibrankSelector {
        background-color: <%
        out.print(bibrankColor);
        %>;
        color: black;
        text-decoration: none;
        width: 100px;
        padding: 20px 10px;


    }
    .rankscoreSelector {
        background-color: <%
        out.print(rankscoreColor);
        %>;
        color: black;
        text-decoration: none;
        width: 100px;
        padding: 20px 10px;

    }

    .wrapper {
        position: relative;
    }

    /*
    *  The .navi properties are for styling only
    *  These properties can be changed or removed
    */
    .navi {
        background-color: #eaeaea;
        height: 40px;
    }


    /*
    *  Position the #infoi element in the top-right
    *  of the .wrapper element
    */
    #infoi {
        position: absolute;
        top:0px;
        right:10%;
        font-size: smaller;

        /*
        *  Styling only, the below can be changed or removed
        *  depending on your use case
        */
        height: 20px;
        padding: 10px 10px;
    }
</style>
<div style="height: 10px;"></div>
<div align="center" style="height:100px;">
<%
    out.print("<div style=\"font-size:xx-large;\">\n");
    out.print(race.getVenue() +": " +race.getDate());
    out.print(" </div>\n");
    out.print("<div>Finish Rate: " + String.format("%.2f", race.getFinishRate()) + "</div>");
    out.print("<div>Number of Scorers: " + scorers.size() + "</div>");
    out.print("<div>Points Per Second: " + String.format("%.2f", race.pointsPerSecond()) + "</div>");

    %>
</div>



<div style="height: 10px"></div>

<div class = "graphSelector" align="left">

        <a class = "bibrankSelector" href=http://www.ski-reference.com/ResultSearch?filter=<%
            out.print(filter + "&raceid=" + raceID + "&event=" + eventType + "&graph=bib_vs_rank");
        %>>Bib vs. Rank
        </a><a class = "rankscoreSelector" href=http://www.ski-reference.com/ResultSearch?filter=<%
        out.print(filter + "&raceid=" + raceID + "&event=" + eventType + "&graph=rank_vs_points");
    %>>Rank vs. FIS Score</a>

</div>
<div class="wrapper">


<%
        ArrayList<Number[]> compareArray = new ArrayList<>();
        for (int i = 0; i < race.getResults().size(); i++) {
            Result theResult = race.getResults().get(i).getResult();
            if (graph.equals("bib_vs_rank")) {
                try {
                    compareArray.add(new Number[]{theResult.getBib(), Integer.parseInt(theResult.getRank())});
                } catch (NumberFormatException e) {
                    compareArray.add(new Number[]{theResult.getBib(), race.getResults().size()});
                }
            } else if (graph.equals("rank_vs_points")) {
                try {
                    compareArray.add(new Number[]{Integer.parseInt(theResult.getRank()), theResult.getScore()});
                } catch(NumberFormatException e) {
//                    compareArray.add(new Number[]{race.getResults().size(), theResult.getScore()});
                }
            }
        }
        String xAxis;
        String yAxis;
        if (graph.equals("bib_vs_rank")) {
        	xAxis = "Bib";
        	yAxis = "Rank";
        } else {
            xAxis = "Rank";
            yAxis = "FIS Score";
        }
        out.print(" <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "    <script align = \"center\" type=\"text/javascript\">\n" +
                "      google.charts.load('current', {'packages':['corechart']});\n" +
                "      google.charts.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "      function drawChart() {\n" +
                "        var data = google.visualization.arrayToDataTable([\n" +
                "          ['"+ xAxis + "', '"+ yAxis + "', {type: 'string', role: 'tooltip'},  {'role' : 'style'}],\n" );
        for (int j = 0; j < compareArray.size(); j++)  {
            String color;
            if (scoringIndices.contains(j)) {
                color = "'point { fill-color: lightgreen; }'";
            } else if (dnfIndices.contains(j)) {
                color = "'point { fill-color: red; }'";
            } else {
                color = "null";
            }
            out.print("[ " + compareArray.get(j)[0] + ",     " + compareArray.get(j)[1] +
                    ", '" + race.getResults().get(j).getLastfirstName() +" (" + race.getResults().get(j).getNation()
                    + "): " + race.getResults().get(j).getResult().getScore() + "' ,"
                    + color + "],\n");
//        } else {
//            out.print("[ " + bibAndScore.get(j)[0] + ",     " + bibAndScore.get(j)[1] +
//                    ", '" + race.getResults().get(j).getLastfirstName() + "'" + ", null],\n");
//        }
        }
        out.print("]);\n" +
                "\n" +
                "        var options = {\n" +
                "          title: '"+ xAxis + " vs. "+ yAxis + " comparison',\n" +
                "          hAxis: {title: '"+ xAxis + "', minValue: 0, maxValue: " + race.getResults().size() + "" +
                ", gridlines: { count: 10 }},\n" +
                "          vAxis: {title: '"+ yAxis + "', minValue: 0, maxValue: " + race.getResults().size() + "" +
                ", gridlines: { count: 10 }},\n" +
                "          legend: 'none',\n" +
                " pointSize: 4\n ");
        out.print("};\n" +
                "\n" +
                "        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));\n" +
                "\n" +
                "        chart.draw(data, options);\n" +
                "google.visualization.events.addListener(table, 'sort',\n" +
                "      function(event) {\n" +
                "        data.sort([{column: event.column, desc: !event.ascending}]);\n" +
                "        chart.draw(view);\n" +
                "      });" +
                "      }\n" +
                "    </script>" + "<div  align =\"center\" class =\"chart\" id=\"chart_div\" style=\"width:500\"></div>");


        System.out.println(graph);
%>

    <div id="infoi">
        <div style="display:flex">
        <div style="background-color:blue;height:20px;width:20px">
        </div><div>&nbsp;Finished</div>
        </div>
        <div style="height: 5px"></div>
        <div style="display:flex">
        <div style="background-color:lightgreen;height:20px;width:20px">
        </div> <div>&nbsp;Lowered World Rank</div>
        </div>
        <div style="height: 5px"></div>
        <div style="display:flex">
        <div style="background-color:red;height:20px;width:20px">
        </div><div>&nbsp;Did Not Finish</div>
        </div>

    </div>
</div>


<div style="height:20px"></div>

<%--<div align="left" style="height: 30px;overflow: hidden">--%>
    <div class="filterSelector" align="left">

        <a class = "nofilterSelector" href=http://www.ski-reference.com/ResultSearch?filter=none&raceid=<%
            out.print(raceID + "&event=" + eventType + "&graph=" + graph);
        %>>Full Results
        </a><a class = "scorersSelector" href=http://www.ski-reference.com/ResultSearch?filter=scorers&raceid=<%
            out.print(raceID + "&event=" + eventType + "&graph=" + graph);
        %>>Scorers</a>
    </div>
<%--</div>--%>
<table style="width:100%" cellspacing='0' align = "center" >
    <%
        String thisLine;
        int count=0;
        BufferedReader myInput = new BufferedReader(new StringReader(race.asResultsCSV()));
        int i=0;
        int rowNum = 0;



        int colorFactor = 0;
        while ((thisLine = myInput.readLine()) != null) {

            if ((filter.equals("scorers") && scoringIndices.contains(rowNum - 1)) || filter.equals("none") || rowNum == 0) {

                String strar[] = thisLine.split(",");
                String add;
                if (rowNum == 0) {
                    add = "\"#FFFFFF\" style=\"font-weight: bold\"";
                }
                else if (colorFactor == 0){
                    add = "\"#E1E8ED\"";
                    colorFactor = 1;
                } else {
                    add = "\"#CCD6DD\"";
                    colorFactor = 0;
                }
    %>
    <tr align="left"  bgcolor = <%
    out.print(add + "> ");
    for(int j=0;j<strar.length;j++) {
        if(i!=0) {
            out.print("<td> "+ strar[j]+ "</td> ");
        }
        else {
            out.print(" <td> <b>" +strar[j]+ "</b> </td> ");
        }
        i++;
    }
%>
            </tr><%
    }
    rowNum++;
    }
%>
</table>

</body>
</html>

