package spanish;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionMeta {

	public static String QUESTION_CONJUGATE 	= "conj";
	public static String QUESTION_RESPONSE 		= "resp";
	public static String QUESTION_TO_ENGLISH 	= "engl";
	public static String QUESTION_VERB       	= "verb";
	
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
		
		return null;
	}

	public Question getQuestionConj() {
		
		HashMap<String, String> filter = new HashMap<>();
		filter.put("verb", verb);
		filter.put("tiempo", tiempo);
		filter.put("persona", persona);
		
		ArrayList<DataBaseEntry> list = DataBase.common.query(filter);
		DataBaseEntry answer = list.get(0);
		
		filter = new HashMap<>();
		filter.put("verb", verb);
		filter.put("persona", persona);
		
		ArrayList<DataBaseEntry> options = DataBase.common.query(filter);

		Question q = new Question();
		int count = 0;
		for (DataBaseEntry option: options) {
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
		
		q.setQuestion(persona + " (" + verb + ") : " +tiempo + " (" + JsonUtils.meaning(tiempo) + ")");
		q.setOver(corrects);
		
		return q;
	}

	public Question getQuestionVerb() {
		
		ArrayList<Verb> verbs = Verb.mixed();
		Verb correct = null;
		Question q = new Question();
		for (Verb v: verbs) {
			String buf = v.getVerb();
			q.addOption(buf);
			if (buf.equals(verb)) {
				q.setAnswer(buf);
				correct = v;
			}
		}

		q.setQuestion(correct.getMeaning());
		q.setOver(corrects);
		
		return q;
	}

}
