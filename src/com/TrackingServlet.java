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
import java.util.Scanner;

public class TrackingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Tracking.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {



		try {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/TrackingPost.jsp");

			String fisIdsCSV = request.getParameter("fisIDs");
			Scanner scanner = new Scanner(fisIdsCSV);
			try {
				InternetAddress email = new InternetAddress(request.getParameter("email"));
				email.validate();
				CreateTracking.validateFISidList(fisIdsCSV);
			} catch(AddressException | NumberFormatException e) {
				rd = request.getRequestDispatcher("/WEB-INF/TrackingError.jsp");
			}
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}

