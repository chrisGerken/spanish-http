package spanish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TerminalTest {

	private static String verb[] = { "pedir" , "perder" , "contestar" };
	
	// { "presente", "preterito", "imperfecto", "futuro immediato", "futuro" };
	private String[] tensesToCheck = { "presente" , "preterito"};
	
	private ArrayList<Question> questions = new ArrayList<>();;
	
	public TerminalTest() {
		new Loader().load("v01.json");
		loadQuestions();
	}

	private void loadQuestions() {
		for (String v: verb) {
			for (String t: tensesToCheck) {
				for (String p: JsonUtils.persona) {
					QuestionMeta qm = new QuestionMeta(v, t, p, QuestionMeta.QUESTION_CONJUGATE, 1);
					questions.add(qm.getQuestion());
				}
			}
		}
		
		ArrayList<Verb> list = new ArrayList<>(Verb.verbs);
		for (Verb v: list) {
			QuestionMeta qm = new QuestionMeta(v.getVerb(), null, null, QuestionMeta.QUESTION_VERB, 1);
			questions.add(qm.getQuestion());
		}
		
		Collections.sort(questions);
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public static void main(String[] args) {

		TerminalTest tt = new TerminalTest();
		Scanner sc= new Scanner(System.in);   
		
		ArrayList<Question> current = tt.getQuestions();

		while (!current.isEmpty()) {
			
			ArrayList<Question> pending = new ArrayList<>();;
			int count = 0;
			
			for (Question q: current) {

				System.out.println("\n\n");  
				System.out.println(q.getPrompt());  
				
				Integer answer = null;
				while (answer == null) {
					try {
						String str= sc.nextLine();                 
						answer = Integer.parseInt(str);
					} catch (NumberFormatException e) {
						System.out.println("Try again?");
					}
				}
				
				if (answer == q.getAnswerCode()) {
					System.out.println("Correct!"); 
					q.correct();
				} else {
					q.incorrect();
					System.out.println("Incorrect. Correct answer was : "+q.getAnswerCode());     
				}

				if (!q.isFinished()) {
					pending.add(q);
				}
				count++;
				System.out.println("Remaining: "+(current.size()-count)+":"+pending.size());

			}

			
			current = pending;
		}
		

	}

}
