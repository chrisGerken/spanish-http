package org.gerken.spanish.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Strip01 {

	public static void main(String[] args) {

		try {
			String from = "/home/chris/Documents/test/verbs_html.txt";
			String to   = "/home/chris/Documents/test/verbs.txt";
			Strip01 strip = new Strip01();
			strip.strip(from, to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Strip01() {
	}

	private void strip(String from, String to) throws IOException {

		FileWriter fw = new FileWriter(to);
		
		BufferedReader br = new BufferedReader(new FileReader(from));
		String line = br.readLine();
		while (line != null) {
			line = line.trim();
			if (line.startsWith("<td")) {
				StringTokenizer st = new StringTokenizer(line, "<>");
				st.nextToken();
				String es = st.nextToken();
				st.nextToken();
				st.nextToken();
				String en = st.nextToken();
				String kind = es.substring(es.length()-2);
				fw.write(es+"|"+en+"|"+kind+"\n");
			}
			
			line = br.readLine();
		}
		fw.close();
		br.close();
	}

}
