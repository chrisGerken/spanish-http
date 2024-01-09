package org.gerken.spanish.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DataBase {

	public static DataBase common = new DataBase();
	
	private ArrayList<Conjugation> conjugations = new ArrayList<>();
	
	private HashMap<Integer,Question> questions = new HashMap<>();
	private ArrayList<Question>       ordered = null;

	private ArrayList<Verb> verbs = new ArrayList<>();
	
	private DataBase() {

	}

	public void addQuestion(Question question) {
		questions.put(question.getId(), question);
	}
	
	public Question getQuestion(Integer id) {
		return questions.get(id);
	}
	
	public void add(Conjugation conjugation) {
		conjugations.add(conjugation);
	}

	public ArrayList<Conjugation> query(HashMap<String, String> filter) {

		ArrayList<Conjugation> result = new ArrayList<>();
		for (Conjugation conjugation: conjugations) {
			if (conjugation.matches(filter)) {
				result.add(conjugation);
			}
		}

		return result;
	}

	public void sortQuestions() {
		ordered = new ArrayList<>();
		for (Question q: questions.values()) {
			if (!q.isFinished()) {
				q.resort();
				ordered.add(q);
			}
		}
		Collections.sort(ordered);
	}

	public Question nextQuestion() {
		if (ordered.isEmpty()) {
			sortQuestions();
		}
		if (ordered.isEmpty()) {
			return null;
		}
		return ordered.remove(0);
	}

	public ArrayList<Question> allQuestions() {
		Comparator<Question> comp = new Comparator<Question>() {
			
			@Override
			public int compare(Question o1, Question o2) {
				return o1.getId().compareTo(o2.getId());
			}
		};
		ArrayList<Question> byId = new ArrayList<>(questions.values());
		Collections.sort(byId, comp);
		return byId;
	}

	public ArrayList<Verb> allVerbs() {
		return new ArrayList<Verb>(verbs);
	}
	
	public ArrayList<Verb> randomizedVerbs() {
		for (Verb v: verbs) {
			v.spin();
		}
		Collections.sort(verbs);
		return new ArrayList<Verb>(verbs);
	}
	
	public ArrayList<String> verbs() {
		ArrayList<String> result = new ArrayList<>(verbs.size());
		for (Verb v: verbs) {
			result.add(v.getVerb());
		}
		return result;
	}

	public void addVerb(Verb verb) {
		verbs.add(verb);
	}

	public Verb getVerb(String verb) {
		for (Verb v: verbs) {
			if (verb.equals(v.getVerb())) {
				return v;
			}
		}
		return null;
	}

	public void clearQuestions() {
		questions = new HashMap<>();
		ordered = null;
	}
}
