package org.gerken.spanish.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionMeta {

	public static String QUESTION_CONJUGATE 	= "Verb Conjugation";
	public static String QUESTION_RESPONSE 		= "Response Conjugation";
	public static String QUESTION_TO_ENGLISH 	= "Spanish to English";
	public static String QUESTION_VERB       	= "English to Spanish";
	
	public static String[] questionTypes = { QUESTION_CONJUGATE , QUESTION_RESPONSE , QUESTION_TO_ENGLISH , QUESTION_VERB };
	
	private String verb;
	private String tiempo;
	private String persona;	
	private String kind;
	
	private int    corrects;
	
	public QuestionMeta(String verb, String tiempo, String persona, String kind, int corrects) {
		super();
		this.verb = verb;
		this.tiempo = tiempo;
		this.persona = persona;
		this.kind = kind;
		this.corrects = corrects;
	}

	@Override
	public String toString() {
		return "QuestionMeta [verb=" + verb + ", tiempo=" + tiempo + ", persona=" + persona + ", kind=" + kind + "]";
	}

	public Question getQuestion() {
		
		if (kind.equals(QUESTION_CONJUGATE)) {
			return getQuestionConj();
		}
		
		if (kind.equals(QUESTION_VERB)) {
			return getQuestionVerb();
		}
		
		if (kind.equals(QUESTION_TO_ENGLISH)) {
			return getQuestionToEnglish();
		}
		
		return null;
	}

	public Question getQuestionConj() {
		
		HashMap<String, String> filter = new HashMap<>();
		filter.put("verb", verb);
		filter.put("tiempo", tiempo);
		filter.put("persona", persona);
		
		ArrayList<Conjugation> list = DataBase.common.query(filter);
		Conjugation answer = list.get(0);
		
		filter = new HashMap<>();
		filter.put("verb", verb);
		filter.put("persona", persona);
		
		ArrayList<Conjugation> options = DataBase.common.query(filter);

		Question q = new Question();
		int count = 0;
		for (Conjugation option: options) {
			String buf = persona + " " + option.getSpelling();
			if (option.equals(answer)) {
				q.addOption(buf);
				q.setAnswer(buf);
			} else {
				count++;
				if (count < 5) {
					q.addOption(buf);
				}
			}
		}

		// answer.getVerbDef().getMeaning()
		
		q.setQuestion(persona + " (" + verb + ") : " +tiempo + " (" + Constants.tenseDefinition(tiempo) + ")");
		q.setOver(corrects);
		
		return q;
	}

	public Question getQuestionVerb() {
		
		ArrayList<Verb> verbs = DataBase.common.randomizedVerbs();
		Question q = new Question();
		int count = 5;
		int index = 0;
		for (Verb v: verbs) {
			String buf = v.getVerb();
			if (index < count) {
				index++;
				q.addOption(buf);
			}
			if (buf.equals(verb)) {
				q.addOption(buf);
				q.setAnswer(buf);
				q.setQuestion(v.getMeaning());
			}
		}

		q.setOver(corrects);
		
		return q;
	}

	public Question getQuestionToEnglish() {
		
		ArrayList<Verb> verbs = DataBase.common.randomizedVerbs();
		Question q = new Question();
		int count = 5;
		int index = 0;
		for (Verb v: verbs) {
			String buf = v.getMeaning();
			if (index < count) {
				index++;
				q.addOption(buf);
			}
			if (v.getVerb().equals(verb)) {
				q.addOption(buf);
				q.setAnswer(buf);
				q.setQuestion(v.getVerb());
			}
		}

		q.setOver(corrects);
		
		return q;
	}

}
