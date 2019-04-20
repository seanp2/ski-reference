package com;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConfirmTrackingServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ConfirmTracking.jsp");
		try {
			rd.forward(request,response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}
}
