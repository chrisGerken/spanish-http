package org.gerken.spanish.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Question implements Comparable<Question> {

	private static Random 	random 	= new Random(System.currentTimeMillis());
	private static Integer 	lastId 	= 0;  
	
	private String 				question = "";
	private HashSet<String> 	rawOptions = new HashSet<>();
	private ArrayList<String> 	orderedOptions = new ArrayList<>();

	private String 				answer;
	private int 				answerCode;
	
	private int    				correct   = 0;
	private int	   				incorrect = 0;
	private int					over = 2;
	
	private Double				sort = random.nextDouble();
	private Integer				id = lastId++;
	
	public Question() {
		super();
	}

	public void setQuestion(String buf) {
		question = buf;
	}
	
	public String getQuestion() {
		return question;
	}

	public HashSet<String> getRawOptions() {
		return rawOptions;
	}

	public ArrayList<String> getOrderedOptions() {
		return orderedOptions;
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

	public String getPrompt() throws JSONException {
		
		return asJson().toString(4);
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

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(id, other.id);
	}
	
	public void resort() {
		sort = random.nextDouble();
	}

	public JSONObject asJson() throws JSONException {
		JSONObject result = new JSONObject();
		
		orderedOptions = new ArrayList<>();
		ArrayList<Integer> ordered = new ArrayList<>();
		ArrayList<String> options = new ArrayList<>(rawOptions);
		for (int i = options.size(); i > 0; i--) {
			int pick = (int) Math.floor(random.nextDouble() * i);
			ordered.add(pick);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(question+"\n\n");
		for (int i = 1; i <= ordered.size(); i++) {
			int index = ordered.get(i-1);
			String buf = options.get(index);
			orderedOptions.add(buf);
			options.remove(index);
			sb.append(""+i+") "+buf+"\n");
			if (buf.equals(answer)) {
				answerCode = i;
			}
		}
		
		result.put("question", question);
		result.put("answerCode", answerCode);
		result.put("answer", answer);
		result.put("correct", correct);
		result.put("incorrect", incorrect);
		result.put("id", id);
		
		JSONArray jarr = new JSONArray(rawOptions);
		result.put("rawOptions", rawOptions);
		
		jarr = new JSONArray(orderedOptions);
		result.put("orderedOptions", orderedOptions);
		
		return result;
	}

	public String stats() {
		return "Question [id=" + id + ", correct=" + correct + ", incorrect=" + incorrect + ", isFinished()="
				+ isFinished() + "]";
	}
	
}
