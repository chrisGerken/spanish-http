package org.gerken.spanish.servlet;

// Begin imports

import java.io.IOException;
import java.io.PrintWriter;

import org.gerken.spanish.access.SpanishHttp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// End imports

public class StopServlet extends AbstractSpanishServlet {


	// Begin declarations

	private static final long serialVersionUID = 1L;

	// End declarations

	public StopServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin HTML 
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				try { 
					Thread.sleep(2000L); 
				} catch (Throwable t) {  
					
				}
				SpanishHttp.common.stop();
			}
		};

		new Thread(runnable).start();
		
		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();
		 
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/spanish/spanish.css\">");
		writer.println("</head>");
		

		writer.println("<h1>Ending Practice Session</h1>");
		writer.println("<p>Practice app is stopping");

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
