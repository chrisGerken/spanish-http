package org.gerken.spanish.logic;

import java.util.HashMap;
import java.util.Objects;

public class Conjugation {

	private String verb;
	private String tiempo;
	private String persona;
	private String spelling;
	private Verb   verbDef;
	private HashMap<String,String> map = new HashMap<>();
	

	public Conjugation(String tiempo, String persona, String spelling, Verb verb) {
		super();
		this.tiempo = tiempo;
		this.persona = persona;
		this.spelling = spelling;
		this.verbDef = verb;
		this.verb = verb.getVerb();
		this.map.put("persona", persona);
		this.map.put("tiempo", tiempo);
		this.map.put("spelling", spelling);
		this.map.put("verb", verbDef.getVerb());
	}

	public String getTiempo() {
		return tiempo;
	}

	public String getPersona() {
		return persona;
	}

	public String getSpelling() {
		return spelling;
	}

	public String getVerb() {
		return verb;
	}

	public Verb getVerbDef() {
		return verbDef;
	}

	public boolean matches(HashMap<String, String> filter) {
		for (String key: filter.keySet()) {
			if (!filter.get(key).equals(map.get(key))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(persona, tiempo, verb);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conjugation other = (Conjugation) obj;
		return Objects.equals(persona, other.persona) && Objects.equals(tiempo, other.tiempo)
				&& Objects.equals(verb, other.verb);
	}

}
