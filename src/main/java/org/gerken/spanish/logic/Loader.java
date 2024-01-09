package org.gerken.spanish.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Loader {
	
//	private static String verbsToUse[] = { "pedir" , "perder" , "contestar" , "estar" , "ser" , "ir" };
	private static String verbsToUse[] = { "comprar" , "bajar" , "contestar" , "enviar" , "tomar" };
	
//	private static String[] tensesToUse = { "presente" , "indefinido"};
	private static String[] tensesToUse = { "indefinido"};

	private static HashSet<String> questionsToAsk = new HashSet<>();

	public static void load() {
		loadVerbs("v01.json");
		loadVerbs("v02.json");
		loadVerbs("v03.json");
		loadVerbs("v04.json");
		loadVerbs("v05.json");
		loadQuestions();
	}

	public static void setVerbsToUse(String[] array) {
		verbsToUse = array;
	}
	
	public static void setTensesToUse(String[] array) {
		tensesToUse = array;
	}
	
	public static void setQuestionsToAsk(HashSet<String> set) {
		questionsToAsk = set;
	}
	
	private static void loadVerbs(String resource) {
		JSONArray jarr = readFrom(resource);
		store(jarr);		
	}

	public static void loadQuestions() {
		
		DataBase.common.clearQuestions();
		
		if (questionsToAsk.contains(QuestionMeta.QUESTION_CONJUGATE)) {
			for (String v: verbsToUse) {
				for (String t: tensesToUse) {
					for (String p: Constants.persona) {
						QuestionMeta qm = new QuestionMeta(v, t, p, QuestionMeta.QUESTION_CONJUGATE, 1);
						DataBase.common.addQuestion(qm.getQuestion());
					}
				}
			}
		}

		if (questionsToAsk.contains(QuestionMeta.QUESTION_VERB)) {
			for (String verb: verbsToUse) {
				QuestionMeta qm = new QuestionMeta(verb, null, null, QuestionMeta.QUESTION_VERB, 2);
				DataBase.common.addQuestion(qm.getQuestion());
			}
		}
		
		DataBase.common.sortQuestions();
	}
	
	private static void store(JSONArray jarr) {

		for (int index = 0; index < jarr.length(); index++) {
			try {
				DataBase.common.addVerb(new Verb(jarr.getJSONObject(index)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static JSONArray readFrom(String resource) {
		try {
			InputStream is = Loader.class.getResourceAsStream(resource);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[5000];
			int len = is.read(b);
			while (len > -1) {
				baos.write(b, 0, len);
				len = is.read(b);
			}
			is.close();
			String buf = baos.toString();
			return new JSONArray(buf);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
