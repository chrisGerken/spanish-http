package org.gerken.spanish.servlet;

// Begin imports

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// End imports

public class QuestionServlet extends AbstractSpanishServlet {


	// Begin declarations

	private static final long serialVersionUID = 1L;

	// End declarations

	public QuestionServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin HTML 

		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();
		 
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/spanish/spanish.css\">");
		writer.println("</head>");
		

		writer.println("<h1>Servlet question</h1>");
		writer.println("<p>Other servlets include:");

		writer.println("<p><a href=\"questiontype\" target=\"_blank\">Questiontype</a>");

		writer.println("<p><a href=\"question\" target=\"_blank\">Question</a>");

		writer.println("<p><a href=\"stats\" target=\"_blank\">Stats</a>");
		
		
		writer.println("<h2>CSS and other HTML files</h2>");
		writer.println("<p>Note that this page uses a CSS file from the src/main/resources folder");

		writer.println("<html>");

		resp.setStatus(HttpServletResponse.SC_OK);

		// End HTML 

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin POST logic 

		super.doPost(req, resp);

		// End POST logic 

	}

	// Begin custom methods
	
	
	// End custom methods

	
}
