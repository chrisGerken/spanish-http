package spanish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Question implements Comparable<Question> {

	private static Random random = new Random(System.currentTimeMillis());
	
	private String 				question = "";
	private HashSet<String> 	rawOptions = new HashSet<>();
	private String 				answer;
	private int 				answerCode;
	
	private int    				correct   = 0;
	private int	   				incorrect = 0;
	private int					over = 0;
	
	private Double				sort = random.nextDouble();
	
	public Question() {
		super();
	}

	public void setQuestion(String buf) {
		question = buf;
	}
	
	public void addOption(String option) {
		rawOptions.add(option);
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getAnswerCode() {
		return answerCode;
	}

	public String getPrompt() {
		ArrayList<Integer> order = new ArrayList<>();
		ArrayList<String> options = new ArrayList<>(rawOptions);
		for (int i = options.size(); i > 0; i--) {
			int pick = (int) Math.floor(random.nextDouble() * i);
			order.add(pick);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(question+"\n\n");
		for (int i = 1; i <= order.size(); i++) {
			int index = order.get(i-1);
			String buf = options.get(index);
			options.remove(index);
			sb.append(""+i+") "+buf+"\n");
			if (buf.equals(answer)) {
				answerCode = i;
			}
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(Question o) {
		return sort.compareTo(o.sort);
	}

	public void correct() {
		correct++;
	}
	
	public void incorrect() {
		incorrect++;
	}
	
	public void setOver(int over) {
		this.over = over;
	}

	public boolean isFinished() {
		return over <= (correct - incorrect);
	}
}
