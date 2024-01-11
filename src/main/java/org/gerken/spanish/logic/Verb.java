package org.gerken.spanish.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Verb implements Comparable<Verb> {

	private static Random random = new Random(System.currentTimeMillis());

	public static ArrayList<Verb> verbs = new ArrayList<>();
	
	private HashMap<String, HashMap<String,String>> spellings = new HashMap<>();
	private HashMap<String, String> uses = new HashMap<>();
	private String meaning;
	private String verb;
	private String kind;
	
	private Double				sort = random.nextDouble();

	public Verb(JSONObject jsonObject) throws JSONException {
		
		verb 	= jsonObject.getString("verb");
		meaning = jsonObject.getString("meaning");
		kind 	= jsonObject.getString("kind");
		
		JSONObject tiempos = jsonObject.getJSONObject("tiempos");
		
		for (String t: Constants.tiempo) {
			
			JSONObject jobj = tiempos.getJSONObject(t);
			spellings.put(t, new HashMap<>());
			
			for (String p: Constants.persona) {
				String spelling = jobj.getString(p);
				spellings.get(t).put(p, jobj.getString(p));
				
				DataBase.common.add(new Conjugation(t, p, spelling, this));
			}
			
		}
		
		for (int index = 0; index < Constants.tiempo.length; index++) {
			uses.put(Constants.tiempo[index], Constants.use[index]);
		}
	
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Override
	public int hashCode() {
		return Objects.hash(verb);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Verb other = (Verb) obj;
		return Objects.equals(verb, other.verb);
	}

	public void spin() {
		sort = random.nextDouble();
	}

	@Override
	public int compareTo(Verb o) {
		return sort.compareTo(o.sort);
	}

	public ArrayList<ConjugationRow> getRows() {
		ArrayList<ConjugationRow> result = new ArrayList<>();
		for (String t: spellings.keySet()) {
			if (spellings.containsKey(t)) {
				ConjugationRow row = new ConjugationRow(verb, kind, t, spellings.get(t));
				result.add(row);
			}
		}
		return result;
	}

}
