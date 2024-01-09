package org.gerken.spanish.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class FilterConfig {

	private int idIndex = 0;
	
	private ArrayList<FilterConfigOption> verbOptions = new ArrayList<>();
	private ArrayList<FilterConfigOption> verbTypeOptions = new ArrayList<>();
	private ArrayList<FilterConfigOption> tenseOptions = new ArrayList<>();
	private ArrayList<FilterConfigOption> questionOptions = new ArrayList<>();

	private HashSet<String> includedVerbs = new HashSet<>();
	private HashSet<String> includedTenses = new HashSet<>();
	private HashSet<String> includedQuestions = new HashSet<>();
	
	private HashMap<String, FilterConfigOption> options = new HashMap<>(); 
	
	public FilterConfig() {
		super();
		reset();
	}
	
	public void reset() {

		options 			= new HashMap<>();
		includedVerbs 		= new HashSet<>();
		includedTenses 		= new HashSet<>();
		includedQuestions	= new HashSet<>();
		verbOptions			= new ArrayList<>();
		verbTypeOptions		= new ArrayList<>();
		tenseOptions		= new ArrayList<>();
		questionOptions		= new ArrayList<>();
		
		// Verbs 
		
		ArrayList<String> sorted = new ArrayList<>();
		for (Verb v: DataBase.common.allVerbs()) {
			sorted.add(v.getVerb());
		}
		Collections.sort(sorted);
		for (String v: sorted) {
			verbOptions.add(new VerbOption(this, nextId(), v, v, false));
		}

		// Verb types 

		verbTypeOptions.add(new VerbTypeOption(this, nextId(), "-ar verbs", "ar", false));
		verbTypeOptions.add(new VerbTypeOption(this, nextId(), "-er verbs", "er", false));
		verbTypeOptions.add(new VerbTypeOption(this, nextId(), "-ir verbs", "ir", false));
		verbTypeOptions.add(new VerbTypeOption(this, nextId(), "irregular verbs", "irregular", false));
		verbTypeOptions.add(new AllVerbOption(this, nextId(), false));

		// Tenses 
		
		for (String t: Constants.tiempo) {
			tenseOptions.add(new TenseOption(this, nextId(), t, t, false));
		}

		// Questions 
		
		for (String q: QuestionMeta.questionTypes) {
			questionOptions.add(new QuestionTypeOption(this, nextId(), q, q, false));
		}
		
	}
	
	public void includeVerb(String verb, boolean enabled) {
		if (enabled) {
			includedVerbs.add(verb);
		} else {
			includedVerbs.remove(verb);
		}
	}

	public void includeTense(String tense, boolean enabled) {
		if (enabled) {
			includedTenses.add(tense);
		} else {
			includedTenses.remove(tense);
		}
	}

	public void includeQuestionType(String type, boolean enabled) {
		if (enabled) {
			includedQuestions.add(type);
		} else {
			includedQuestions.remove(type);
		}
	}

	public void includeVerbType(String type, boolean enabled) {

		for (Verb v: DataBase.common.allVerbs()) {
			if (type.equals(v.getKind())) {
				includeVerb(v.getVerb(), enabled);
			}
		}

	}

	public void includeAllVerbs(boolean enabled) {

		for (Verb v: DataBase.common.allVerbs()) {
			includeVerb(v.getVerb(), enabled);
		}

	}

	public String nextId() {
		idIndex++;
		return "id"+idIndex;
	}

	public ArrayList<FilterConfigOption> getVerbOptions() {
		return verbOptions;
	}

	public ArrayList<FilterConfigOption> getVerbTypeOptions() {
		return verbTypeOptions;
	}

	public ArrayList<FilterConfigOption> getTenseOptions() {
		return tenseOptions;
	}

	public ArrayList<FilterConfigOption> getQuestionOptions() {
		return questionOptions;
	}

	public HashSet<String> getIncludedVerbs() {
		return includedVerbs;
	}

	public HashSet<String> getIncludedTenses() {
		return includedTenses;
	}

	public HashSet<String> getIncludedQuestions() {
		return includedQuestions;
	}
	
	public void note(FilterConfigOption fco) {
		options.put(fco.getId(), fco);
	}
	
	public FilterConfigOption getOption(String id) {
		return options.get(id);
	}

}
