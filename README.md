# ski-reference
__This repository is now deprecated, please see [ski-reference-ui](https://github.com/seanp2/ski-reference-ui) and [ski-reference-backend](https://github.com/seanp2/ski-reference-backend)__
The following Jars are required in the classpath dependencies: 
* Apache Tomcat 9  [https://tomcat.apache.org/download-90.cgi]
* JUnit 4 [https://github.com/JUnit-team/JUnit4/wiki/Download-and-Install]
* MySQL Connector [https://dev.mysql.com/downloads/connector/j/]
* JSoup 1.11.3 [https://jsoup.org/news/release-1.11.3]
* Javax Mail jar 1.6.2 [https://www.oracle.com/technetwork/java/javamail/index-138643.html]
- - - -
The following libraries/jars from above must also be placed in the “web/WEB-INF/lib” package:
* Javax Mail
* JSoup
* MySQL Connector
- - - -
In order to run the program, create a run configuration using Apache Tomcat, 
then use that configuration to run the project on a local Tomcat host.
- - - -
__Note__: The current DBconnection.java file 
connects to the remote server. 
If you are going to use a local database server,
the DBconnection.java file will need to be changed
such that it connects to your local database.
__Note__: The package com.results, as well as some of the
 servlets and JSP files, contain the code used for the original
 purpose of the site, race analysis. 
 Here is a list of files that do not contribute to the purpose
 of the new additions made in this project:
 * com.results.*
 * com.updatedb.PointMakeUpGenerator.java
 * com.ResultServlet
 * web.WEB-INF.Error.JSP
 * web.WEB-INF.Header.JSP
 * web.WEB-INF.Result.JSP
 * web.WEB-INF.ResultSearch.JSP
 * web.WEB-INF.ResultsHeader.JSP
 
