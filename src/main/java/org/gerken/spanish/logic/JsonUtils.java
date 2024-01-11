package org.gerken.spanish.logic;

import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JsonUtils {

	public static void main(String[] args) {

		try {
			JSONArray jarr = new JSONArray();
			
			jarr.put(templateForVerb("dormir", "to sleer", "ir", "dorm"));
			jarr.put(templateForVerb("escribir", "to write", "ir", "escrib"));
			jarr.put(templateForVerb("entender", "to learn", "er", "intend"));
			jarr.put(templateForVerb("comer", "to eat", "er", "com"));
			jarr.put(templateForVerb("beber", "to drink", "er", "beb"));
			jarr.put(templateForVerb("aprender", "to understand", "er", "aprend"));
			jarr.put(templateForVerb("querer", "to want", "irregular", "quer"));
			jarr.put(templateForVerb("poder", "to be able", "irregular", "pod"));
			jarr.put(templateForVerb("saber", "to know", "irregular", "sa"));
			jarr.put(templateForVerb("decir", "to say", "irregular", "dec"));
			System.out.println(jarr.toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private static JSONObject templateForVerb(String verb, String meaning, String kind, String stem) throws JSONException {
		
		HashMap<String, String> irs = new HashMap<>();
		irs.put("yo", "voy a ");
		irs.put("tú", "vas a ");
		irs.put("él", "va a ");
		irs.put("nosotros", "vamos a ");
		irs.put("vosotros", "vais a ");
		irs.put("ellas", "van a ");
		
		HashMap<String, String> futs = new HashMap<>();
		futs.put("yo", "é");
		futs.put("tú", "ás");
		futs.put("él", "á");
		futs.put("nosotros", "emos");
		futs.put("vosotros", "éis");
		futs.put("ellas", "arán");
		
		
		JSONObject cla = new JSONObject();
		cla.put("verb", verb);
		cla.put("meaning", meaning);
		cla.put("kind", kind);
		
		JSONObject tiempos = new JSONObject();
		cla.put("tiempos", tiempos);
		for (String t: Constants.tiempo) {
			JSONObject jobj = new JSONObject();
			tiempos.put(t, jobj);
			
			for (String p: Constants.persona) {
				String buf = verb;
				if (t.equals("presente")) {
					buf = stem;
				} else if (t.equals("indefinido")) {
					buf = stem;
				} else if (t.equals("imperfecto")) {
					buf = stem;
				} else if (t.equals("futuro immediato")) {
					buf = irs.get(p) + verb;
				} else if (t.equals("futuro")) {
					buf = verb + futs.get(p);
				}
				jobj.put(p, buf);
			}
			
		}
		
		return cla;
	
	}
}	
