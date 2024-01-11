package org.gerken.spanish.servlet;

// Begin imports

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.gerken.spanish.logic.ConjugationRow;
import org.gerken.spanish.logic.Constants;
import org.gerken.spanish.logic.DataBase;
import org.gerken.spanish.logic.Verb;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// End imports

public class ConjugationsServlet extends AbstractSpanishServlet {


	// Begin declarations

	private static final long serialVersionUID = 1L;

	// End declarations

	public ConjugationsServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin HTML 
		 
		Map<String, String> query = parse(req.getQueryString());
		String format = query.get("format");
		if (format == null) {
			format = "verb=*|tense=presente";
		}
		
		ArrayList<String> order = new ArrayList<>();
		ArrayList<String> filter = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(format,"|");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int index = token.indexOf('=');
			String property = token.substring(0,index);
			String value = token.substring(index+1);
			order.add(property);
			filter.add(value);
		}
		
		ArrayList<ConjugationRow> rows = new ArrayList<>();
		for (Verb verb: DataBase.common.allVerbs()) {
			for (ConjugationRow cr: verb.getRows()) {
				if (cr.matches(order,filter)) {
					rows.add(cr);
				}
			}
		}
		
		Collections.sort(rows);
		
		resp.setContentType("text/html");

		
		PrintWriter writer = resp.getWriter();
		 
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/spanish/spanish.css\">");
		writer.println("</head>");
		
		ArrayList<String> ths = new ArrayList<>();
		boolean filtered = false;
		StringBuffer sb = new StringBuffer();
		String delim = "";
		for (int index = 0; index<order.size(); index++) {
			String property = order.get(index);
			String value = filter.get(index);
			if (!value.equals("*")) {
				if (property.equals("verb")) {
					sb.append(delim+value);
				} else if (property.equals("kind")) {
					sb.append(delim+value+" verbs");
				} else if (property.equals("tense")) {
					sb.append(delim+value);
				}
				delim = "; ";
				filtered = true;
			} else {
				ths.add(property);
			}
		}
		if (!filtered) {
			sb.append("all verbs; all tenses");
		}

		writer.println("<h1>Conjugations</h1>");
		writer.println("<h3>"+sb.toString()+"</h3>");
		
		writer.println("<table border=\"1\">");
		writer.println("<tr>");
		for (String h: ths) {
			writer.println("<th>"+h+"</th>");
		}
		for (String h: Constants.persona) {
			writer.println("<th>"+h+"</th>");
		}
		writer.println("</tr>");

		for (ConjugationRow cr: rows) {

			writer.println("<tr>");
			for (String h: cr.getNav()) {
				writer.println("<th>"+h+"</th>");
			}
			for (String buf: cr.getConjugation()) {
				writer.println("<td>"+buf+"</td>");
			}
			writer.println("</tr>");

		}
		

		writer.println("</table>");

		writer.println("<BR><BR><a href=\"question\" >Continue questions</a>");
		writer.println("<BR><a href =\"filter\">Restart questions</a>");

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
	

	protected Map<String, String> parse(String queryString) {
		if (queryString==null) { queryString = ""; }
		StringTokenizer st = new StringTokenizer(queryString, "&");
		HashMap<String, String> results = new HashMap<String, String>();
		while (st.hasMoreTokens()) {
			String part = st.nextToken();
			int index = part.indexOf('=');
			if (index < 0) {
				results.put(part.toLowerCase(), "");
			} else {
				String key = part.substring(0, index).toLowerCase();
				String value = part.substring(index+1);
				value = URLDecoder.decode(value);
				results.put(key, value);
			}
		}
		return results;
	}
	
	// End custom methods

	
}
