package org.gerken.spanish.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ConjugationRow implements Comparable<ConjugationRow> {

	private String   			verb;
	private String	 			kind;
	private String 	 			tense;
	private String[] 			conjugation;
	private ArrayList<String> 	nav;
	private ArrayList<String>	sort;
	private ArrayList<Integer>	spans;
	
	public ConjugationRow(String verb, String kind, String tense, HashMap<String, String> hashMap) {
		super();
		this.verb = verb;
		this.kind = kind;
		this.tense = tense;
		this.conjugation = new String[6];
		int index = -1;
		for (String p: Constants.persona) {
			index++;
			this.conjugation[index] = hashMap.get(p);
		}
	}
	
	public String[] getConjugation() {
		return conjugation;
	}
	
	public String getVerb() {
		return verb;
	}
	
	public String getKind() {
		return kind;
	}
	
	public String getTense() {
		return tense;
	}

	public ArrayList<String> getSort() {
		return sort;
	}

	public ArrayList<String> getNav() {
		return nav;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sort);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjugationRow other = (ConjugationRow) obj;
		return sort == other.sort;
	}

	public boolean matches(ArrayList<String> order, ArrayList<String> filter) {
		nav  = new ArrayList<>(); 
		sort = new ArrayList<>(); 
		for (int index=0; index < filter.size(); index++) {
			String property = order.get(index);
			String value = filter.get(index);
			
			String current = null;
			String sortkey = null;
			if (property.equals("verb")) {
				current = verb;
				sortkey = verb;
			} else if (property.equals("kind")) {
				current = kind;
				sortkey = lookup(kind,Constants.kind);
			} else if (property.equals("tense")) {
				current = tense;
				sortkey = lookup(tense,Constants.tiempo);
			} 
			if (value.equals("*")) {
				nav.add(current);
				sort.add(sortkey);
			} else {
				if (!current.equals(value)) {
					return false;
				}
			}
			
		}
		
		return true;
	}

	private String lookup(String key, String[] array) {
		for (int index = 0; index < array.length; index++) {
			if (key.equals(array[index])) {
				return String.valueOf(100+index);
			}
		}
		return "x";
	}

	@Override
	public int compareTo(ConjugationRow o) {
		for (int i = 0; i < sort.size(); i++) {
			int result = sort.get(i).compareTo(o.sort.get(i));
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "ConjugationRow [verb=" + verb + ", kind=" + kind + ", tense=" + tense + ", conjugation="
				+ Arrays.toString(conjugation) + "]";
	}

	public void lookAt(ConjugationRow next) {
		
		if (next.spans == null) {
			next.spans = new ArrayList<>();
			for (String str: nav) {
				next.spans.add(1);
			}
		}

		spans = new ArrayList<>();
		
		boolean newSpan = false;
		for (int index = 0; index < nav.size(); index++) {
			if (newSpan) {
				next.spans.add(index, 1);
				spans.add(1);
			} else if (nav.get(index).equals(next.nav.get(index))) {
				next.nav.set(index, "");
				spans.add(next.spans.get(index) + 1);
				next.spans.set(index, 0);
			} else {
				newSpan = true;
				next.spans.add(index, 1);
				spans.add(1);
			}
		}
		
		
	}


}
