package org.gerken.spanish.logic;

import java.util.HashMap;

public class Constants {

	public static String tiempo[] = { "presente", "indefinido", "imperfecto", "futuro immediato", "futuro" }; 
	public static String use[] = { "present", "past once", "past many", "be going to", "future" }; 
	public static String persona[] = { "yo" , "tú", "él" , "nosotros", "vosotros" , "ellas" };
	public static String kind[] = { "-ar" , "-er", "-ir" , "irregular" };
	private static HashMap<String, String> tenseDefinitions = null;
	
	public static String tenseDefinition(String tense) {
		if (tenseDefinitions == null) {
			tenseDefinitions = new HashMap<>();
			for (int index = 0; index < Constants.tiempo.length; index++) {
				tenseDefinitions.put(Constants.tiempo[index], Constants.use[index]);
			}
		}
		return tenseDefinitions.get(tense);
	}

}
