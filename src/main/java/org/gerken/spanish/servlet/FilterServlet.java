package org.gerken.spanish.servlet;

// Begin imports

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.gerken.spanish.logic.FilterConfig;
import org.gerken.spanish.logic.FilterConfigOption;
import org.gerken.spanish.logic.Loader;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// End imports

public class FilterServlet extends AbstractSpanishServlet {


	// Begin declarations

	private static final long serialVersionUID = 1L;
	
	private static FilterConfig filterConfig = new FilterConfig();

	// End declarations

	public FilterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin HTML 

		filterConfig.reset();
		
		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();
		 
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/spanish/spanish.css\">");
		writer.println("</head>");
		

		writer.println("<h1>Coverage</h1>");

		writer.println("<form action=\"/spanish/filter\" method=\"POST\" >");

		writer.println("<table border=\"1\">");

		writer.println("<tr>");
		writer.println("<td>Verbs</td>");
		writer.println("<td>Tenses</td>");
		writer.println("<td>Questions</td>");
		writer.println("</tr>");

		writer.println("<tr>");
		writer.println("<td class=\"optionCell\">");
		writer.println("<p>Choose groups of verbs to cover:<br>");
		for (FilterConfigOption fco: filterConfig.getVerbTypeOptions()) {
			writer.println("<input type=\"checkbox\" id=\""+fco.getId()+"\" name=\""+fco.getId()+"\" />");
			writer.println("<label for=\""+fco.getId()+"\">"+fco.getLabel()+"</label>");
			writer.println("<br>");
		}
		writer.println("<p>As well as additional individual verbs:<br>");
		for (FilterConfigOption fco: filterConfig.getVerbOptions()) {
			writer.println("<input type=\"checkbox\" id=\""+fco.getId()+"\" name=\""+fco.getId()+"\" />");
			writer.println("<label for=\""+fco.getId()+"\">"+fco.getLabel()+"</label>");
			writer.println("<br>");
		}
		writer.println("</td>");

		writer.println("<td class=\"optionCell\">");
		for (FilterConfigOption fco: filterConfig.getTenseOptions()) {
			writer.println("<input type=\"checkbox\" id=\""+fco.getId()+"\" name=\""+fco.getId()+"\" />");
			writer.println("<label for=\""+fco.getId()+"\">"+fco.getLabel()+"</label>");
			writer.println("<br>");
		}
		writer.println("</td>");

		writer.println("<td class=\"optionCell\">");
		for (FilterConfigOption fco: filterConfig.getQuestionOptions()) {
			writer.println("<input type=\"checkbox\" id=\""+fco.getId()+"\" name=\""+fco.getId()+"\" />");
			writer.println("<label for=\""+fco.getId()+"\">"+fco.getLabel()+"</label>");
			writer.println("<br>");
		}
		writer.println("</td>");
		writer.println("</tr>");

		writer.println("<tr><td colspan=\"3\">");
		writer.println("<input type=\"submit\">");
		writer.println("</tr>");

		writer.println("</table>");
		writer.println("</form>");
		
		writer.println("<h3>Other actions</h3>");
		writer.println("<BR><BR><a href =\"/spanish/ref01.html\" target=\"_blank\">Reference</a>");
		writer.println("<BR><BR><a href =\"/spanish/stop\">End practice</a>");
		
		writer.println("<html>");

		resp.setStatus(HttpServletResponse.SC_OK);

		// End HTML 

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin POST logic 

		Map<String, String[]> map = req.getParameterMap();
		
		for (String id: map.keySet()) {
			String[] value = map.get(id);
			boolean setting = "on".equals(value[0]);
			FilterConfigOption fco = filterConfig.getOption(id);
			fco.setEnabled(setting);
		}
		
		ArrayList<String> selected = new ArrayList<String>(filterConfig.getIncludedVerbs());
		String[] array = new String[selected.size()];
		selected.toArray(array);
		Loader.setVerbsToUse(array);
		
		selected = new ArrayList<String>(filterConfig.getIncludedTenses());
		array = new String[selected.size()];
		selected.toArray(array);
		Loader.setTensesToUse(array);
		
		Loader.setQuestionsToAsk(filterConfig.getIncludedQuestions());
		
		Loader.loadQuestions();
		
		resp.sendRedirect("question");
		
		// End POST logic 

	}

	// Begin custom methods
	
	
	// End custom methods

	
}
