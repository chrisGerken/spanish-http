package org.gerken.spanish.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class Minimal {

	public static void main(String[] args) {

		try {
			String from = "/home/chris/git/spanish-http/ingest/verbs.txt";
			String to   = "/home/chris/git/spanish-http/src/main/resources/org/gerken/spanish/logic/minimal.json";
			Minimal min = new Minimal();
			min.reform(from, to);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Minimal() {
	}

	private void reform(String from, String to) throws Exception {

		FileWriter fw = new FileWriter(to);
		
		JSONArray jarr = new JSONArray();
		
		BufferedReader br = new BufferedReader(new FileReader(from));
		String line = br.readLine();
		while (line != null) {
			line = line.trim();
			StringTokenizer st = new StringTokenizer(line, "|");
			String es = st.nextToken().trim();
			String en = st.nextToken().trim();
			String kind = st.nextToken().trim();
		
			JSONObject jobj = new JSONObject();
			jobj.put("verb", es);
			jobj.put("meaning", en);
			jobj.put("kind", kind);
			
			jarr.put(jobj);
			
			line = br.readLine();
		}
		
		fw.write(jarr.toString(4)+"\n");
		fw.close();
		br.close();
	}

}
