package com;

import com.tracking.CreateTracking;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveTrackingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/RemoveTracking.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/RemoveTrackingPost.jsp");
			String fisIdsCSV = request.getParameter("fisIDs");
			System.out.println("HIII1");
			try {
				System.out.println("HIII");
				CreateTracking.validateFISidList(fisIdsCSV);

			} catch( NumberFormatException e) {
				rd = request.getRequestDispatcher("/WEB-INF/RemoveTrackingError.jsp");
//				response.sendRedirect("http://localhost:8080/removeTrackingError?email=" + request.getParameter("email"));
			}

			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
