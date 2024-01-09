package org.gerken.spanish.servlet;

// Begin imports

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.gerken.spanish.logic.DataBase;
import org.gerken.spanish.logic.Question;

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

		Map<String, String> query = parse(req.getQueryString());
		if (query.containsKey("id")) {
			String buf = query.get("id"); 
			Integer id = Integer.parseInt(buf);
			buf = query.get("correct"); 
			boolean correct = Boolean.parseBoolean(buf);
			Question q = DataBase.common.getQuestion(id);
			if (correct) {
				q.correct();
			} else {
				q.incorrect();
			}
		}
		
		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();

		Question q = DataBase.common.nextQuestion();
		if (q == null) {
			resp.sendRedirect("/spanish/filter");
			return;
		}
		
		JSONObject jobj = null;
		try {
			jobj = q.asJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
 		String rightId = "'r"+q.getAnswerCode()+"button'";
		 
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/spanish/spanish.css\">");

		writer.println("	<script>");
		writer.println("        function right( button ) {");
		writer.println("			button.style.background = 'green';");
		writer.println("			location.replace(\"/spanish/question?id="+q.getId()+"&correct=true\");");
		writer.println("		}");

		writer.println("        function wrong( button ) {");
		writer.println("			button.style.background = 'red';");
		writer.println("			document.getElementById("+rightId+").style.background = 'green';");
//		writer.println("			location.replace(\"/spanish/question?id="+q.getId()+"&correct=false\");");
		writer.println("		}");
		writer.println("	</script>");
		
		writer.println("</head>");
		
	    

		writer.println("<div >");
		writer.println("<h3>"+q.getQuestion()+"</h3>");
		writer.println("</div >");

		writer.println("<div class=\"question\">");
 		
 		int index = 0;
 		for (String r: q.getOrderedOptions()) {
 			index++;
 			if (index==q.getAnswerCode()) {
 				String fn = "right";

 	 			String button = "<button id=\"r"+index+"button\" onclick=\""+fn+"(this)\" class=\"answerButton\">"+r+"</button>";
 	 			writer.println("<br><br>"+ button);

 			} else {
 	 			String fn = "wrong";

 	 			String button = "<button id=\"r"+index+"button\" onclick=\""+fn+"(this)\" class=\"answerButton\">"+r+"</button>";
 	 			writer.println("<br><br>"+ button);


 			}
 			
 		}

		writer.println("</div >");

		writer.println("<BR><BR><a href =\"/spanish/question\">Next question</a>");

// 		writer.println("<p><pre>");
//		try {
//			writer.println(jobj.toString(4));
//		} catch (JSONException e) {
//		}
//		writer.println("</pre>");
		
		int count = 0;
		for (Question q1: DataBase.common.allQuestions()) {
			if (!q1.isFinished()) {
				count++;
//				writer.println("<BR>"+q1.stats());
			}
		}
		writer.println("<h3>Stats</h3>");
		writer.println("<p>"+count+" question(s) remaining.");
		
		writer.println("<h3>Other actions</h3>");
		writer.println("<BR><BR><a href =\"/spanish/ref01.html\" target=\"_blank\">Reference</a>");
		writer.println("<BR><BR><a href =\"/spanish/filter\">Restart questions</a>");
		writer.println("<BR><BR><a href =\"/spanish/stop\">End practice</a>");

		writer.println("</html>");

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
