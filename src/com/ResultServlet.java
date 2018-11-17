package com;

import com.results.AthleteUtils;
import com.results.RaceAthlete;
import com.results.TechRace;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultServlet extends HttpServlet {

	public static String hostName = "http://localhost:8080";

	/**
	 * When the user enters a url, the response is redirected back to this
	 * servlet and changes the raceid field such that it is the race id
	 * on the fis website. This is done to clean up the url of the
	 * resulting web page
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TechRace myRace = null;
		PrintWriter out = response.getWriter();
		String raceid = request.getParameter("raceid");
		String event = request.getParameter("event");
		try {
			Integer.parseInt(raceid);
			try {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Result.jsp");
				rd.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			// raceid is user entered URL of race
			try {
				raceid = new AthleteUtils().getRaceID(raceid);
			} catch (Exception s) {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Error.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException e1) {
					e1.printStackTrace();
				}
			}
			response.sendRedirect("ResultSearch?raceid=" + raceid +
					"&event=" + event);
		}
	}
}
